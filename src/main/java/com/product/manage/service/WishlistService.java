package com.product.manage.service;

import com.product.manage.dto.WishlistDTO;

import java.util.List;


public interface WishlistService {
    List<WishlistDTO> getWishlist(Long userId);
    WishlistDTO addToWishlist(WishlistDTO wishlistDTO);
    void removeFromWishlist(Long id);
}