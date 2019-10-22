package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.repository.ProductsExtendRepository;
import com.resource.server.service.ProductsExtendService;
import com.resource.server.service.ProductsQueryService;
import com.resource.server.service.ProductsService;
import com.resource.server.service.StockItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ProductsExtendResource REST controller.
 *
 * @see ProductsExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductsExtendResourceIT {

    private MockMvc restMockMvc;
    private final ProductsExtendService productExtendService;
    private final ProductsService productsService;
    private final ProductsQueryService productsQueryService;
    private final ProductsExtendRepository productsExtendRepository;
    private final StockItemsService stockItemsService;

    public ProductsExtendResourceIT(ProductsExtendService productExtendService, ProductsService productsService, ProductsQueryService productsQueryService, ProductsExtendRepository productsExtendRepository, StockItemsService stockItemsService) {
        this.productExtendService = productExtendService;
        this.productsService = productsService;
        this.productsQueryService = productsQueryService;
        this.productsExtendRepository = productsExtendRepository;
        this.stockItemsService = stockItemsService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ProductsExtendResource productsExtendResource = new ProductsExtendResource(productExtendService, productsService, productsQueryService, productsExtendRepository, stockItemsService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(productsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/products-extend/default-action"))
            .andExpect(status().isOk());
    }
}
