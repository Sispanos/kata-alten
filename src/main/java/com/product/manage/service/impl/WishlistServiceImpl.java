package com.product.manage.service.impl;

import com.product.manage.dto.WishlistDTO;
import com.product.manage.repository.ProductRepository;
import com.product.manage.repository.UserRepository;
import com.product.manage.repository.WishlistRepository;
import com.product.manage.model.Product;
import com.product.manage.model.User;
import com.product.manage.model.Wishlist;
import com.product.manage.service.WishlistService;
import com.product.manage.service.mapper.WishlistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WishlistMapper wishlistMapper;

    public List<WishlistDTO> getWishlist(Long userId) {
        return wishlistRepository.findByUserId(userId).stream()
                .map(wishlistMapper::toDTO)
                .collect(Collectors.toList());
    }

    public WishlistDTO addToWishlist(WishlistDTO wishlistDTO) {
        User user = userRepository.findById(wishlistDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(wishlistDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Wishlist wishlist = wishlistMapper.toEntity(wishlistDTO);
        wishlist.setUser(user);
        wishlist.setProduct(product);

        Wishlist savedWishlist = wishlistRepository.save(wishlist);
        return wishlistMapper.toDTO(savedWishlist);
    }

    public void removeFromWishlist(Long id) {
        wishlistRepository.deleteById(id);
    }
}