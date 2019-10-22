package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.ShoppingCartsExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the ShoppingCartsExtendResource REST controller.
 *
 * @see ShoppingCartsExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class ShoppingCartsExtendResourceIT {

    private MockMvc restMockMvc;
    private final ShoppingCartsExtendService cartService;

    public ShoppingCartsExtendResourceIT(ShoppingCartsExtendService cartService) {
        this.cartService = cartService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ShoppingCartsExtendResource shoppingCartsExtendResource = new ShoppingCartsExtendResource(cartService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(shoppingCartsExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/shopping-carts-extend/default-action"))
            .andExpect(status().isOk());
    }
}
