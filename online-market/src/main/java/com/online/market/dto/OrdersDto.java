package com.online.market.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class OrdersDto {

	private String username;
	private String phoneNumber;
	private String email;
	private String method;
	private String address;
	private int totalProducts;
	private float totalPrice;
	private LocalDateTime placedOn;
	private String status; 

}
