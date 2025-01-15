package com.online.market.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.market.entity.UsersPropertiesEntity;

@Repository
public interface UsersPropertiesRepository extends JpaRepository<UsersPropertiesEntity, Long> {

	List<UsersPropertiesEntity> findByEmail(String email);
	
	public List<UsersPropertiesEntity> findByUsername(String username);
}
