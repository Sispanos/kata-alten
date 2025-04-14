package com.product.manage.controller;

import com.product.manage.dto.WishlistDTO;
import com.product.manage.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<List<WishlistDTO>> getWishlist(@RequestParam Long userId) {
        return ResponseEntity.ok(wishlistService.getWishlist(userId));
    }

    @PostMapping
    public ResponseEntity<WishlistDTO> addToWishlist(@RequestBody WishlistDTO wishlistDTO) {
        return ResponseEntity.ok(wishlistService.addToWishlist(wishlistDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromWishlist(@PathVariable Long id) {
        wishlistService.removeFromWishlist(id);
        return ResponseEntity.noContent().build();
    }
}
