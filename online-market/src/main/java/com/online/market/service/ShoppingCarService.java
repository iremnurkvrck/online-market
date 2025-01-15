package com.online.market.service;

import java.util.List;

import com.online.market.dto.ShoppingCarDto;
import com.online.market.entity.ShoppingCarEntity;

public interface ShoppingCarService {

     ShoppingCarEntity saveCart(ShoppingCarDto shoppingCarDto, String username);

    List<ShoppingCarEntity> updateCartItem(String username, ShoppingCarDto shoppingCarDto);

    List<ShoppingCarEntity> findByAllCart();

    boolean deleteItemFromCart(Long id);

    // Kullanıcı adına göre sepet öğelerini listeleyen metodu ekliyoruz
    List<ShoppingCarEntity> findCartByUsername(String username); 
    double calculateTotalCartValue(String username);
}
