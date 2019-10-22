package com.resource.server.web.rest;

import com.resource.server.service.ProductOptionExtendService;
import com.resource.server.service.dto.ProductOptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

/**
 * ProductOptionExtendResource controller
 */
@RestController
@RequestMapping("/api/product-option-extend")
public class ProductOptionExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProductOptionExtendResource.class);
    private final ProductOptionExtendService productOptionExtendService;

    public ProductOptionExtendResource(ProductOptionExtendService productOptionExtendService) {
        this.productOptionExtendService = productOptionExtendService;
    }

    @GetMapping("/product-option-extend")
    public List<ProductOptionDTO> getAllProductOptions(@RequestParam(value = "optionSetId", required = true) Long optionSetId, Principal principal) {
        log.debug("REST request to get all ProductAttributes");
        return productOptionExtendService.getAllProductOptions(optionSetId, principal);
    }

}
