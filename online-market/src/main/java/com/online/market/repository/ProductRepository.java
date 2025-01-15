package com.online.market.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.market.entity.ProductsEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity, Long> {

	Optional<ProductsEntity> findByName(String name);

	List<ProductsEntity> findByCategory(String category);

}
