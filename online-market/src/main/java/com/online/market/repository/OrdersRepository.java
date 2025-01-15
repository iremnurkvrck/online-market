package com.online.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.market.entity.OrdersEntity;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity,Long> {

	Page<OrdersEntity> findAll(Pageable pageable);
	List<OrdersEntity> findByUsername(String username);
	Optional<OrdersEntity> findByEmail(String email);
	List<OrdersEntity> findByMethod(String method);
	List<OrdersEntity> findByTotalProductsGreaterThan(int totalProducts);



}
