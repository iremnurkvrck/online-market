package com.online.market.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.market.dto.ShoppingCarDto;
import com.online.market.entity.ProductsEntity;
import com.online.market.entity.ShoppingCarEntity;
import com.online.market.repository.ProductRepository;
import com.online.market.repository.ShoppingCarRepository;

@Service
public class ShoppingCarServiceImpl implements ShoppingCarService {

    @Autowired
    private ShoppingCarRepository shoppingCarRepository;
    
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ShoppingCarEntity> updateCartItem(String username, ShoppingCarDto shoppingCarDto) {
        List<ShoppingCarEntity> shoppingCart = shoppingCarRepository.findByUsername(username);

        if (shoppingCart != null && !shoppingCart.isEmpty()) {
            List<ShoppingCarEntity> updatedCartItems = new ArrayList<>();
            for (ShoppingCarEntity item : shoppingCart) {
                updatedCartItems.add(shoppingCarRepository.save(convertDtoToEntity(item, shoppingCarDto)));
            }
            return updatedCartItems;
        } else {
            return new ArrayList<>(); // Eğer sepet öğesi bulunamazsa, boş liste döndürülür
        }
    }

    @Override
    public ShoppingCarEntity saveCart(ShoppingCarDto shoppingCarDto, String username) {
        ShoppingCarEntity shoppingCarEntity = new ShoppingCarEntity();
        
        // Ürünün fiyatını ve resmini veritabanından çekiyoruz
        ProductsEntity product = productRepository.findById((long) shoppingCarDto.getPid())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı: " + shoppingCarDto.getPid()));
        
        shoppingCarEntity.setPid(shoppingCarDto.getPid());
        shoppingCarEntity.setQuantity(shoppingCarDto.getQuantity());
        shoppingCarEntity.setUsername(username);
        shoppingCarEntity.setImage(product.getImage()); // Doğru resmi alıyoruz
        shoppingCarEntity.setPrice(product.getPrice()); // Doğru fiyatı alıyoruz
        
        return shoppingCarRepository.save(shoppingCarEntity);
    }


    @Override
    public boolean deleteItemFromCart(Long id) {
        try {
            shoppingCarRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false; // Hata durumunda false döner
        }
    }

    @Override
    public List<ShoppingCarEntity> findByAllCart() {
        return shoppingCarRepository.findAll(); // Tüm sepet öğelerini döndürür
    }

    @Override
    public List<ShoppingCarEntity> findCartByUsername(String username) {
        return shoppingCarRepository.findByUsername(username); // Kullanıcı adına göre sepet öğelerini döndürür
    }

    @Override
    public double calculateTotalCartValue(String username) {
        // Kullanıcı adına göre sepet öğelerini alıyoruz
        List<ShoppingCarEntity> shoppingCart = shoppingCarRepository.findByUsername(username);

        double total = 0.0;

        // Sepet öğeleri varsa, her bir öğenin fiyatını ve miktarını kullanarak toplamı hesaplıyoruz
        if (shoppingCart != null && !shoppingCart.isEmpty()) {
            for (ShoppingCarEntity item : shoppingCart) {
                total += item.getPrice() * item.getQuantity(); // Fiyat * Miktar
            }
        }

        return total; // Toplam tutarı döndürüyoruz
    }
    private ShoppingCarEntity convertDtoToEntity(ShoppingCarEntity shoppingCarEntity, ShoppingCarDto shoppingCarDto) {
        return ShoppingCarEntity.builder()
                .image(shoppingCarDto.getImage())
                .pid(shoppingCarDto.getPid())
                .quantity(shoppingCarDto.getQuantity()) // Burada doğru alanı kullanıyoruz
                .username(shoppingCarDto.getUsername()).price(shoppingCarDto.getPrice())
                .build();
    }
}
