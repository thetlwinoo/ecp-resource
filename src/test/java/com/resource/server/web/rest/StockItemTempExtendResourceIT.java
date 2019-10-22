package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.StockItemTempExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the StockItemTempExtendResource REST controller.
 *
 * @see StockItemTempExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class StockItemTempExtendResourceIT {

    private MockMvc restMockMvc;
    private final StockItemTempExtendService stockItemTempExtendService;

    public StockItemTempExtendResourceIT(StockItemTempExtendService stockItemTempExtendService) {
        this.stockItemTempExtendService = stockItemTempExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        StockItemTempExtendResource stockItemTempExtendResource = new StockItemTempExtendResource(stockItemTempExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(stockItemTempExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/stock-item-temp-extend/default-action"))
            .andExpect(status().isOk());
    }
}
