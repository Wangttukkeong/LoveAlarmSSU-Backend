package com.yourssu.rookieton.repository;

import com.yourssu.rookieton.entity.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation, UUID> {
}
