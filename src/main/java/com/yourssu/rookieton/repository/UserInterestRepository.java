package com.yourssu.rookieton.repository;

import com.yourssu.rookieton.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {

    @Query("SELECT ui FROM UserInterest ui WHERE ui.user.id = :userId")
    List<UserInterest> findByUserId(UUID userId);

    @Modifying
    @Query("DELETE FROM UserInterest ui WHERE ui.user.id = :userId")
    void deleteByUserId(UUID userId);
}
