package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.BatchUploadService;
import com.resource.server.service.ProductsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the BatchUploadResource REST controller.
 *
 * @see BatchUploadResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class BatchUploadResourceIT {

    private MockMvc restMockMvc;
    private final ProductsService productsService;
    private final BatchUploadService batchUploadService;

    public BatchUploadResourceIT(ProductsService productsService, BatchUploadService batchUploadService) {
        this.productsService = productsService;
        this.batchUploadService = batchUploadService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        BatchUploadResource batchUploadResource = new BatchUploadResource(productsService, batchUploadService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(batchUploadResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/batch-upload/default-action"))
            .andExpect(status().isOk());
    }
}
