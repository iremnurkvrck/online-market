package com.online.market.service;

import java.util.List;

import com.online.market.dto.UsersPropertiesDto;
import com.online.market.entity.UsersPropertiesEntity;

public interface UsersPropertiesService {

	List<UsersPropertiesEntity> findByAllUsers();

	UsersPropertiesEntity saveUsers(UsersPropertiesDto usersDto);

	boolean deleteUsersyById(Long id);

	UsersPropertiesEntity updateUsers(UsersPropertiesDto usersDto, String username);

	List<UsersPropertiesEntity> findByEmail(String email);
	
	UsersPropertiesEntity findUsername(String username);


}
