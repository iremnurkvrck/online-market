package com.online.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class FavoriteProductDto {

	private String username;
	private int pid;
	private String image;
	private int price;
	private String productName;
}
