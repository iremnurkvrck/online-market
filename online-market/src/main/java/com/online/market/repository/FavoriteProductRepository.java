package com.online.market.repository;


import com.online.market.entity.FavoriteProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProductEntity, Long> {

    List<FavoriteProductEntity> findByUsername(String username);
    
    boolean existsByUsernameAndPid(String username, int pid);
    
    void deleteByUsernameAndPid(String username, int pid);
}
