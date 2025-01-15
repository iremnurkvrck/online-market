package com.online.market.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.market.dto.AuthenticationRequest;
import com.online.market.dto.FavoriteProductDto;
import com.online.market.dto.OrdersDto;
import com.online.market.dto.RegisterRequest;
import com.online.market.dto.ShoppingCarDto;
import com.online.market.dto.UsersPropertiesDto;
import com.online.market.entity.FavoriteProductEntity;
import com.online.market.entity.OrdersEntity;
import com.online.market.entity.ProductsEntity;
import com.online.market.entity.ShoppingCarEntity;
import com.online.market.entity.UsersPropertiesEntity;
import com.online.market.service.AuthenticationService;
import com.online.market.service.FavoriteProductService;
import com.online.market.service.OrdersService;
import com.online.market.service.ProductService;
import com.online.market.service.ShoppingCarService;
import com.online.market.service.UsersPropertiesService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService service;
	@Autowired
	private UsersPropertiesService usersPropertiesService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ShoppingCarService shoppingCarService;
	@Autowired
	FavoriteProductService favoriteProductService;

	@Autowired
	private OrdersService ordersService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		boolean isRegistered = service.register(request);
		if (isRegistered) {
			return new ResponseEntity<>("Kayıt başarılı", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Kullanıcı zaten mevcut", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
		return new ResponseEntity<>(service.authenticate(request), HttpStatus.OK);
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return new ResponseEntity<>(service.refreshToken(request, response), HttpStatus.OK);
	}

	//////
	@PostMapping("/create-users")
	public ResponseEntity<UsersPropertiesEntity> createUser(@RequestBody UsersPropertiesDto usersDto) {
		UsersPropertiesEntity savedUser = usersPropertiesService.saveUsers(usersDto);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	// Tüm ürünleri listele
	@GetMapping("/all")
	public ResponseEntity<List<ProductsEntity>> getAllProducts() {
		List<ProductsEntity> products = productService.findAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/category/{category}")
	public ResponseEntity<List<ProductsEntity>> getProductsByCategory(@PathVariable("category") String category) {
		List<ProductsEntity> products = productService.findProductsByCategory(category);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	// Sepete ürün ekle
	@PostMapping("/add")
	public ResponseEntity<ShoppingCarEntity> addItemToCart(@RequestBody ShoppingCarDto shoppingCarDto) {
		// Kullanıcı adını alıyoruz
		String username = shoppingCarDto.getUsername();
		if (username == null || username.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// Ürün ve kullanıcı adı ile sepete ekleme işlemi yapılır
		try {
			// Sepete ekleme işlemi ve kullanıcı bilgisini kaydetme
			ShoppingCarEntity savedCart = shoppingCarService.saveCart(shoppingCarDto, username);
			return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
		} catch (Exception e) {
			// Hata durumunda
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{username}")
	public ResponseEntity<UsersPropertiesEntity> updateUser(@PathVariable("username") String username,
			@RequestBody UsersPropertiesDto usersDto) {
		UsersPropertiesEntity updatedUser = usersPropertiesService.updateUsers(usersDto, username);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	@GetMapping("/username/{username}")
	public ResponseEntity<UsersPropertiesEntity> getUserByUsername(@PathVariable("username") String username) {
		UsersPropertiesEntity user = usersPropertiesService.findUsername(username);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/user-cart/{username}")
	public ResponseEntity<List<ShoppingCarEntity>> getCartByUserName(@PathVariable("username") String username) {
		List<ShoppingCarEntity> cartItems = shoppingCarService.findCartByUsername(username);
		if (cartItems.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Kullanıcıya ait sepet bulunamazsa 404 döndürülür
		}
		return new ResponseEntity<>(cartItems, HttpStatus.OK);
	}

	@GetMapping("/cart/total/{username}")
	public double getTotalCartValue(@PathVariable("username") String username) {
		return shoppingCarService.calculateTotalCartValue(username);
	}

	@PostMapping("/favorite-add")
	public ResponseEntity<FavoriteProductEntity> addToFavorites(@RequestBody FavoriteProductDto favoriteProductDto) {
		FavoriteProductEntity savedOrder = favoriteProductService.saveProduct(favoriteProductDto);
		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}

	@GetMapping("/favorite-list/{username}")
	public ResponseEntity<List<FavoriteProductEntity>> getFavoriteByUsername(
			@PathVariable("username") String username) {
		List<FavoriteProductEntity> favorites = favoriteProductService.findProductsByUsername(username);
		return ResponseEntity.ok(favorites);
	}

	@PostMapping("/orders-create")
	public ResponseEntity<OrdersEntity> createOrder(@RequestBody OrdersDto orderDto) {
		OrdersEntity savedOrder = ordersService.saveOrder(orderDto);
		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}
	
	@GetMapping("/orders/username/{username}")
	public ResponseEntity<List<OrdersEntity>> getOrderByUsername(@PathVariable("username") String username) {
		List<OrdersEntity> order = ordersService.findByUsername(username);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-orders/{id}")
	public ResponseEntity<Boolean> deletePolicy(@PathVariable("id") Long id) {

		return ResponseEntity.ok(shoppingCarService.deleteItemFromCart(id));
	}




}