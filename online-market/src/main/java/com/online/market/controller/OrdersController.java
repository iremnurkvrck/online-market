package com.online.market.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.online.market.dto.OrdersDto;
import com.online.market.entity.OrdersEntity;
import com.online.market.service.OrdersService;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@GetMapping("/all")
	public ResponseEntity<List<OrdersEntity>> getAllOrders() {
		List<OrdersEntity> orders = ordersService.findByAllOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<OrdersEntity> createOrder(@RequestBody OrdersDto orderDto) {
		OrdersEntity savedOrder = ordersService.saveOrder(orderDto);
		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		boolean isDeleted = ordersService.deleteOrderById(id);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}



	@GetMapping("/email/{email}")
	public ResponseEntity<OrdersEntity> getOrderByEmail(@PathVariable String email) {
		OrdersEntity order = ordersService.findByEmail(email);
		return new ResponseEntity<>(order, HttpStatus.OK);
	}


	@GetMapping("/method/{method}")
	public ResponseEntity<List<OrdersEntity>> getOrdersByMethod(@PathVariable String method) {
		List<OrdersEntity> orders = ordersService.findOrdersByMethod(method);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/total-products/{totalProducts}")
	public ResponseEntity<List<OrdersEntity>> getOrdersByTotalProductsGreaterThan(@PathVariable int totalProducts) {
		List<OrdersEntity> orders = ordersService.findOrdersByTotalProductsGreaterThan(totalProducts);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

}
