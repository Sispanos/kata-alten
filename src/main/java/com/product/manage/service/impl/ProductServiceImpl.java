package com.product.manage.service.impl;

import com.product.manage.dto.ProductDTO;
import com.product.manage.repository.ProductRepository;
import com.product.manage.model.Product;
import com.product.manage.service.ProductService;
import com.product.manage.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    public ProductDTO createProduct(ProductDTO productDto) {
        Product product = productMapper.toEntity(productDto);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDto) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    productMapper.updateEntityFromDTO(productDto, existingProduct);
                    existingProduct.setUpdatedAt(LocalDateTime.now());
                    Product updatedProduct = productRepository.save(existingProduct);
                    return productMapper.toDTO(updatedProduct);
                })
                .orElse(null); //
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
