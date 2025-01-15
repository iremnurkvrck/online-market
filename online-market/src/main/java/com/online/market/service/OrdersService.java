package com.online.market.service;

import java.util.List;

import com.online.market.dto.OrdersDto;
import com.online.market.entity.OrdersEntity;

public interface OrdersService {

	List<OrdersEntity> findByAllOrders();

	OrdersEntity saveOrder(OrdersDto orderDto);

	boolean deleteOrderById(Long id);


	OrdersEntity findByEmail(String email);

	 List<OrdersEntity> findByUsername(String username);

	List<OrdersEntity> findOrdersByMethod(String method);

	List<OrdersEntity> findOrdersByTotalProductsGreaterThan(int totalProducts);


}
