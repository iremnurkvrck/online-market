package com.online.market.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.online.market.dto.OrdersDto;
import com.online.market.entity.OrdersEntity;
import com.online.market.repository.OrdersRepository;
import com.online.market.repository.ProductRepository;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	private OrdersRepository ordersRepository;
	
    
    @Autowired
    ProductRepository productRepository;

	@Override
	public List<OrdersEntity> findByAllOrders() {

		return ordersRepository.findAll();
		// Butun kayıtları getirir.
	}

	@Override
	public OrdersEntity saveOrder(OrdersDto orderDto) {
	    if (orderDto.getPlacedOn() == null) {
	        orderDto.setPlacedOn(LocalDateTime.now());
	    }
	    return ordersRepository.save(convertDtoToEntity(new OrdersEntity(), orderDto));
	}


	@Override
	public boolean deleteOrderById(Long id) {

		try {
			ordersRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
		// kaydı siler.
	}


	@Override
	public OrdersEntity findByEmail(String email) {

		Optional<OrdersEntity> orders = ordersRepository.findByEmail(email);
		return orders.get();
		// Email adresine gore bulur.
	}

	@Override
	public List<OrdersEntity> findByUsername(String username) {

		return ordersRepository.findByUsername(username);
		// Kullanıcı adına gore bulur.
	}



	@Override
	public List<OrdersEntity> findOrdersByMethod(String method) {
		return ordersRepository.findByMethod(method);
		// Verılen odeme yontemıne göre siparisleri getirir.
	}

	@Override
	public List<OrdersEntity> findOrdersByTotalProductsGreaterThan(int totalProducts) {

		return ordersRepository.findByTotalProductsGreaterThan(totalProducts);
		// Toplam urun sayısı belirli bir degerin uzerınde olan siparisleri getirir.
	}


	private OrdersEntity convertDtoToEntity(OrdersEntity ordersEntity, OrdersDto ordersDto) {
	    return OrdersEntity.builder()
	            .id(ordersEntity.getId())
	            .address(ordersDto.getAddress()) // adress yerine address olarak düzeltildi.
	            .email(ordersDto.getEmail())
	            .method(ordersDto.getMethod())
	            .phoneNumber(ordersDto.getPhoneNumber())
	            .placedOn(ordersDto.getPlacedOn() != null ? ordersDto.getPlacedOn() : LocalDateTime.now())
	            .totalPrice(ordersDto.getTotalPrice())
	            .totalProducts(ordersDto.getTotalProducts())
	            .status(ordersDto.getStatus())
	            .username(ordersDto.getUsername())
	            .build();
	}



}
