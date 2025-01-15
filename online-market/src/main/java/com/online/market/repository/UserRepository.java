package com.online.market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.market.entity.ApiUserEntity;

@Repository
public interface UserRepository extends JpaRepository<ApiUserEntity, Long> {

	Optional<ApiUserEntity> findByUsername(String username);

}