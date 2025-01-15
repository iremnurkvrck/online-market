package com.online.market.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.market.dto.FavoriteProductDto;
import com.online.market.entity.FavoriteProductEntity;
import com.online.market.entity.ProductsEntity;
import com.online.market.repository.FavoriteProductRepository;
import com.online.market.repository.ProductRepository;

@Service
public class FavoriteProductServiceImpl implements FavoriteProductService {

	@Autowired
	FavoriteProductRepository favoriteProductRepository;
	
    @Autowired
    ProductRepository productRepository;

	@Override
	public List<FavoriteProductEntity> findAllProducts() {
		// Tüm favori ürünleri getir
		return favoriteProductRepository.findAll();
	}

	@Override
	public FavoriteProductEntity saveProduct(FavoriteProductDto favoriteProductDto) {
		
		FavoriteProductEntity favoriteProductEntity = convertDtoToEntity(new FavoriteProductEntity(),
				favoriteProductDto);
		return favoriteProductRepository.save(favoriteProductEntity);
	}

	@Override
	public void removeProduct(String username, int pid) {
		// Favorilerden ürün silme
		favoriteProductRepository.deleteByUsernameAndPid(username, pid);
	}

	@Override
	public List<FavoriteProductEntity> findProductsByUsername(String username) {
		// Kullanıcının favori ürünlerini getir
		return favoriteProductRepository.findByUsername(username);
	}

	@Override
	public Optional<FavoriteProductEntity> findProductById(Long id) {
		// ID'ye göre favori ürünü getir
		return favoriteProductRepository.findById(id);
	}

	@Override
	public boolean isProductInFavorites(String username, int pid) {
		// Kullanıcı favorilerinde bu ürün var mı kontrol et
		return favoriteProductRepository.existsByUsernameAndPid(username, pid);
	}

	// DTO'dan Entity'ye dönüştürme işlemi
	private FavoriteProductEntity convertDtoToEntity(FavoriteProductEntity favoriteProductEntity,
			FavoriteProductDto favoriteProductDto) {
		
		ProductsEntity product = productRepository.findById((long) favoriteProductDto.getPid())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + favoriteProductDto.getPid()));
		return FavoriteProductEntity.builder().username(favoriteProductDto.getUsername())
				.pid(favoriteProductDto.getPid()).image(favoriteProductDto.getImage())
				.price(product.getPrice()).productName(favoriteProductDto.getProductName()).build();
	}
}
