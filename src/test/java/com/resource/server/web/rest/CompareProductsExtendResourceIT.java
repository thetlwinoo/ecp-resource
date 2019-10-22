package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the CompareProductsExtendResource REST controller.
 *
 * @see CompareProductsExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class CompareProductsExtendResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        CompareProductsExtendResource compareProductsExtendResource = new CompareProductsExtendResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(compareProductsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/compare-products-extend/default-action"))
            .andExpect(status().isOk());
    }
}
