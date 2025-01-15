package com.online.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online.market.dto.FavoriteProductDto;
import com.online.market.entity.FavoriteProductEntity;
import com.online.market.service.FavoriteProductService;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteProductController {

	@Autowired
	FavoriteProductService favoriteProductService;

	// Favoriye ürün ekleme
	@PostMapping("/add")
	public ResponseEntity<FavoriteProductEntity> addToFavorites(@RequestBody FavoriteProductDto favoriteProductDto) {
		FavoriteProductEntity savedOrder = favoriteProductService.saveProduct(favoriteProductDto);
		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}

	// Kullanıcının tüm favori ürünlerini listeleme
	@GetMapping("/list")
	public ResponseEntity<List<FavoriteProductEntity>> getFavorites() {
		List<FavoriteProductEntity> favorites = favoriteProductService.findAllProducts();
		return ResponseEntity.ok(favorites);
	}

	// Favorilerden ürün silme
	@DeleteMapping("/remove")
	public ResponseEntity<String> removeFromFavorites(@RequestParam String username, @RequestParam int pid) {
		favoriteProductService.removeProduct(username, pid);
		return ResponseEntity.ok("Product removed from favorites");
	}
}
