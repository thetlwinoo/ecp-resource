package com.resource.server.service.impl;

import com.resource.server.domain.Photos;
import com.resource.server.domain.StockItems;
import com.resource.server.repository.PhotosRepository;
import com.resource.server.repository.StockItemTempExtendRepository;
import com.resource.server.repository.StockItemsRepository;
import com.resource.server.service.StockItemsExtendService;
import com.resource.server.service.dto.PhotosDTO;
import com.resource.server.service.dto.StockItemTempDTO;
import com.resource.server.service.mapper.StockItemTempMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StockItemsExtendServiceImpl implements StockItemsExtendService {

    private final Logger log = LoggerFactory.getLogger(StockItemsExtendServiceImpl.class);
    private final StockItemsRepository stockItemsRepository;
    private final PhotosRepository photosRepository;

    public StockItemsExtendServiceImpl(StockItemsRepository stockItemsRepository, PhotosRepository photosRepository) {
        this.stockItemsRepository = stockItemsRepository;
        this.photosRepository = photosRepository;
    }

    @Override
    public PhotosDTO addPhotos(PhotosDTO photosDTO) {
        try {
            StockItems stockItems = stockItemsRepository.getOne(photosDTO.getStockItemId());
            Photos photos = new Photos();
            photos.setStockItem(stockItems);
            photos.setId(photosDTO.getId());
            photos.setOriginalPhotoBlob(photosDTO.getOriginalPhotoBlob());
            photos.setOriginalPhotoBlobContentType(photosDTO.getOriginalPhotoBlobContentType());
            photos.setThumbnailPhotoBlob(photosDTO.getThumbnailPhotoBlob());
            photos.setThumbnailPhotoBlobContentType(photosDTO.getThumbnailPhotoBlobContentType());
            stockItems.getPhotoLists().add(photos);
            stockItemsRepository.save(stockItems);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        return photosDTO;
    }

    @Override
    public PhotosDTO updatePhotos(PhotosDTO photosDTO) {
        try {
            StockItems stockItems = stockItemsRepository.getOne(photosDTO.getStockItemId());
            for (Photos photo : stockItems.getPhotoLists()) {
                if (photo.getId().equals(photosDTO.getId())) {
                    photo.setOriginalPhotoBlob(photosDTO.getOriginalPhotoBlob());
                    photo.setOriginalPhotoBlobContentType(photosDTO.getOriginalPhotoBlobContentType());
                    photo.setThumbnailPhotoBlob(photosDTO.getThumbnailPhotoBlob());
                    photo.setThumbnailPhotoBlobContentType(photosDTO.getThumbnailPhotoBlobContentType());
                    break;
                }
            }
            stockItemsRepository.save(stockItems);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

        return photosDTO;
    }
}
