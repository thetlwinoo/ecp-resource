package com.resource.server.service;

import com.resource.server.service.dto.PhotosDTO;
import com.resource.server.service.dto.StockItemTempDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StockItemsExtendService {
    PhotosDTO addPhotos(PhotosDTO photosDTO);
    PhotosDTO updatePhotos(PhotosDTO photosDTO);
}
