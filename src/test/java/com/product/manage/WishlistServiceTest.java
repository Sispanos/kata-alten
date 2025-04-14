package com.product.manage;

import com.product.manage.dto.WishlistDTO;
import com.product.manage.repository.ProductRepository;
import com.product.manage.repository.UserRepository;
import com.product.manage.repository.WishlistRepository;
import com.product.manage.model.InventoryStatus;
import com.product.manage.model.Product;
import com.product.manage.model.User;
import com.product.manage.model.Wishlist;
import com.product.manage.service.impl.WishlistServiceImpl;
import com.product.manage.service.mapper.WishlistMapper;
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

class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private WishlistMapper wishlistMapper;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getWishlist() {
        // Arrange
        User user = new User(1L, "jamal_bh", "jamalbh@gmail.com", "password123", null, null);
        Product product = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, null, null);
        Wishlist wishlist = new Wishlist(1L, user, product);
        when(wishlistRepository.findByUserId(1L)).thenReturn(Arrays.asList(wishlist));
        when(wishlistMapper.toDTO(wishlist)).thenReturn(new WishlistDTO(1L, 1L, 1L));

        // Act
        List<WishlistDTO> wishlistItems = wishlistService.getWishlist(1L);

        // Assert
        assertEquals(1, wishlistItems.size());
        assertEquals(1L, wishlistItems.get(0).getId());
        verify(wishlistRepository, times(1)).findByUserId(1L);
    }

    @Test
    void addToWishlist() {
        // Arrange
        WishlistDTO wishlistDTO = new WishlistDTO(null, 1L, 1L);
        User user = new User(1L, "jamal_bh", "jamalbh@gmail.com", "password123", null, null);
        Product product = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, null, null);
        Wishlist wishlist = new Wishlist(1L, user, product);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(wishlistMapper.toEntity(wishlistDTO)).thenReturn(wishlist);
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);
        when(wishlistMapper.toDTO(wishlist)).thenReturn(new WishlistDTO(1L, 1L, 1L));

        // Act
        WishlistDTO savedWishlist = wishlistService.addToWishlist(wishlistDTO);

        // Assert
        assertNotNull(savedWishlist);
        assertEquals(1L, savedWishlist.getId());
        verify(wishlistRepository, times(1)).save(wishlist);
    }

    @Test
    void removeFromWishlist() {
        // Arrange
        doNothing().when(wishlistRepository).deleteById(1L);

        // Act
        wishlistService.removeFromWishlist(1L);

        // Assert
        verify(wishlistRepository, times(1)).deleteById(1L);
    }
}
