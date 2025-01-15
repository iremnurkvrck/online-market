package com.online.market.service;

import java.util.List;
import java.util.Optional;

import com.online.market.dto.ProductsDto;
import com.online.market.entity.ProductsEntity;

public interface ProductService {

	// Tüm ürünleri listele
	List<ProductsEntity> findAllProducts();

	// Ürün ekle
	ProductsEntity saveProduct(ProductsDto productDto);

	// ID'ye göre ürün sil
	boolean deleteProductById(Long id);

	// ID'ye göre ürün güncelle
	ProductsEntity updateProductById(String name, ProductsDto productDto);

	// Kategoriye göre ürünleri getir
	List<ProductsEntity> findProductsByCategory(String category);

	// İsme göre ürün ara
	Optional<ProductsEntity> findProductByName(String name);

	// Belirli bir fiyat aralığındaki ürünleri getir
	List<ProductsEntity> findProductsByPriceBetween(int minPrice, int maxPrice);

	// En yüksek fiyatlı ürünü getir
	Optional<ProductsEntity> findMostExpensiveProduct();

	// En düşük fiyatlı ürünü getir
	Optional<ProductsEntity> findCheapestProduct();
}