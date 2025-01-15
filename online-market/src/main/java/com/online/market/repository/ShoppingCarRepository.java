package com.online.market.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.online.market.entity.ShoppingCarEntity;

@Repository
public interface ShoppingCarRepository extends JpaRepository<ShoppingCarEntity, Long> {

    Page<ShoppingCarEntity> findAll(Pageable pageable);

    List<ShoppingCarEntity> findByUsername(String username);  // Optional yerine List kullanımı

}
