package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.ProductOption;
import com.resource.server.domain.ProductOptionSet;
import com.resource.server.domain.Suppliers;
import com.resource.server.repository.ProductOptionRepository;
import com.resource.server.service.ProductOptionService;
import com.resource.server.service.dto.ProductOptionDTO;
import com.resource.server.service.mapper.ProductOptionMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ProductOptionCriteria;
import com.resource.server.service.ProductOptionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductOptionResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductOptionResourceIT {

    private static final String DEFAULT_PRODUCT_OPTION_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    @Autowired
    private ProductOptionService productOptionService;

    @Autowired
    private ProductOptionQueryService productOptionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductOptionMockMvc;

    private ProductOption productOption;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductOptionResource productOptionResource = new ProductOptionResource(productOptionService, productOptionQueryService);
        this.restProductOptionMockMvc = MockMvcBuilders.standaloneSetup(productOptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOption createEntity(EntityManager em) {
        ProductOption productOption = new ProductOption()
            .productOptionValue(DEFAULT_PRODUCT_OPTION_VALUE);
        return productOption;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOption createUpdatedEntity(EntityManager em) {
        ProductOption productOption = new ProductOption()
            .productOptionValue(UPDATED_PRODUCT_OPTION_VALUE);
        return productOption;
    }

    @BeforeEach
    public void initTest() {
        productOption = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOption() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);
        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getProductOptionValue()).isEqualTo(DEFAULT_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void createProductOptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // Create the ProductOption with an existing ID
        productOption.setId(1L);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductOptionValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionRepository.findAll().size();
        // set the field null
        productOption.setProductOptionValue(null);

        // Create the ProductOption, which fails.
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        restProductOptionMockMvc.perform(post("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isBadRequest());

        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptions() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList
        restProductOptionMockMvc.perform(get("/api/product-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionValue").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE)));
    }
    
    @Test
    @Transactional
    public void getProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get the productOption
        restProductOptionMockMvc.perform(get("/api/product-options/{id}", productOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOption.getId().intValue()))
            .andExpect(jsonPath("$.productOptionValue").value(DEFAULT_PRODUCT_OPTION_VALUE));
    }

    @Test
    @Transactional
    public void getAllProductOptionsByProductOptionValueIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where productOptionValue equals to DEFAULT_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldBeFound("productOptionValue.equals=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the productOptionList where productOptionValue equals to UPDATED_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldNotBeFound("productOptionValue.equals=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionsByProductOptionValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where productOptionValue not equals to DEFAULT_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldNotBeFound("productOptionValue.notEquals=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the productOptionList where productOptionValue not equals to UPDATED_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldBeFound("productOptionValue.notEquals=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionsByProductOptionValueIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where productOptionValue in DEFAULT_PRODUCT_OPTION_VALUE or UPDATED_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldBeFound("productOptionValue.in=" + DEFAULT_PRODUCT_OPTION_VALUE + "," + UPDATED_PRODUCT_OPTION_VALUE);

        // Get all the productOptionList where productOptionValue equals to UPDATED_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldNotBeFound("productOptionValue.in=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionsByProductOptionValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where productOptionValue is not null
        defaultProductOptionShouldBeFound("productOptionValue.specified=true");

        // Get all the productOptionList where productOptionValue is null
        defaultProductOptionShouldNotBeFound("productOptionValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductOptionsByProductOptionValueContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where productOptionValue contains DEFAULT_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldBeFound("productOptionValue.contains=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the productOptionList where productOptionValue contains UPDATED_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldNotBeFound("productOptionValue.contains=" + UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionsByProductOptionValueNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where productOptionValue does not contain DEFAULT_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldNotBeFound("productOptionValue.doesNotContain=" + DEFAULT_PRODUCT_OPTION_VALUE);

        // Get all the productOptionList where productOptionValue does not contain UPDATED_PRODUCT_OPTION_VALUE
        defaultProductOptionShouldBeFound("productOptionValue.doesNotContain=" + UPDATED_PRODUCT_OPTION_VALUE);
    }


    @Test
    @Transactional
    public void getAllProductOptionsByProductOptionSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);
        ProductOptionSet productOptionSet = ProductOptionSetResourceIT.createEntity(em);
        em.persist(productOptionSet);
        em.flush();
        productOption.setProductOptionSet(productOptionSet);
        productOptionRepository.saveAndFlush(productOption);
        Long productOptionSetId = productOptionSet.getId();

        // Get all the productOptionList where productOptionSet equals to productOptionSetId
        defaultProductOptionShouldBeFound("productOptionSetId.equals=" + productOptionSetId);

        // Get all the productOptionList where productOptionSet equals to productOptionSetId + 1
        defaultProductOptionShouldNotBeFound("productOptionSetId.equals=" + (productOptionSetId + 1));
    }


    @Test
    @Transactional
    public void getAllProductOptionsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        productOption.setSupplier(supplier);
        productOptionRepository.saveAndFlush(productOption);
        Long supplierId = supplier.getId();

        // Get all the productOptionList where supplier equals to supplierId
        defaultProductOptionShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productOptionList where supplier equals to supplierId + 1
        defaultProductOptionShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionShouldBeFound(String filter) throws Exception {
        restProductOptionMockMvc.perform(get("/api/product-options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionValue").value(hasItem(DEFAULT_PRODUCT_OPTION_VALUE)));

        // Check, that the count call also returns 1
        restProductOptionMockMvc.perform(get("/api/product-options/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionShouldNotBeFound(String filter) throws Exception {
        restProductOptionMockMvc.perform(get("/api/product-options?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionMockMvc.perform(get("/api/product-options/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductOption() throws Exception {
        // Get the productOption
        restProductOptionMockMvc.perform(get("/api/product-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption
        ProductOption updatedProductOption = productOptionRepository.findById(productOption.getId()).get();
        // Disconnect from session so that the updates on updatedProductOption are not directly saved in db
        em.detach(updatedProductOption);
        updatedProductOption
            .productOptionValue(UPDATED_PRODUCT_OPTION_VALUE);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(updatedProductOption);

        restProductOptionMockMvc.perform(put("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getProductOptionValue()).isEqualTo(UPDATED_PRODUCT_OPTION_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionMockMvc.perform(put("/api/product-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeDelete = productOptionRepository.findAll().size();

        // Delete the productOption
        restProductOptionMockMvc.perform(delete("/api/product-options/{id}", productOption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOption.class);
        ProductOption productOption1 = new ProductOption();
        productOption1.setId(1L);
        ProductOption productOption2 = new ProductOption();
        productOption2.setId(productOption1.getId());
        assertThat(productOption1).isEqualTo(productOption2);
        productOption2.setId(2L);
        assertThat(productOption1).isNotEqualTo(productOption2);
        productOption1.setId(null);
        assertThat(productOption1).isNotEqualTo(productOption2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionDTO.class);
        ProductOptionDTO productOptionDTO1 = new ProductOptionDTO();
        productOptionDTO1.setId(1L);
        ProductOptionDTO productOptionDTO2 = new ProductOptionDTO();
        assertThat(productOptionDTO1).isNotEqualTo(productOptionDTO2);
        productOptionDTO2.setId(productOptionDTO1.getId());
        assertThat(productOptionDTO1).isEqualTo(productOptionDTO2);
        productOptionDTO2.setId(2L);
        assertThat(productOptionDTO1).isNotEqualTo(productOptionDTO2);
        productOptionDTO1.setId(null);
        assertThat(productOptionDTO1).isNotEqualTo(productOptionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productOptionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productOptionMapper.fromId(null)).isNull();
    }
}
