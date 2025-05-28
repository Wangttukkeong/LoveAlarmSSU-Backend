package com.yourssu.rookieton.service;

import com.yourssu.rookieton.dto.response.InterestResponse;
import com.yourssu.rookieton.dto.request.UpdateInterestRequest;
import com.yourssu.rookieton.entity.User;
import com.yourssu.rookieton.entity.UserInterest;
import com.yourssu.rookieton.entity.enums.Category;
import com.yourssu.rookieton.entity.enums.CategoryType;
import com.yourssu.rookieton.exception.CustomException;
import com.yourssu.rookieton.exception.ErrorCode;
import com.yourssu.rookieton.repository.UserInterestRepository;
import com.yourssu.rookieton.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterestService {
    private final UserRepository userRepository;
    private final UserInterestRepository userInterestRepository;

    public List<InterestResponse> getInterests(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return user.getInterestList().stream()
                .map(interest -> new InterestResponse(
                        interest.getCategory(), interest.getSubCategory(), interest.getHashtagList()))
                .toList();
    }

    @Transactional
    public void updateInterest(UUID userId, List<UpdateInterestRequest> dtoList) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        validateNoMatchCategory(dtoList);
        validateNoDuplicateCategory(dtoList);
        userInterestRepository.deleteByUserId(userId); // 이게 맞나
        for (UpdateInterestRequest dto : dtoList) {
            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .category(dto.getCategory())
                    .subCategory(dto.getSubCategory())
                    .hashtagList(dto.getHashtagList())
                    .build();
            user.getInterestList().add(userInterest);
        }
    }

    private void validateNoDuplicateCategory(List<UpdateInterestRequest> dtoList) {
        Set<CategoryType> seen = new HashSet<>();
        for (UpdateInterestRequest dto : dtoList) {
            if (!seen.add(dto.getSubCategory())) {
                // 중복된 카테고리를 발견했을 때
                throw new CustomException(
                        ErrorCode.DUPLICATE_INTEREST_CATEGORY);
            }
        }
    }

    private void validateNoMatchCategory(List<UpdateInterestRequest> dtoList) {
        // parent not match child
        for (UpdateInterestRequest dto : dtoList) {
            Category category = dto.getCategory();
            CategoryType subCategory = dto.getSubCategory();
            if (category == null) {
                throw new CustomException(ErrorCode.EMPTY_INTEREST_CATEGORY);
            }
            if (subCategory == null) {
                throw new CustomException(ErrorCode.EMPTY_INTEREST_SUBCATEGORY);
            }

            if (!subCategory.getParent().getValue().equals(category.getValue())) {
                throw new CustomException(ErrorCode.MISMATCH_CATEGORY);
            }
        }
    }
}
