package com.online.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ShoppingCarDto {

	private String username;
	private int pid;
	private int quantity;
	private String image;
private int price;
}
