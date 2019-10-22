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
 * Test class for the AddressesExtendResource REST controller.
 *
 * @see AddressesExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class AddressesExtendResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        AddressesExtendResource addressesExtendResource = new AddressesExtendResource();
        restMockMvc = MockMvcBuilders
            .standaloneSetup(addressesExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/addresses-extend/default-action"))
            .andExpect(status().isOk());
    }
}
