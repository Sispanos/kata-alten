package com.product.manage.service;

import com.product.manage.dto.CartDTO;

import java.util.List;


public interface CartService {
    List<CartDTO> getCart(Long userId);
    CartDTO addToCart(CartDTO cartDTO);
    void removeFromCart(Long id);
}