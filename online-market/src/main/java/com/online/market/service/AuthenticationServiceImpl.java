package com.online.market.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.online.market.dto.AuthenticationRequest;
import com.online.market.dto.AuthenticationResponse;
import com.online.market.dto.RegisterRequest;
import com.online.market.entity.ApiUserEntity;
import com.online.market.enumurated.RoleName;
import com.online.market.repository.UserRepository;
import com.online.market.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public Boolean register(RegisterRequest request) {
		if (!repository.findByUsername(request.getUsername()).isEmpty())
			return false;
		ApiUserEntity user = ApiUserEntity.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).role(RoleName.USER)
				.createDate(LocalDateTime.now()).build();
		System.out.println("User to save: " + user);
		repository.save(user);
		return true;
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		var user = repository.findByUsername(request.getUsername()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		return AuthenticationResponse.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
	}

	@Override
	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		final String refreshToken;
		final String userEmail;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return null;
		}
		refreshToken = authHeader.substring(7);
		userEmail = jwtService.extractUsername(refreshToken);
		if (userEmail != null) {
			var user = this.repository.findByUsername(userEmail).orElseThrow();
			if (jwtService.isTokenValid(refreshToken, user)) {
				var accessToken = jwtService.generateToken(user);
				return AuthenticationResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

			}
		}
		return null;
	}
}