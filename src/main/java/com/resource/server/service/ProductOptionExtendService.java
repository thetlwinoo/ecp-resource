package com.resource.server.service;

import com.resource.server.service.dto.ProductOptionDTO;

import java.security.Principal;
import java.util.List;

public interface ProductOptionExtendService {
    List<ProductOptionDTO> getAllProductOptions(Long optionSetId, Principal principal);
}
