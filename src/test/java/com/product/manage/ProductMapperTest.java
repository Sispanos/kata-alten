package com.product.manage;

import com.product.manage.dto.ProductDTO;
import com.product.manage.model.InventoryStatus;
import com.product.manage.model.Product;
import com.product.manage.service.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void testToEntity() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(
                null, "P001", "Product 1", "Description 1", "image1.jpg",
                "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5,
                LocalDateTime.now(), LocalDateTime.now()
        );

        // Act
        Product product = productMapper.toEntity(productDTO);

        // Assert
        assertNotNull(product);
        assertEquals("P001", product.getCode());
        assertEquals("Product 1", product.getName());
        assertEquals("Description 1", product.getDescription());
        assertEquals("image1.jpg", product.getImage());
        assertEquals("Category 1", product.getCategory());
        assertEquals(100.0, product.getPrice());
        assertEquals(10, product.getQuantity());
        assertEquals("REF001", product.getInternalReference());
        assertEquals(1L, product.getShellId());
        assertEquals(InventoryStatus.INSTOCK, product.getInventoryStatus());
        assertEquals(5, product.getRating());
        assertNull(product.getCreatedAt()); // createdAt est ignoré dans le mapper
        assertNull(product.getUpdatedAt()); // updatedAt est ignoré dans le mapper
    }

    @Test
    void testToDTO() {
        // Arrange
        Product product = new Product(
                1L, "P001", "Product 1", "Description 1", "image1.jpg",
                "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5,
                LocalDateTime.now(), LocalDateTime.now()
        );

        // Act
        ProductDTO productDTO = productMapper.toDTO(product);

        // Assert
        assertNotNull(productDTO);
        assertEquals(1L, productDTO.getId());
        assertEquals("P001", productDTO.getCode());
        assertEquals("Product 1", productDTO.getName());
        assertEquals("Description 1", productDTO.getDescription());
        assertEquals("image1.jpg", productDTO.getImage());
        assertEquals("Category 1", productDTO.getCategory());
        assertEquals(100.0, productDTO.getPrice());
        assertEquals(10, productDTO.getQuantity());
        assertEquals("REF001", productDTO.getInternalReference());
        assertEquals(1L, productDTO.getShellId());
        assertEquals(InventoryStatus.INSTOCK, productDTO.getInventoryStatus());
        assertEquals(5, productDTO.getRating());
        assertNotNull(productDTO.getCreatedAt());
        assertNotNull(productDTO.getUpdatedAt());
    }

    @Test
    void testUpdateEntityFromDTO() {
        // Arrange
        Product existingProduct = new Product(
                1L, "P001", "Product 1", "Description 1", "image1.jpg",
                "Category 1", 100.0, 10, "REF001", 1L, InventoryStatus.INSTOCK, 5,
                LocalDateTime.now(), LocalDateTime.now()
        );

        ProductDTO productDTO = new ProductDTO(
                null, "P002", "Updated Product", "Updated Description", "image2.jpg",
                "Category 2", 200.0, 20, "REF002", 2L, InventoryStatus.LOWSTOCK, 4,
                null, null
        );

        // Act
        productMapper.updateEntityFromDTO(productDTO, existingProduct);

        // Assert
        assertEquals(1L, existingProduct.getId()); // L'ID ne doit pas changer
        assertEquals("P002", existingProduct.getCode());
        assertEquals("Updated Product", existingProduct.getName());
        assertEquals("Updated Description", existingProduct.getDescription());
        assertEquals("image2.jpg", existingProduct.getImage());
        assertEquals("Category 2", existingProduct.getCategory());
        assertEquals(200.0, existingProduct.getPrice());
        assertEquals(20, existingProduct.getQuantity());
        assertEquals("REF002", existingProduct.getInternalReference());
        assertEquals(2L, existingProduct.getShellId());
        assertEquals(InventoryStatus.LOWSTOCK, existingProduct.getInventoryStatus());
        assertEquals(4, existingProduct.getRating());
        assertNotNull(existingProduct.getCreatedAt()); // createdAt ne doit pas être modifié
        assertNotNull(existingProduct.getUpdatedAt()); // updatedAt ne doit pas être modifié ici (sera géré dans le service)
    }
}