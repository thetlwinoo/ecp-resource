package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.ProductAttributeSet;
import com.resource.server.domain.ProductOptionSet;
import com.resource.server.repository.ProductAttributeSetRepository;
import com.resource.server.service.ProductAttributeSetService;
import com.resource.server.service.dto.ProductAttributeSetDTO;
import com.resource.server.service.mapper.ProductAttributeSetMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ProductAttributeSetCriteria;
import com.resource.server.service.ProductAttributeSetQueryService;

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
 * Integration tests for the {@link ProductAttributeSetResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductAttributeSetResourceIT {

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_SET_NAME = "BBBBBBBBBB";

    @Autowired
    private ProductAttributeSetRepository productAttributeSetRepository;

    @Autowired
    private ProductAttributeSetMapper productAttributeSetMapper;

    @Autowired
    private ProductAttributeSetService productAttributeSetService;

    @Autowired
    private ProductAttributeSetQueryService productAttributeSetQueryService;

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

    private MockMvc restProductAttributeSetMockMvc;

    private ProductAttributeSet productAttributeSet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAttributeSetResource productAttributeSetResource = new ProductAttributeSetResource(productAttributeSetService, productAttributeSetQueryService);
        this.restProductAttributeSetMockMvc = MockMvcBuilders.standaloneSetup(productAttributeSetResource)
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
    public static ProductAttributeSet createEntity(EntityManager em) {
        ProductAttributeSet productAttributeSet = new ProductAttributeSet()
            .productAttributeSetName(DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME);
        return productAttributeSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttributeSet createUpdatedEntity(EntityManager em) {
        ProductAttributeSet productAttributeSet = new ProductAttributeSet()
            .productAttributeSetName(UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
        return productAttributeSet;
    }

    @BeforeEach
    public void initTest() {
        productAttributeSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttributeSet() throws Exception {
        int databaseSizeBeforeCreate = productAttributeSetRepository.findAll().size();

        // Create the ProductAttributeSet
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);
        restProductAttributeSetMockMvc.perform(post("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttributeSet testProductAttributeSet = productAttributeSetList.get(productAttributeSetList.size() - 1);
        assertThat(testProductAttributeSet.getProductAttributeSetName()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME);
    }

    @Test
    @Transactional
    public void createProductAttributeSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeSetRepository.findAll().size();

        // Create the ProductAttributeSet with an existing ID
        productAttributeSet.setId(1L);
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeSetMockMvc.perform(post("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductAttributeSetNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAttributeSetRepository.findAll().size();
        // set the field null
        productAttributeSet.setProductAttributeSetName(null);

        // Create the ProductAttributeSet, which fails.
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);

        restProductAttributeSetMockMvc.perform(post("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isBadRequest());

        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSets() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttributeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeSetName").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME)));
    }
    
    @Test
    @Transactional
    public void getProductAttributeSet() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get the productAttributeSet
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/{id}", productAttributeSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAttributeSet.getId().intValue()))
            .andExpect(jsonPath("$.productAttributeSetName").value(DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME));
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductAttributeSetNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where productAttributeSetName equals to DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldBeFound("productAttributeSetName.equals=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME);

        // Get all the productAttributeSetList where productAttributeSetName equals to UPDATED_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldNotBeFound("productAttributeSetName.equals=" + UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductAttributeSetNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where productAttributeSetName not equals to DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldNotBeFound("productAttributeSetName.notEquals=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME);

        // Get all the productAttributeSetList where productAttributeSetName not equals to UPDATED_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldBeFound("productAttributeSetName.notEquals=" + UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductAttributeSetNameIsInShouldWork() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where productAttributeSetName in DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME or UPDATED_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldBeFound("productAttributeSetName.in=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME + "," + UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);

        // Get all the productAttributeSetList where productAttributeSetName equals to UPDATED_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldNotBeFound("productAttributeSetName.in=" + UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductAttributeSetNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where productAttributeSetName is not null
        defaultProductAttributeSetShouldBeFound("productAttributeSetName.specified=true");

        // Get all the productAttributeSetList where productAttributeSetName is null
        defaultProductAttributeSetShouldNotBeFound("productAttributeSetName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductAttributeSetsByProductAttributeSetNameContainsSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where productAttributeSetName contains DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldBeFound("productAttributeSetName.contains=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME);

        // Get all the productAttributeSetList where productAttributeSetName contains UPDATED_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldNotBeFound("productAttributeSetName.contains=" + UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
    }

    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductAttributeSetNameNotContainsSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        // Get all the productAttributeSetList where productAttributeSetName does not contain DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldNotBeFound("productAttributeSetName.doesNotContain=" + DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME);

        // Get all the productAttributeSetList where productAttributeSetName does not contain UPDATED_PRODUCT_ATTRIBUTE_SET_NAME
        defaultProductAttributeSetShouldBeFound("productAttributeSetName.doesNotContain=" + UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
    }


    @Test
    @Transactional
    public void getAllProductAttributeSetsByProductOptionSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);
        ProductOptionSet productOptionSet = ProductOptionSetResourceIT.createEntity(em);
        em.persist(productOptionSet);
        em.flush();
        productAttributeSet.setProductOptionSet(productOptionSet);
        productAttributeSetRepository.saveAndFlush(productAttributeSet);
        Long productOptionSetId = productOptionSet.getId();

        // Get all the productAttributeSetList where productOptionSet equals to productOptionSetId
        defaultProductAttributeSetShouldBeFound("productOptionSetId.equals=" + productOptionSetId);

        // Get all the productAttributeSetList where productOptionSet equals to productOptionSetId + 1
        defaultProductAttributeSetShouldNotBeFound("productOptionSetId.equals=" + (productOptionSetId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductAttributeSetShouldBeFound(String filter) throws Exception {
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttributeSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeSetName").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_SET_NAME)));

        // Check, that the count call also returns 1
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductAttributeSetShouldNotBeFound(String filter) throws Exception {
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductAttributeSet() throws Exception {
        // Get the productAttributeSet
        restProductAttributeSetMockMvc.perform(get("/api/product-attribute-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttributeSet() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        int databaseSizeBeforeUpdate = productAttributeSetRepository.findAll().size();

        // Update the productAttributeSet
        ProductAttributeSet updatedProductAttributeSet = productAttributeSetRepository.findById(productAttributeSet.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttributeSet are not directly saved in db
        em.detach(updatedProductAttributeSet);
        updatedProductAttributeSet
            .productAttributeSetName(UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(updatedProductAttributeSet);

        restProductAttributeSetMockMvc.perform(put("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeUpdate);
        ProductAttributeSet testProductAttributeSet = productAttributeSetList.get(productAttributeSetList.size() - 1);
        assertThat(testProductAttributeSet.getProductAttributeSetName()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_SET_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttributeSet() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeSetRepository.findAll().size();

        // Create the ProductAttributeSet
        ProductAttributeSetDTO productAttributeSetDTO = productAttributeSetMapper.toDto(productAttributeSet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeSetMockMvc.perform(put("/api/product-attribute-sets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeSetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttributeSet in the database
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttributeSet() throws Exception {
        // Initialize the database
        productAttributeSetRepository.saveAndFlush(productAttributeSet);

        int databaseSizeBeforeDelete = productAttributeSetRepository.findAll().size();

        // Delete the productAttributeSet
        restProductAttributeSetMockMvc.perform(delete("/api/product-attribute-sets/{id}", productAttributeSet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttributeSet> productAttributeSetList = productAttributeSetRepository.findAll();
        assertThat(productAttributeSetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeSet.class);
        ProductAttributeSet productAttributeSet1 = new ProductAttributeSet();
        productAttributeSet1.setId(1L);
        ProductAttributeSet productAttributeSet2 = new ProductAttributeSet();
        productAttributeSet2.setId(productAttributeSet1.getId());
        assertThat(productAttributeSet1).isEqualTo(productAttributeSet2);
        productAttributeSet2.setId(2L);
        assertThat(productAttributeSet1).isNotEqualTo(productAttributeSet2);
        productAttributeSet1.setId(null);
        assertThat(productAttributeSet1).isNotEqualTo(productAttributeSet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeSetDTO.class);
        ProductAttributeSetDTO productAttributeSetDTO1 = new ProductAttributeSetDTO();
        productAttributeSetDTO1.setId(1L);
        ProductAttributeSetDTO productAttributeSetDTO2 = new ProductAttributeSetDTO();
        assertThat(productAttributeSetDTO1).isNotEqualTo(productAttributeSetDTO2);
        productAttributeSetDTO2.setId(productAttributeSetDTO1.getId());
        assertThat(productAttributeSetDTO1).isEqualTo(productAttributeSetDTO2);
        productAttributeSetDTO2.setId(2L);
        assertThat(productAttributeSetDTO1).isNotEqualTo(productAttributeSetDTO2);
        productAttributeSetDTO1.setId(null);
        assertThat(productAttributeSetDTO1).isNotEqualTo(productAttributeSetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productAttributeSetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productAttributeSetMapper.fromId(null)).isNull();
    }
}
