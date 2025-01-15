package com.online.market.service;

import java.util.List;
import java.util.Optional;

import com.online.market.dto.FavoriteProductDto;
import com.online.market.entity.FavoriteProductEntity;

public interface FavoriteProductService {

	List<FavoriteProductEntity> findAllProducts();

	FavoriteProductEntity saveProduct(FavoriteProductDto favoriteProductDto);

	void removeProduct(String username, int pid);

	List<FavoriteProductEntity> findProductsByUsername(String username);

	Optional<FavoriteProductEntity> findProductById(Long id);

	boolean isProductInFavorites(String username, int pid);
}
