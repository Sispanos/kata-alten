package com.product.manage.dto;

import com.product.manage.model.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private String category;
    private double price;
    private int quantity;
    private String internalReference;
    private Long shellId;
    private InventoryStatus inventoryStatus;
    private int rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}