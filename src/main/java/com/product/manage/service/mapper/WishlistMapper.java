package com.product.manage.service.mapper;

import com.product.manage.dto.WishlistDTO;
import com.product.manage.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    WishlistMapper INSTANCE = Mappers.getMapper(WishlistMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    WishlistDTO toDTO(Wishlist wishlist);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "productId", target = "product.id")
    Wishlist toEntity(WishlistDTO wishlistDTO);
}
