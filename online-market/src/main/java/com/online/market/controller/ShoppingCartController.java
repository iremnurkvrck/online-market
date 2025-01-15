package com.online.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.online.market.dto.ShoppingCarDto;
import com.online.market.entity.ShoppingCarEntity;
import com.online.market.service.ShoppingCarService;

@RestController
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCarService shoppingCarService;
//
//    // Sepete ürün ekle
//    @PostMapping("/add")
//    public ResponseEntity<ShoppingCarEntity> addItemToCart(@RequestBody ShoppingCarDto shoppingCarDto) {
//        ShoppingCarEntity savedCart = shoppingCarService.saveCart(shoppingCarDto);
//        return new ResponseEntity<>(savedCart, HttpStatus.CREATED);
//    }


    // Sepetteki ürünleri güncelle
    @PutMapping("/update/{username}")
    public ResponseEntity<List<ShoppingCarEntity>> updateCartItem(@PathVariable String username,
                                                                  @RequestBody ShoppingCarDto shoppingCarDto) {
        List<ShoppingCarEntity> updatedCart = shoppingCarService.updateCartItem(username, shoppingCarDto);
        if (updatedCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Eğer sepet bulunmazsa 404 döndürülür
        }
        return new ResponseEntity<>(updatedCart, HttpStatus.OK);
    }

 

    // Tüm sepet öğelerini getir
    @GetMapping("/all")
    public ResponseEntity<List<ShoppingCarEntity>> getAllProducts() {
        List<ShoppingCarEntity> cart = shoppingCarService.findByAllCart();
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
    
    @GetMapping("/user/{username}")
    public ResponseEntity<List<ShoppingCarEntity>> getCartByUserName(@PathVariable("username") String username) {
        List<ShoppingCarEntity> cartItems = shoppingCarService.findCartByUsername(username);
        if (cartItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Kullanıcıya ait sepet bulunamazsa 404 döndürülür
        }
        return new ResponseEntity<>(cartItems, HttpStatus.OK);
    }

}
