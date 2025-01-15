package com.online.market.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.market.dto.ProductsDto;
import com.online.market.entity.ProductsEntity;
import com.online.market.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<ProductsEntity> findAllProducts() {

		return productRepository.findAll();
		// Tum urunler listelenir.
	}

	@Override
	public boolean deleteProductById(Long id) {

		try {
			productRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		// Urunu siler.
	}

	@Override
	public List<ProductsEntity> findProductsByCategory(String category) {

		return productRepository.findByCategory(category);
		// Belirli bir kategoriye göre listeler.
	}

	@Override
	public Optional<ProductsEntity> findProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<ProductsEntity> findProductsByPriceBetween(int minPrice, int maxPrice) {
		  return productRepository.findAll().stream()
	                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
	                .collect(Collectors.toList());
		  //Belirli bir fiyat aralıgında olan urunlerı listeler
	}

	@Override
	public Optional<ProductsEntity> findMostExpensiveProduct() {
		   return productRepository.findAll().stream()
	                .max((product1, product2) -> Double.compare(product1.getPrice(), product2.getPrice()));
		   //En pahalı urunu bulur.
	}

	@Override
	public Optional<ProductsEntity> findCheapestProduct() {
		
		return productRepository.findAll().stream()
                .min((product1, product2) -> Double.compare(product1.getPrice(), product2.getPrice()));
		//En ucuz urunu bulur
	}


	@Override
	public ProductsEntity saveProduct(ProductsDto productDto) {

		return productRepository.save(convertDtoToEntity(new ProductsEntity(), productDto));
		// Urunu kaydeder
	}

	@Override
	public ProductsEntity updateProductById(String name, ProductsDto productDto) {

		Optional<ProductsEntity> products = productRepository.findByName(name);

		return productRepository.save(convertDtoToEntity(products.get(), productDto));

		// Urun gunceller
	}

	private ProductsEntity convertDtoToEntity(ProductsEntity productsEntity, ProductsDto productsDto) {

		return ProductsEntity.builder().category(productsDto.getCategory()).image(productsDto.getImage())
				.name(productsDto.getName()).build();

	}

}
