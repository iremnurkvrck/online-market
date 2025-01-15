package com.online.market.service;

import java.io.IOException;

import com.online.market.dto.AuthenticationRequest;
import com.online.market.dto.AuthenticationResponse;
import com.online.market.dto.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

	public Boolean register(RegisterRequest request);

	public AuthenticationResponse authenticate(AuthenticationRequest request);

	public AuthenticationResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws IOException;
}