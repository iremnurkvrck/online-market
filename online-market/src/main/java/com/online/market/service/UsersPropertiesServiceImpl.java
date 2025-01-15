package com.online.market.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.online.market.dto.UsersPropertiesDto;
import com.online.market.entity.ApiUserEntity;
import com.online.market.entity.UsersPropertiesEntity;
import com.online.market.enumurated.RoleName;
import com.online.market.repository.UserRepository;
import com.online.market.repository.UsersPropertiesRepository;

@Service
public class UsersPropertiesServiceImpl implements UsersPropertiesService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UsersPropertiesRepository usersPropertiesRepository;
	
	@Autowired
	private UserRepository apiUserRepository;
	

	@Override
	public List<UsersPropertiesEntity> findByAllUsers() {

		return usersPropertiesRepository.findAll();
	}


	@Override
	public boolean deleteUsersyById(Long id) {
		try {
			usersPropertiesRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UsersPropertiesEntity updateUsers(UsersPropertiesDto usersPropertiesDto, String username) {
	    // Username ile kullanıcıyı buluyoruz
	    List<UsersPropertiesEntity> usersDatabaseList = usersPropertiesRepository.findByUsername(username);
	    
	    // Eğer kullanıcı bulunamazsa, hata fırlatıyoruz
	 

	    // Kullanıcıyı alıyoruz (burada ilk elemanı alıyoruz)
	    UsersPropertiesEntity usersDatabase = usersDatabaseList.get(0);
	    
	    // Kullanıcı bilgilerini güncelliyoruz
	    usersDatabase.setFirstName(usersPropertiesDto.getFirstName());
	    usersDatabase.setLastName(usersPropertiesDto.getLastName());
	    usersDatabase.setEmail(usersPropertiesDto.getEmail());
	    usersDatabase.setBirthdate(usersPropertiesDto.getBirthdate());
	    usersDatabase.setBirthplace(usersPropertiesDto.getBirthplace());
	    usersDatabase.setGender(usersPropertiesDto.getGender());

	    // Güncellenmiş kullanıcıyı kaydediyoruz
	    return usersPropertiesRepository.save(usersDatabase);
	}


	@Override
	public List<UsersPropertiesEntity> findByEmail(String email) {

		return usersPropertiesRepository.findByEmail(email);
	}

	private UsersPropertiesEntity convertDtoToEntity(UsersPropertiesDto usersDto) {

		UsersPropertiesEntity usersEntity = new UsersPropertiesEntity();
		usersEntity.setUsername(usersDto.getUsername());
		usersEntity.setPassword(passwordEncoder.encode(usersDto.getPassword()));
		usersEntity.setFirstName(usersDto.getFirstName());
		usersEntity.setLastName(usersDto.getLastName());
		usersEntity.setEmail(usersDto.getEmail());
		usersEntity.setBirthdate(usersDto.getBirthdate());
		usersEntity.setBirthplace(usersDto.getBirthplace());
		usersEntity.setGender(usersDto.getGender());

		return usersEntity;

	}

	@Override
	public UsersPropertiesEntity findUsername(String username) {
	    List<UsersPropertiesEntity> usersList = usersPropertiesRepository.findByUsername(username);
	    
	  
	    return usersList.get(0);  // Eğer bir kullanıcı bulunduysa, ilk kullanıcıyı döndürüyoruz
	}
	
	@Override
	public UsersPropertiesEntity saveUsers(UsersPropertiesDto userDto) {
	    try {
	        // UsersPropertiesEntity'ye dönüştür
	        UsersPropertiesEntity usersEntity = convertDtoToEntity(userDto);

	        // Kullanıcıyı UsersProperties tablosuna kaydet
	        UsersPropertiesEntity savedUser = usersPropertiesRepository.save(usersEntity);

	        // Kullanıcıyı ApiUser tablosuna kaydet
	        ApiUserEntity apiUser = convertToApiUser(userDto);
	        apiUserRepository.save(apiUser);

	        return savedUser;
	    } catch (Exception e) {
	        System.out.println("Hata: " + e.getMessage());
	        throw new RuntimeException("Kullanıcı kaydı sırasında bir hata oluştu.");
	    }
	}

	// DTO'dan ApiUserEntity'ye dönüştürme
	private ApiUserEntity convertToApiUser(UsersPropertiesDto userDto) {
	    return ApiUserEntity.builder()
	        .username(userDto.getUsername())
	        .password(passwordEncoder.encode(userDto.getPassword()))
	        .role(RoleName.USER) // Varsayılan rol olarak USER atanıyor
	        .createDate(LocalDateTime.now())
	        .build();
	}



}
