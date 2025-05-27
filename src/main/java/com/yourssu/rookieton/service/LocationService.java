package com.yourssu.rookieton.service;

import com.yourssu.rookieton.dto.response.InterestResponse;
import com.yourssu.rookieton.dto.response.LocationRespose;
import com.yourssu.rookieton.dto.response.NearByUserResponse;
import com.yourssu.rookieton.entity.User;
import com.yourssu.rookieton.entity.UserLocation;
import com.yourssu.rookieton.exception.CustomException;
import com.yourssu.rookieton.exception.ErrorCode;
import com.yourssu.rookieton.repository.UserInterestRepository;
import com.yourssu.rookieton.repository.UserLocationRepository;
import com.yourssu.rookieton.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    @Value("${spring.data.redis.geo-key}")
    private String GEO_KEY;
    private final GeoOperations<String, String> geoOps;
    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final UserInterestRepository userInterestRepository;

    public LocationRespose getLocation(UUID userId) {
        UserLocation userLocation = userLocationRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return LocationRespose.from(userLocation);
    }

    @Transactional
    public void updateLocation(UUID userId, double latitude, double longitude) {
        UserLocation userLocation = userLocationRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        userLocation.setLatitude(latitude);
        userLocation.setLongitude(longitude);
        userLocationRepository.save(userLocation);

        geoOps.add(GEO_KEY, new Point(longitude, latitude), userId.toString());
    }

    @Transactional(readOnly = true)
    public List<NearByUserResponse> findNearByAll(UUID userId) {
        return findNearBy(userId, user -> true);
    }

    @Transactional(readOnly = true)
    public List<NearByUserResponse> findNearByWithCommonInterest(UUID userId) {
        Set<String> baseCategories = userInterestRepository.findByUserId(userId)
                .stream().map(ui -> ui.getSubCategory().name())
                .collect(Collectors.toSet());
        if (baseCategories.isEmpty()) {
            return List.of();
        }

        return findNearBy(userId, user ->
                user.getInterestList().stream()
                        .map(i -> i.getSubCategory().name())
                        .anyMatch(baseCategories::contains)
        );
    }

    private List<NearByUserResponse> findNearBy(UUID userId, Predicate<User> filter) {
        if (!userLocationRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        GeoResults<RedisGeoCommands.GeoLocation<String>> results = redisRadiusSearch(userId);
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> filtered = results.getContent().stream()
                .filter(geoResult ->
                        !UUID.fromString(geoResult.getContent().getName()).equals(userId)
                )
                .toList();

        if (filtered.isEmpty()) {
            return List.of();
        }

        Map<UUID, Point> locMap = new HashMap<>();
        Map<UUID, Double> distMap = new HashMap<>();
        for (GeoResult<RedisGeoCommands.GeoLocation<String>> geoResult : filtered) {
            UUID id = UUID.fromString(geoResult.getContent().getName());
            locMap.put(id, geoResult.getContent().getPoint());
            distMap.put(id, geoResult.getDistance().getValue());
        }
        List<UUID> ids = new ArrayList<>(locMap.keySet());
        if (ids.isEmpty()) return List.of();

        List<User> users = userRepository.findAllWithInterestsById(ids);
        return users.stream()
                .filter(filter)
                .map(u -> toNearbyDTO(u, locMap.get(u.getId()), distMap.get(u.getId()))).toList();
    }

    private GeoResults<RedisGeoCommands.GeoLocation<String>> redisRadiusSearch(UUID userId) {
        String uid = userId.toString();
        GeoReference<String> ref = GeoReference.fromMember(uid);
        Distance radius = new Distance(50, Metrics.METERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs()
                .includeDistance()
                .includeCoordinates()
                .limit(10)
                .sortAscending();

        return geoOps.search(GEO_KEY, ref, radius, args);
    }

    private NearByUserResponse toNearbyDTO(
            User user, Point point, Double distance) {
        List<InterestResponse> interests = user.getInterestList().stream()
                .map(i -> new InterestResponse(i.getSubCategory(), i.getHashtagList()))
                .toList();

        return NearByUserResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .emoji(user.getEmoji())
                .interests(interests)
                .latitude(point.getY())
                .longitude(point.getX())
                .distance(distance)
                .build();
    }
}
