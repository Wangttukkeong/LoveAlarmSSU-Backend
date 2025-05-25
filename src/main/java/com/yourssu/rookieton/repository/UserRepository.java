package com.yourssu.rookieton.repository;

import com.yourssu.rookieton.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u left join fetch u.interestList where u.id in :ids")
    List<User> findAllWithInterestsById(@Param("ids") List<UUID> ids);
}
