package com.product.manage;

import com.product.manage.dto.CartDTO;
import com.product.manage.repository.CartRepository;
import com.product.manage.repository.ProductRepository;
import com.product.manage.repository.UserRepository;
import com.product.manage.model.Cart;
import com.product.manage.model.InventoryStatus;
import com.product.manage.model.Product;
import com.product.manage.model.User;
import com.product.manage.service.impl.CartServiceImpl;
import com.product.manage.service.mapper.CartMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCart() {
        // Arrange
        User user = new User(1L, "jamal_bh", "jamalbh@gmail.com", "password123", null, null);
        Product product = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, null, null);
        Cart cart = new Cart(1L, user, product, 2);
        when(cartRepository.findByUserId(1L)).thenReturn(Arrays.asList(cart));
        when(cartMapper.toDTO(cart)).thenReturn(new CartDTO(1L, 1L, 1L, 2));

        // Act
        List<CartDTO> cartItems = cartService.getCart(1L);

        // Assert
        assertEquals(1, cartItems.size());
        assertEquals(1L, cartItems.get(0).getId());
        verify(cartRepository, times(1)).findByUserId(1L);
    }

    @Test
    void addToCart() {
        // Arrange
        CartDTO cartDTO = new CartDTO(null, 1L, 1L, 2);
        User user = new User(1L, "jamal_bh", "jamalbh@gmail.com", "password123", null, null);
        Product product = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, null, null);
        Cart cart = new Cart(1L, user, product, 2);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartMapper.toEntity(cartDTO)).thenReturn(cart);
        when(cartRepository.save(cart)).thenReturn(cart);
        when(cartMapper.toDTO(cart)).thenReturn(new CartDTO(1L, 1L, 1L, 2));

        // Act
        CartDTO savedCart = cartService.addToCart(cartDTO);

        // Assert
        assertNotNull(savedCart);
        assertEquals(1L, savedCart.getId());
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    void removeFromCart() {
        // Arrange
        doNothing().when(cartRepository).deleteById(1L);

        // Act
        cartService.removeFromCart(1L);

        // Assert
        verify(cartRepository, times(1)).deleteById(1L);
    }
}
