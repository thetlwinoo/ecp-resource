package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.ProductOptionSet;
import com.resource.server.repository.ProductOptionSetRepository;
import com.resource.server.service.ProductOptionSetService;
import com.resource.server.service.dto.ProductOptionSetDTO;
import com.resource.server.service.mapper.ProductOptionSetMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ProductOptionSetCriteria;
import com.resource.server.service.ProductOptionSetQueryService;

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
 * Integration tests for the {@link ProductOptionSetResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductOptionSetResourceIT {

    private static final String DEFAULT_PRODUCT_OPTION_SET_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_OPTION_SET_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductOptionSetRepository productOptionSetRepository;

    @Autowired
    private ProductOptionSetMapper productOptionSetMapper;

    @Autowired
    private ProductOptionSetService productOptionSetService;

    @Autowired
    private ProductOptionSetQueryService productOptionSetQueryService;

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

    private MockMvc restProductOptionSetMockMvc;

    private ProductOptionSet productOptionSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductOptionSetResource productOptionSetResource = new ProductOptionSetResource(productOptionSetService, productOptionSetQueryService);
        this.restProductOptionSetMockMvc = MockMvcBuilders.standaloneSetup(productOptionSetResource)
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
    public static ProductOptionSet createEntity(EntityManager em) {
        ProductOptionSet productOptionSet = new ProductOptionSet()
            .productOptionSetValue(DEFAULT_PRODUCT_OPTION_SET_VALUE);
        return productOptionSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionSet createUpdatedEntity(EntityManager em) {
        ProductOptionSet productOptionSet = new ProductOptionSet()
            .productOptionSetValue(UPDATED_PRODUCT_OPTION_SET_VALUE);
        return productOptionSet;
    }

    @BeforeEach
    public void initTest() {
        productOptionSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductOptionSet() throws Exception {
        int databaseSizeBeforeCreate = productOptionSetRepository.findAll().size();

        // Create the ProductOptionSet
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);
        restProductOptionSetMockMvc.perform(post("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionSet testProductOptionSet = productOptionSetList.get(productOptionSetList.size() - 1);
        assertThat(testProductOptionSet.getProductOptionSetValue()).isEqualTo(DEFAULT_PRODUCT_OPTION_SET_VALUE);
    }

    @Test
    @Transactional
    public void createProductOptionSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productOptionSetRepository.findAll().size();

        // Create the ProductOptionSet with an existing ID
        productOptionSet.setId(1L);
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionSetMockMvc.perform(post("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductOptionSetValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productOptionSetRepository.findAll().size();
        // set the field null
        productOptionSet.setProductOptionSetValue(null);

        // Create the ProductOptionSet, which fails.
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);

        restProductOptionSetMockMvc.perform(post("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isBadRequest());

        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductOptionSets() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionSetValue").value(hasItem(DEFAULT_PRODUCT_OPTION_SET_VALUE)));
    }
    
    @Test
    @Transactional
    public void getProductOptionSet() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get the productOptionSet
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/{id}", productOptionSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionSet.getId().intValue()))
            .andExpect(jsonPath("$.productOptionSetValue").value(DEFAULT_PRODUCT_OPTION_SET_VALUE));
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByProductOptionSetValueIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where productOptionSetValue equals to DEFAULT_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldBeFound("productOptionSetValue.equals=" + DEFAULT_PRODUCT_OPTION_SET_VALUE);

        // Get all the productOptionSetList where productOptionSetValue equals to UPDATED_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldNotBeFound("productOptionSetValue.equals=" + UPDATED_PRODUCT_OPTION_SET_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByProductOptionSetValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where productOptionSetValue not equals to DEFAULT_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldNotBeFound("productOptionSetValue.notEquals=" + DEFAULT_PRODUCT_OPTION_SET_VALUE);

        // Get all the productOptionSetList where productOptionSetValue not equals to UPDATED_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldBeFound("productOptionSetValue.notEquals=" + UPDATED_PRODUCT_OPTION_SET_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByProductOptionSetValueIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where productOptionSetValue in DEFAULT_PRODUCT_OPTION_SET_VALUE or UPDATED_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldBeFound("productOptionSetValue.in=" + DEFAULT_PRODUCT_OPTION_SET_VALUE + "," + UPDATED_PRODUCT_OPTION_SET_VALUE);

        // Get all the productOptionSetList where productOptionSetValue equals to UPDATED_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldNotBeFound("productOptionSetValue.in=" + UPDATED_PRODUCT_OPTION_SET_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByProductOptionSetValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where productOptionSetValue is not null
        defaultProductOptionSetShouldBeFound("productOptionSetValue.specified=true");

        // Get all the productOptionSetList where productOptionSetValue is null
        defaultProductOptionSetShouldNotBeFound("productOptionSetValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductOptionSetsByProductOptionSetValueContainsSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where productOptionSetValue contains DEFAULT_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldBeFound("productOptionSetValue.contains=" + DEFAULT_PRODUCT_OPTION_SET_VALUE);

        // Get all the productOptionSetList where productOptionSetValue contains UPDATED_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldNotBeFound("productOptionSetValue.contains=" + UPDATED_PRODUCT_OPTION_SET_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductOptionSetsByProductOptionSetValueNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        // Get all the productOptionSetList where productOptionSetValue does not contain DEFAULT_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldNotBeFound("productOptionSetValue.doesNotContain=" + DEFAULT_PRODUCT_OPTION_SET_VALUE);

        // Get all the productOptionSetList where productOptionSetValue does not contain UPDATED_PRODUCT_OPTION_SET_VALUE
        defaultProductOptionSetShouldBeFound("productOptionSetValue.doesNotContain=" + UPDATED_PRODUCT_OPTION_SET_VALUE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionSetShouldBeFound(String filter) throws Exception {
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productOptionSetValue").value(hasItem(DEFAULT_PRODUCT_OPTION_SET_VALUE)));

        // Check, that the count call also returns 1
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionSetShouldNotBeFound(String filter) throws Exception {
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductOptionSet() throws Exception {
        // Get the productOptionSet
        restProductOptionSetMockMvc.perform(get("/api/product-option-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductOptionSet() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        int databaseSizeBeforeUpdate = productOptionSetRepository.findAll().size();

        // Update the productOptionSet
        ProductOptionSet updatedProductOptionSet = productOptionSetRepository.findById(productOptionSet.getId()).get();
        // Disconnect from session so that the updates on updatedProductOptionSet are not directly saved in db
        em.detach(updatedProductOptionSet);
        updatedProductOptionSet
            .productOptionSetValue(UPDATED_PRODUCT_OPTION_SET_VALUE);
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(updatedProductOptionSet);

        restProductOptionSetMockMvc.perform(put("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isOk());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionSet testProductOptionSet = productOptionSetList.get(productOptionSetList.size() - 1);
        assertThat(testProductOptionSet.getProductOptionSetValue()).isEqualTo(UPDATED_PRODUCT_OPTION_SET_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductOptionSet() throws Exception {
        int databaseSizeBeforeUpdate = productOptionSetRepository.findAll().size();

        // Create the ProductOptionSet
        ProductOptionSetDTO productOptionSetDTO = productOptionSetMapper.toDto(productOptionSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionSetMockMvc.perform(put("/api/product-option-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productOptionSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionSet in the database
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductOptionSet() throws Exception {
        // Initialize the database
        productOptionSetRepository.saveAndFlush(productOptionSet);

        int databaseSizeBeforeDelete = productOptionSetRepository.findAll().size();

        // Delete the productOptionSet
        restProductOptionSetMockMvc.perform(delete("/api/product-option-sets/{id}", productOptionSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOptionSet> productOptionSetList = productOptionSetRepository.findAll();
        assertThat(productOptionSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionSet.class);
        ProductOptionSet productOptionSet1 = new ProductOptionSet();
        productOptionSet1.setId(1L);
        ProductOptionSet productOptionSet2 = new ProductOptionSet();
        productOptionSet2.setId(productOptionSet1.getId());
        assertThat(productOptionSet1).isEqualTo(productOptionSet2);
        productOptionSet2.setId(2L);
        assertThat(productOptionSet1).isNotEqualTo(productOptionSet2);
        productOptionSet1.setId(null);
        assertThat(productOptionSet1).isNotEqualTo(productOptionSet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionSetDTO.class);
        ProductOptionSetDTO productOptionSetDTO1 = new ProductOptionSetDTO();
        productOptionSetDTO1.setId(1L);
        ProductOptionSetDTO productOptionSetDTO2 = new ProductOptionSetDTO();
        assertThat(productOptionSetDTO1).isNotEqualTo(productOptionSetDTO2);
        productOptionSetDTO2.setId(productOptionSetDTO1.getId());
        assertThat(productOptionSetDTO1).isEqualTo(productOptionSetDTO2);
        productOptionSetDTO2.setId(2L);
        assertThat(productOptionSetDTO1).isNotEqualTo(productOptionSetDTO2);
        productOptionSetDTO1.setId(null);
        assertThat(productOptionSetDTO1).isNotEqualTo(productOptionSetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productOptionSetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productOptionSetMapper.fromId(null)).isNull();
    }
}
