package com.product.manage;

import com.product.manage.dto.ProductDTO;
import com.product.manage.repository.ProductRepository;
import com.product.manage.model.InventoryStatus;
import com.product.manage.model.Product;
import com.product.manage.service.impl.ProductServiceImpl;
import com.product.manage.service.mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllProducts() {
        // Arrange
        Product product1 = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now());
        Product product2 = new Product(2L, "P002", "Product 2", "Description 2", "image2.jpg", "Category 2", 200.0, 20, "REF002", 2L, InventoryStatus.LOWSTOCK, 4, LocalDateTime.now(), LocalDateTime.now());
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productMapper.toDTO(product1)).thenReturn(new ProductDTO(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now()));
        when(productMapper.toDTO(product2)).thenReturn(new ProductDTO(2L, "P002", "Product 2", "Description 2", "image2.jpg", "Category 2", 200.0, 20, "REF002", 2L, InventoryStatus.LOWSTOCK, 4, LocalDateTime.now(), LocalDateTime.now()));

        // Act
        List<ProductDTO> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        assertEquals("Product 1", products.get(0).getName());
        assertEquals("Product 2", products.get(1).getName());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toDTO(product1);
        verify(productMapper, times(1)).toDTO(product2);
    }

    @Test
    void getProductById_WhenProductExists() {
        // Arrange
        Product product = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(new ProductDTO(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now()));

        // Act
        Optional<ProductDTO> foundProduct = productService.getProductById(1L);

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals("Product 1", foundProduct.get().getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toDTO(product);
    }

    @Test
    void getProductById_WhenProductDoesNotExist() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<ProductDTO> foundProduct = productService.getProductById(1L);

        // Assert
        assertFalse(foundProduct.isPresent());
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, never()).toDTO(any());
    }

    @Test
    void createProduct() {
        // Arrange
        ProductDTO productDto = new ProductDTO(null, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, null, null);
        Product product = new Product(null, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, null, null);
        Product savedProduct = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now());
        when(productMapper.toEntity(productDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDTO(savedProduct)).thenReturn(new ProductDTO(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now()));

        // Act
        ProductDTO createdProduct = productService.createProduct(productDto);

        // Assert
        assertNotNull(createdProduct.getId());
        assertEquals("Product 1", createdProduct.getName());
        verify(productMapper, times(1)).toEntity(productDto);
        verify(productRepository, times(1)).save(product);
        verify(productMapper, times(1)).toDTO(savedProduct);
    }

    @Test
    void deleteProduct() {
        // Arrange
        doNothing().when(productRepository).deleteById(1L);

        // Act
        productService.deleteProduct(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateProduct_WhenProductExists() {
        // Arrange
        Product existingProduct = new Product(1L, "P001", "Product 1", "Description 1", "image1.jpg", "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5, LocalDateTime.now(), LocalDateTime.now());
        ProductDTO updatedDetails = new ProductDTO(null, "P001", "Updated Product 1", "Updated Description 1", "image1.jpg", "Category 1", 120.0, 15, "REF001", 1L, InventoryStatus.LOWSTOCK, 4, null, null);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);
        when(productMapper.toDTO(existingProduct)).thenReturn(new ProductDTO(1L, "P001", "Updated Product 1", "Updated Description 1", "image1.jpg", "Category 1", 120.0, 15, "REF001", 1L, InventoryStatus.LOWSTOCK, 4, LocalDateTime.now(), LocalDateTime.now()));

        // Act
        ProductDTO updatedProduct = productService.updateProduct(1L, updatedDetails);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("Updated Product 1", updatedProduct.getName());
        assertEquals(120.0, updatedProduct.getPrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).updateEntityFromDTO(updatedDetails, existingProduct);
        verify(productRepository, times(1)).save(existingProduct);
        verify(productMapper, times(1)).toDTO(existingProduct);
    }

    @Test
    void updateProduct_WhenProductDoesNotExist() {
        // Arrange
        ProductDTO updatedDetails = new ProductDTO(null, "P001", "Updated Product 1", "Updated Description 1", "image1.jpg", "Category 1", 120.0, 15, "REF001", 1L, InventoryStatus.LOWSTOCK, 4, null, null);
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        ProductDTO updatedProduct = productService.updateProduct(1L, updatedDetails);

        // Assert
        assertNull(updatedProduct);
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, never()).updateEntityFromDTO(any(), any());
        verify(productRepository, never()).save(any());
        verify(productMapper, never()).toDTO(any());
    }
        
}