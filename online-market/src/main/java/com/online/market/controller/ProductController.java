package com.online.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.online.market.dto.ProductsDto;
import com.online.market.entity.ProductsEntity;
import com.online.market.service.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Tüm ürünleri listele
    @GetMapping("/all")
    public ResponseEntity<List<ProductsEntity>> getAllProducts() {
        List<ProductsEntity> products = productService.findAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Yeni ürün ekle
    @PostMapping("/create")
    public ResponseEntity<ProductsEntity> createProduct(@RequestBody ProductsDto productDto) {
        ProductsEntity savedProduct = productService.saveProduct(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // ID'ye göre ürün sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProductById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ID'ye göre ürün güncelle
    @PutMapping("/{name}")
    public ResponseEntity<ProductsEntity> updateProduct(
            @PathVariable String name, @RequestBody ProductsDto productDto) {
        ProductsEntity updatedProduct = productService.updateProductById(name, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // Kategoriye göre ürünleri getir
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductsEntity>> getProductsByCategory(@PathVariable String category) {
        List<ProductsEntity> products = productService.findProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // İsme göre ürün ara
    @GetMapping("/name/{name}")
    public ResponseEntity<ProductsEntity> getProductByName(@PathVariable String name) {
        Optional<ProductsEntity> product = productService.findProductByName(name);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Belirli bir fiyat aralığındaki ürünleri getir
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductsEntity>> getProductsByPriceRange(
            @RequestParam int minPrice, @RequestParam int maxPrice) {
        List<ProductsEntity> products = productService.findProductsByPriceBetween(minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // En yüksek fiyatlı ürünü getir
    @GetMapping("/most-expensive")
    public ResponseEntity<ProductsEntity> getMostExpensiveProduct() {
        Optional<ProductsEntity> product = productService.findMostExpensiveProduct();
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // En düşük fiyatlı ürünü getir
    @GetMapping("/cheapest")
    public ResponseEntity<ProductsEntity> getCheapestProduct() {
        Optional<ProductsEntity> product = productService.findCheapestProduct();
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
