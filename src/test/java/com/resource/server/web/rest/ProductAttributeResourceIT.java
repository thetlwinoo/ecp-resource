package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.ProductAttribute;
import com.resource.server.domain.ProductAttributeSet;
import com.resource.server.domain.Suppliers;
import com.resource.server.repository.ProductAttributeRepository;
import com.resource.server.service.ProductAttributeService;
import com.resource.server.service.dto.ProductAttributeDTO;
import com.resource.server.service.mapper.ProductAttributeMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ProductAttributeCriteria;
import com.resource.server.service.ProductAttributeQueryService;

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
 * Integration tests for the {@link ProductAttributeResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductAttributeResourceIT {

    private static final String DEFAULT_PRODUCT_ATTRIBUTE_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_ATTRIBUTE_VALUE = "BBBBBBBBBB";

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductAttributeMapper productAttributeMapper;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Autowired
    private ProductAttributeQueryService productAttributeQueryService;

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

    private MockMvc restProductAttributeMockMvc;

    private ProductAttribute productAttribute;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductAttributeResource productAttributeResource = new ProductAttributeResource(productAttributeService, productAttributeQueryService);
        this.restProductAttributeMockMvc = MockMvcBuilders.standaloneSetup(productAttributeResource)
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
    public static ProductAttribute createEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .productAttributeValue(DEFAULT_PRODUCT_ATTRIBUTE_VALUE);
        return productAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAttribute createUpdatedEntity(EntityManager em) {
        ProductAttribute productAttribute = new ProductAttribute()
            .productAttributeValue(UPDATED_PRODUCT_ATTRIBUTE_VALUE);
        return productAttribute;
    }

    @BeforeEach
    public void initTest() {
        productAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductAttribute() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getProductAttributeValue()).isEqualTo(DEFAULT_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void createProductAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute with an existing ID
        productAttribute.setId(1L);
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductAttributeValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAttributeRepository.findAll().size();
        // set the field null
        productAttribute.setProductAttributeValue(null);

        // Create the ProductAttribute, which fails.
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        restProductAttributeMockMvc.perform(post("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductAttributes() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValue").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE)));
    }
    
    @Test
    @Transactional
    public void getProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", productAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productAttribute.getId().intValue()))
            .andExpect(jsonPath("$.productAttributeValue").value(DEFAULT_PRODUCT_ATTRIBUTE_VALUE));
    }

    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeValueIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where productAttributeValue equals to DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldBeFound("productAttributeValue.equals=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the productAttributeList where productAttributeValue equals to UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldNotBeFound("productAttributeValue.equals=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where productAttributeValue not equals to DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldNotBeFound("productAttributeValue.notEquals=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the productAttributeList where productAttributeValue not equals to UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldBeFound("productAttributeValue.notEquals=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeValueIsInShouldWork() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where productAttributeValue in DEFAULT_PRODUCT_ATTRIBUTE_VALUE or UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldBeFound("productAttributeValue.in=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE + "," + UPDATED_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the productAttributeList where productAttributeValue equals to UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldNotBeFound("productAttributeValue.in=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where productAttributeValue is not null
        defaultProductAttributeShouldBeFound("productAttributeValue.specified=true");

        // Get all the productAttributeList where productAttributeValue is null
        defaultProductAttributeShouldNotBeFound("productAttributeValue.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeValueContainsSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where productAttributeValue contains DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldBeFound("productAttributeValue.contains=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the productAttributeList where productAttributeValue contains UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldNotBeFound("productAttributeValue.contains=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeValueNotContainsSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        // Get all the productAttributeList where productAttributeValue does not contain DEFAULT_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldNotBeFound("productAttributeValue.doesNotContain=" + DEFAULT_PRODUCT_ATTRIBUTE_VALUE);

        // Get all the productAttributeList where productAttributeValue does not contain UPDATED_PRODUCT_ATTRIBUTE_VALUE
        defaultProductAttributeShouldBeFound("productAttributeValue.doesNotContain=" + UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }


    @Test
    @Transactional
    public void getAllProductAttributesByProductAttributeSetIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);
        ProductAttributeSet productAttributeSet = ProductAttributeSetResourceIT.createEntity(em);
        em.persist(productAttributeSet);
        em.flush();
        productAttribute.setProductAttributeSet(productAttributeSet);
        productAttributeRepository.saveAndFlush(productAttribute);
        Long productAttributeSetId = productAttributeSet.getId();

        // Get all the productAttributeList where productAttributeSet equals to productAttributeSetId
        defaultProductAttributeShouldBeFound("productAttributeSetId.equals=" + productAttributeSetId);

        // Get all the productAttributeList where productAttributeSet equals to productAttributeSetId + 1
        defaultProductAttributeShouldNotBeFound("productAttributeSetId.equals=" + (productAttributeSetId + 1));
    }


    @Test
    @Transactional
    public void getAllProductAttributesBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        productAttribute.setSupplier(supplier);
        productAttributeRepository.saveAndFlush(productAttribute);
        Long supplierId = supplier.getId();

        // Get all the productAttributeList where supplier equals to supplierId
        defaultProductAttributeShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productAttributeList where supplier equals to supplierId + 1
        defaultProductAttributeShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductAttributeShouldBeFound(String filter) throws Exception {
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].productAttributeValue").value(hasItem(DEFAULT_PRODUCT_ATTRIBUTE_VALUE)));

        // Check, that the count call also returns 1
        restProductAttributeMockMvc.perform(get("/api/product-attributes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductAttributeShouldNotBeFound(String filter) throws Exception {
        restProductAttributeMockMvc.perform(get("/api/product-attributes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductAttributeMockMvc.perform(get("/api/product-attributes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProductAttribute() throws Exception {
        // Get the productAttribute
        restProductAttributeMockMvc.perform(get("/api/product-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Update the productAttribute
        ProductAttribute updatedProductAttribute = productAttributeRepository.findById(productAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedProductAttribute are not directly saved in db
        em.detach(updatedProductAttribute);
        updatedProductAttribute
            .productAttributeValue(UPDATED_PRODUCT_ATTRIBUTE_VALUE);
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(updatedProductAttribute);

        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
        ProductAttribute testProductAttribute = productAttributeList.get(productAttributeList.size() - 1);
        assertThat(testProductAttribute.getProductAttributeValue()).isEqualTo(UPDATED_PRODUCT_ATTRIBUTE_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductAttribute() throws Exception {
        int databaseSizeBeforeUpdate = productAttributeRepository.findAll().size();

        // Create the ProductAttribute
        ProductAttributeDTO productAttributeDTO = productAttributeMapper.toDto(productAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAttributeMockMvc.perform(put("/api/product-attributes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductAttribute in the database
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductAttribute() throws Exception {
        // Initialize the database
        productAttributeRepository.saveAndFlush(productAttribute);

        int databaseSizeBeforeDelete = productAttributeRepository.findAll().size();

        // Delete the productAttribute
        restProductAttributeMockMvc.perform(delete("/api/product-attributes/{id}", productAttribute.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAttribute> productAttributeList = productAttributeRepository.findAll();
        assertThat(productAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttribute.class);
        ProductAttribute productAttribute1 = new ProductAttribute();
        productAttribute1.setId(1L);
        ProductAttribute productAttribute2 = new ProductAttribute();
        productAttribute2.setId(productAttribute1.getId());
        assertThat(productAttribute1).isEqualTo(productAttribute2);
        productAttribute2.setId(2L);
        assertThat(productAttribute1).isNotEqualTo(productAttribute2);
        productAttribute1.setId(null);
        assertThat(productAttribute1).isNotEqualTo(productAttribute2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAttributeDTO.class);
        ProductAttributeDTO productAttributeDTO1 = new ProductAttributeDTO();
        productAttributeDTO1.setId(1L);
        ProductAttributeDTO productAttributeDTO2 = new ProductAttributeDTO();
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
        productAttributeDTO2.setId(productAttributeDTO1.getId());
        assertThat(productAttributeDTO1).isEqualTo(productAttributeDTO2);
        productAttributeDTO2.setId(2L);
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
        productAttributeDTO1.setId(null);
        assertThat(productAttributeDTO1).isNotEqualTo(productAttributeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productAttributeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productAttributeMapper.fromId(null)).isNull();
    }
}
