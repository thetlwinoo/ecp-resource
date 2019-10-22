package com.resource.server.service;

import com.resource.server.service.dto.ProductChoiceDTO;

import java.util.List;

public interface ProductChoiceExtendService {
    List<ProductChoiceDTO> getAllProductChoice(Long categoryId);
}
