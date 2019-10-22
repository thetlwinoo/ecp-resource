package com.resource.server.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CompareProductsExtendResource controller
 */
@RestController
@RequestMapping("/api/compare-products-extend")
public class CompareProductsExtendResource {

    private final Logger log = LoggerFactory.getLogger(CompareProductsExtendResource.class);

    /**
    * GET defaultAction
    */
    @GetMapping("/default-action")
    public String defaultAction() {
        return "defaultAction";
    }

}
