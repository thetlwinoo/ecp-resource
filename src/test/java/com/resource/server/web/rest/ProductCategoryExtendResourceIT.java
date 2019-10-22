package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ProductCategoryExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ProductCategoryExtendResource REST controller.
 *
 * @see ProductCategoryExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductCategoryExtendResourceIT {

    private MockMvc restMockMvc;
    private final ProductCategoryExtendService productCategoryExtendService;

    public ProductCategoryExtendResourceIT(ProductCategoryExtendService productCategoryExtendService) {
        this.productCategoryExtendService = productCategoryExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductCategoryExtendResource productCategoryExtendResource = new ProductCategoryExtendResource(productCategoryExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productCategoryExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/product-category-extend/default-action"))
            .andExpect(status().isOk());
    }
}
