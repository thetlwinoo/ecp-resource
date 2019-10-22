package com.resource.server.service;

import com.resource.server.service.dto.ProductAttributeDTO;

import java.security.Principal;
import java.util.List;

public interface ProductAttributeExtendService {
    List<ProductAttributeDTO> getAllProductAttributes(Long attributeSetId, Principal principal);
}
