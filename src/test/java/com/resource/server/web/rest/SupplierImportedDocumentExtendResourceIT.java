package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.service.SupplierImportedDocumentExtendService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Test class for the SupplierImportedDocumentExtendResource REST controller.
 *
 * @see SupplierImportedDocumentExtendResource
 */
@SpringBootTest(classes = ResourceApp.class)
public class SupplierImportedDocumentExtendResourceIT {

    private MockMvc restMockMvc;
    private final SupplierImportedDocumentExtendService supplierImportedDocumentExtendService;

    public SupplierImportedDocumentExtendResourceIT(SupplierImportedDocumentExtendService supplierImportedDocumentExtendService) {
        this.supplierImportedDocumentExtendService = supplierImportedDocumentExtendService;
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        SupplierImportedDocumentExtendResource supplierImportedDocumentExtendResource = new SupplierImportedDocumentExtendResource(supplierImportedDocumentExtendService);
        restMockMvc = MockMvcBuilders
            .standaloneSetup(supplierImportedDocumentExtendResource)
            .build();
    }

    /**
     * Test defaultAction
     */
    @Test
    public void testDefaultAction() throws Exception {
        restMockMvc.perform(get("/api/supplier-imported-document-extend/default-action"))
            .andExpect(status().isOk());
    }
}
