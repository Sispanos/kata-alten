package com.product.manage.service;

import com.product.manage.dto.AuthRequest;
import com.product.manage.dto.AuthResponse;
import com.product.manage.dto.UserDTO;

public interface AuthService {
    UserDTO createAccount(AuthRequest authRequest);
    AuthResponse generateToken(AuthRequest authRequest);
}