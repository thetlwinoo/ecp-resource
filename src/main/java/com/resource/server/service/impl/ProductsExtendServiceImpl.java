package com.resource.server.service.impl;

import com.resource.server.domain.Photos;
import com.resource.server.domain.Products;
import com.resource.server.domain.StockItems;
import com.resource.server.repository.ProductsExtendFilterRepository;
import com.resource.server.repository.ProductsExtendRepository;
import com.resource.server.service.*;
import com.resource.server.service.dto.ProductCategoryDTO;
import com.resource.server.service.dto.ProductsDTO;
import com.resource.server.service.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductsExtendServiceImpl implements ProductsExtendService {

    private final Logger log = LoggerFactory.getLogger(ProductsExtendServiceImpl.class);
    private final ProductsExtendRepository productsExtendRepository;
    private final ProductsExtendFilterRepository productsExtendFilterRepository;
    private final ProductCategoryMapper productCategoryMapper;
    private final ProductsMapper productsMapper;

    public ProductsExtendServiceImpl(ProductsExtendRepository productsExtendRepository, ProductsExtendFilterRepository productsExtendFilterRepository, ProductCategoryMapper productCategoryMapper, ProductsMapper productsMapper) {
        this.productsExtendRepository = productsExtendRepository;
        this.productsExtendFilterRepository = productsExtendFilterRepository;
        this.productCategoryMapper = productCategoryMapper;
        this.productsMapper = productsMapper;
    }

    @Override
    @Cacheable(key = "{#pageable,#productCategoryId}")
    public List<Products> findAllByProductCategory(Pageable pageable, Long productCategoryId) {
        return productsExtendRepository.findAllByProductCategoryId(pageable, productCategoryId);
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> findTop18ByOrderByLastEditedWhenDesc() {
        return productsExtendRepository.findTop18ByOrderByLastEditedWhenDesc();
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> findTop12ByOrderBySellCountDesc() {
        return productsExtendRepository.findTop12ByOrderBySellCountDesc();
    }

    @Override
//    @CachePut(key = "'findTop12ByOrderBySellCountDesc'")
    public List<Products> findTop12ByOrderBySellCountDescCacheRefresh() {
        return productsExtendRepository.findTop12ByOrderBySellCountDesc();
    }

    @Override
//    @Cacheable(key = "#root.methodName")
    public List<Products> getRelatedProducts(Long productCategoryId, Long id) {
        List<Products> returnList = productsExtendRepository.findTop12ByProductCategoryIdAndIdIsNotOrderBySellCountDesc(productCategoryId, id);
        if (returnList.size() < 8) {
            returnList.addAll(productsExtendRepository.findAllByProductCategoryIdIsNotOrderBySellCountDesc(productCategoryId, PageRequest.of(0, 8 - returnList.size())));
        }
        return returnList;
    }

    @Override
    public List<Products> searchProducts(String keyword, Integer page, Integer size) {
        if (page == null || size == null) {
            throw new IllegalArgumentException("Page and size parameters are required");
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return productsExtendRepository.findAllByProductNameContainingIgnoreCase(keyword, pageRequest);
    }

    @Override
    public List<Products> searchProductsAll(String keyword) {
        return productsExtendRepository.findAllByProductNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Long> getSubCategoryList(Long categoryId) {
        return productsExtendFilterRepository.getSubCategoryIds(categoryId);
    }

    @Override
    public List<ProductCategoryDTO> getRelatedCategories(String keyword, Long category) {
        try {
            List<ProductCategoryDTO> returnList = productsExtendFilterRepository.selectCategoriesByTags(keyword == null ? "" : keyword, category == null ? 0 : category).stream()
                .map(productCategoryMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
//            List<ProductSubCategory> aa = productsExtendFilterRepository.selectCategoriesByTags(keyword,Long.valueOf(category));
            return returnList;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }

    }

    @Override
    public List<String> getRelatedColors(String keyword, Long category) {
        try {
            return productsExtendFilterRepository.selectColorsByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<String> getRelatedBrands(String keyword, Long category) {
        return productsExtendFilterRepository.selectBrandsByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
    }

    @Override
    public Object getRelatedPriceRange(String keyword, Long category) {
        try {
            return productsExtendFilterRepository.selectPriceRangeByTags(keyword == null ? "" : keyword, category == null ? 0 : category);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductsDTO save(Products products, String serverUrl) {
        Products saveProduct = new Products();

        try {
            if (products.getId() != null) {
                saveProduct = products;
                for (StockItems _stockItems : products.getStockItemLists()) {
//                    _stockItems.setStockItemName(products.getProductName());
                    _stockItems.setProduct(products);
                    Set<Photos> photoList = new HashSet<Photos>();
                    for (Photos _photos : _stockItems.getPhotoLists()) {
                        if (_photos.getOriginalPhotoBlob() != null && _photos.getThumbnailPhotoBlob() != null) {
                            _photos.setStockItem(_stockItems);
                            photoList.add(_photos);
                        }
                    }
                    _stockItems.getPhotoLists().clear();
                    _stockItems.getPhotoLists().addAll(photoList);
                }
            } else {
                saveProduct.setProductBrand(products.getProductBrand());
                saveProduct.setHandle(products.getHandle());
                saveProduct.setProductCategory(products.getProductCategory());
                saveProduct.setProductName(products.getProductName());
                saveProduct.setSearchDetails(products.getSearchDetails());
                saveProduct.setSupplier(products.getSupplier());
                saveProduct.setDocument(products.getDocument());
                saveProduct.setActiveInd(false);

                for (StockItems _stockItems : products.getStockItemLists()) {
                    StockItems stockItems = new StockItems();
                    String attributeName = StringUtils.isBlank(_stockItems.getProductAttribute().getProductAttributeValue()) ? "" : " - " + _stockItems.getProductAttribute().getProductAttributeValue();
                    String optionName = StringUtils.isBlank(_stockItems.getProductOption().getProductOptionValue()) ? "" : "(" + _stockItems.getProductOption().getProductOptionValue() + ")";
                    stockItems.setStockItemName(products.getProductName() + attributeName + optionName);
                    stockItems.setVendorCode(_stockItems.getVendorCode());
                    stockItems.setVendorSKU(_stockItems.getVendorSKU());
                    stockItems.setBarcode(_stockItems.getBarcode());
                    stockItems.setBarcodeType(_stockItems.getBarcodeType());
                    stockItems.setUnitPrice(_stockItems.getUnitPrice());
                    stockItems.setRecommendedRetailPrice(_stockItems.getRecommendedRetailPrice());
                    stockItems.setProductAttribute(_stockItems.getProductAttribute());
                    stockItems.setProductOption(_stockItems.getProductOption());
                    stockItems.setQuantityOnHand(_stockItems.getQuantityOnHand());
                    stockItems.setItemLength(_stockItems.getItemLength());
                    stockItems.setItemWidth(_stockItems.getItemWidth());
                    stockItems.setItemHeight(_stockItems.getItemHeight());
                    stockItems.setItemWeight(_stockItems.getItemWeight());
                    stockItems.setItemPackageLength(_stockItems.getItemPackageLength());
                    stockItems.setItemPackageWidth(_stockItems.getItemPackageWidth());
                    stockItems.setItemPackageHeight(_stockItems.getItemPackageHeight());
                    stockItems.setItemPackageWeight(_stockItems.getItemPackageWeight());
                    stockItems.setNoOfPieces(_stockItems.getNoOfPieces());
                    stockItems.setNoOfItems(_stockItems.getNoOfItems());
                    stockItems.setManufacture(_stockItems.getManufacture());
                    stockItems.setMarketingComments(_stockItems.getMarketingComments());
                    stockItems.setInternalComments(_stockItems.getInternalComments());
                    stockItems.setSellStartDate(_stockItems.getSellStartDate());
                    stockItems.setSellEndDate(_stockItems.getSellEndDate());
                    stockItems.setCustomFields(_stockItems.getCustomFields());
                    stockItems.setActiveInd(false);
                    stockItems.setMaterial(_stockItems.getMaterial());

                    stockItems.setProduct(saveProduct);

                    for (Photos _photos : _stockItems.getPhotoLists()) {
                        if (_photos.getOriginalPhotoBlob() != null && _photos.getThumbnailPhotoBlob() != null) {
                            Photos photos = new Photos();

                            photos.originalPhotoBlob(_photos.getOriginalPhotoBlob());
                            photos.originalPhotoBlobContentType(_photos.getOriginalPhotoBlobContentType());

                            photos.thumbnailPhotoBlob(_photos.getThumbnailPhotoBlob());
                            photos.thumbnailPhotoBlobContentType(_photos.getThumbnailPhotoBlobContentType());

                            photos.setStockItem(stockItems);
                            stockItems.getPhotoLists().add(photos);
                        }
                    }
                    saveProduct.getStockItemLists().add(stockItems);
                }
            }

            saveProduct = productsExtendRepository.save(saveProduct);
            int index = 0;
            for (StockItems _stockItems : saveProduct.getStockItemLists()) {
                _stockItems.setThumbnailUrl(serverUrl + "/photos-extend/stockitem/" + _stockItems.getId() + "/thumbnail");
            }
            String _productnumber = saveProduct.getProductName().replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
            _productnumber = _productnumber.length() > 8 ? _productnumber.substring(0, 8) : _productnumber;
            _productnumber = _productnumber + "-" + saveProduct.getId();
            saveProduct.setProductNumber(_productnumber);
            productsExtendRepository.save(saveProduct);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return productsMapper.toDto(saveProduct);
    }

    public List<Long> getProductIdsBySupplier(Long supplierId) {
        return productsExtendFilterRepository.findIdsBySupplier(supplierId);
    }
}
