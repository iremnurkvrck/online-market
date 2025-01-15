package com.online.market.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.online.market.dto.UsersPropertiesDto;
import com.online.market.entity.UsersPropertiesEntity;
import com.online.market.service.UsersPropertiesService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserPropertiesController {

    @Autowired
    private UsersPropertiesService usersPropertiesService;

    // Tüm kullanıcıları listele
    @GetMapping
    public ResponseEntity<List<UsersPropertiesEntity>> getAllUsers() {
        List<UsersPropertiesEntity> users = usersPropertiesService.findByAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }



    // ID'ye göre kullanıcı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = usersPropertiesService.deleteUsersyById(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Kullanıcı bilgilerini güncelle
    @PutMapping("/{username}")
    public ResponseEntity<UsersPropertiesEntity> updateUser(
            @PathVariable("username") String username, @RequestBody UsersPropertiesDto usersDto) {
        UsersPropertiesEntity updatedUser = usersPropertiesService.updateUsers(usersDto, username);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // E-posta ile kullanıcı ara
    @GetMapping("/email/{email}")
    public ResponseEntity<List<UsersPropertiesEntity>> getUsersByEmail(@PathVariable String email) {
        List<UsersPropertiesEntity> users = usersPropertiesService.findByEmail(email);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Kullanıcı adı ile kullanıcı ara
    @GetMapping("/username/{username}")
    public ResponseEntity<UsersPropertiesEntity> getUserByUsername(@PathVariable("username") String username) {
        UsersPropertiesEntity user = usersPropertiesService.findUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
