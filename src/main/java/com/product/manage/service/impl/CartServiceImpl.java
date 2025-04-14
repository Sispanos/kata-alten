package com.product.manage.service.impl;

import com.product.manage.dto.CartDTO;
import com.product.manage.repository.CartRepository;
import com.product.manage.repository.ProductRepository;
import com.product.manage.repository.UserRepository;
import com.product.manage.model.Cart;
import com.product.manage.model.Product;
import com.product.manage.model.User;
import com.product.manage.service.CartService;
import com.product.manage.service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public List<CartDTO> getCart(Long userId) {
        return cartRepository.findByUserId(userId).stream()
                .map(cartMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CartDTO addToCart(CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(cartDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartMapper.toEntity(cartDTO);
        cart.setUser(user);
        cart.setProduct(product);

        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toDTO(savedCart);
    }

    public void removeFromCart(Long id) {
        cartRepository.deleteById(id);
    }
}