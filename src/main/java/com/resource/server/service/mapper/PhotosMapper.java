package com.resource.server.service.mapper;

import com.resource.server.domain.*;
import com.resource.server.service.dto.PhotosDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photos} and its DTO {@link PhotosDTO}.
 */
@Mapper(componentModel = "spring", uses = {StockItemsMapper.class})
public interface PhotosMapper extends EntityMapper<PhotosDTO, Photos> {

    @Mapping(source = "stockItem.id", target = "stockItemId")
    PhotosDTO toDto(Photos photos);

    @Mapping(source = "stockItemId", target = "stockItem")
    Photos toEntity(PhotosDTO photosDTO);

    default Photos fromId(Long id) {
        if (id == null) {
            return null;
        }
        Photos photos = new Photos();
        photos.setId(id);
        return photos;
    }
}
