package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.SupplierCategories;
import com.resource.server.repository.SupplierCategoriesRepository;
import com.resource.server.service.SupplierCategoriesService;
import com.resource.server.service.dto.SupplierCategoriesDTO;
import com.resource.server.service.mapper.SupplierCategoriesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.SupplierCategoriesCriteria;
import com.resource.server.service.SupplierCategoriesQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.resource.server.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupplierCategoriesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class SupplierCategoriesResourceIT {

    private static final String DEFAULT_SUPPLIER_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_CATEGORY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private SupplierCategoriesRepository supplierCategoriesRepository;

    @Autowired
    private SupplierCategoriesMapper supplierCategoriesMapper;

    @Autowired
    private SupplierCategoriesService supplierCategoriesService;

    @Autowired
    private SupplierCategoriesQueryService supplierCategoriesQueryService;

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

    private MockMvc restSupplierCategoriesMockMvc;

    private SupplierCategories supplierCategories;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierCategoriesResource supplierCategoriesResource = new SupplierCategoriesResource(supplierCategoriesService, supplierCategoriesQueryService);
        this.restSupplierCategoriesMockMvc = MockMvcBuilders.standaloneSetup(supplierCategoriesResource)
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
    public static SupplierCategories createEntity(EntityManager em) {
        SupplierCategories supplierCategories = new SupplierCategories()
            .supplierCategoryName(DEFAULT_SUPPLIER_CATEGORY_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return supplierCategories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierCategories createUpdatedEntity(EntityManager em) {
        SupplierCategories supplierCategories = new SupplierCategories()
            .supplierCategoryName(UPDATED_SUPPLIER_CATEGORY_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return supplierCategories;
    }

    @BeforeEach
    public void initTest() {
        supplierCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierCategories() throws Exception {
        int databaseSizeBeforeCreate = supplierCategoriesRepository.findAll().size();

        // Create the SupplierCategories
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);
        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierCategories testSupplierCategories = supplierCategoriesList.get(supplierCategoriesList.size() - 1);
        assertThat(testSupplierCategories.getSupplierCategoryName()).isEqualTo(DEFAULT_SUPPLIER_CATEGORY_NAME);
        assertThat(testSupplierCategories.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSupplierCategories.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createSupplierCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierCategoriesRepository.findAll().size();

        // Create the SupplierCategories with an existing ID
        supplierCategories.setId(1L);
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSupplierCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoriesRepository.findAll().size();
        // set the field null
        supplierCategories.setSupplierCategoryName(null);

        // Create the SupplierCategories, which fails.
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoriesRepository.findAll().size();
        // set the field null
        supplierCategories.setValidFrom(null);

        // Create the SupplierCategories, which fails.
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierCategoriesRepository.findAll().size();
        // set the field null
        supplierCategories.setValidTo(null);

        // Create the SupplierCategories, which fails.
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        restSupplierCategoriesMockMvc.perform(post("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierCategoryName").value(hasItem(DEFAULT_SUPPLIER_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get the supplierCategories
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/{id}", supplierCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierCategories.getId().intValue()))
            .andExpect(jsonPath("$.supplierCategoryName").value(DEFAULT_SUPPLIER_CATEGORY_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesBySupplierCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where supplierCategoryName equals to DEFAULT_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldBeFound("supplierCategoryName.equals=" + DEFAULT_SUPPLIER_CATEGORY_NAME);

        // Get all the supplierCategoriesList where supplierCategoryName equals to UPDATED_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldNotBeFound("supplierCategoryName.equals=" + UPDATED_SUPPLIER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesBySupplierCategoryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where supplierCategoryName not equals to DEFAULT_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldNotBeFound("supplierCategoryName.notEquals=" + DEFAULT_SUPPLIER_CATEGORY_NAME);

        // Get all the supplierCategoriesList where supplierCategoryName not equals to UPDATED_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldBeFound("supplierCategoryName.notEquals=" + UPDATED_SUPPLIER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesBySupplierCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where supplierCategoryName in DEFAULT_SUPPLIER_CATEGORY_NAME or UPDATED_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldBeFound("supplierCategoryName.in=" + DEFAULT_SUPPLIER_CATEGORY_NAME + "," + UPDATED_SUPPLIER_CATEGORY_NAME);

        // Get all the supplierCategoriesList where supplierCategoryName equals to UPDATED_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldNotBeFound("supplierCategoryName.in=" + UPDATED_SUPPLIER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesBySupplierCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where supplierCategoryName is not null
        defaultSupplierCategoriesShouldBeFound("supplierCategoryName.specified=true");

        // Get all the supplierCategoriesList where supplierCategoryName is null
        defaultSupplierCategoriesShouldNotBeFound("supplierCategoryName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierCategoriesBySupplierCategoryNameContainsSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where supplierCategoryName contains DEFAULT_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldBeFound("supplierCategoryName.contains=" + DEFAULT_SUPPLIER_CATEGORY_NAME);

        // Get all the supplierCategoriesList where supplierCategoryName contains UPDATED_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldNotBeFound("supplierCategoryName.contains=" + UPDATED_SUPPLIER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesBySupplierCategoryNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where supplierCategoryName does not contain DEFAULT_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldNotBeFound("supplierCategoryName.doesNotContain=" + DEFAULT_SUPPLIER_CATEGORY_NAME);

        // Get all the supplierCategoriesList where supplierCategoryName does not contain UPDATED_SUPPLIER_CATEGORY_NAME
        defaultSupplierCategoriesShouldBeFound("supplierCategoryName.doesNotContain=" + UPDATED_SUPPLIER_CATEGORY_NAME);
    }


    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom equals to DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom not equals to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom is not null
        defaultSupplierCategoriesShouldBeFound("validFrom.specified=true");

        // Get all the supplierCategoriesList where validFrom is null
        defaultSupplierCategoriesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom is less than DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom is less than UPDATED_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validFrom is greater than DEFAULT_VALID_FROM
        defaultSupplierCategoriesShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the supplierCategoriesList where validFrom is greater than SMALLER_VALID_FROM
        defaultSupplierCategoriesShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo equals to DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo not equals to DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo not equals to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the supplierCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo is not null
        defaultSupplierCategoriesShouldBeFound("validTo.specified=true");

        // Get all the supplierCategoriesList where validTo is null
        defaultSupplierCategoriesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo is less than or equal to SMALLER_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo is less than DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo is less than UPDATED_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierCategoriesByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        // Get all the supplierCategoriesList where validTo is greater than DEFAULT_VALID_TO
        defaultSupplierCategoriesShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the supplierCategoriesList where validTo is greater than SMALLER_VALID_TO
        defaultSupplierCategoriesShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierCategoriesShouldBeFound(String filter) throws Exception {
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierCategoryName").value(hasItem(DEFAULT_SUPPLIER_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierCategoriesShouldNotBeFound(String filter) throws Exception {
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplierCategories() throws Exception {
        // Get the supplierCategories
        restSupplierCategoriesMockMvc.perform(get("/api/supplier-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        int databaseSizeBeforeUpdate = supplierCategoriesRepository.findAll().size();

        // Update the supplierCategories
        SupplierCategories updatedSupplierCategories = supplierCategoriesRepository.findById(supplierCategories.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierCategories are not directly saved in db
        em.detach(updatedSupplierCategories);
        updatedSupplierCategories
            .supplierCategoryName(UPDATED_SUPPLIER_CATEGORY_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(updatedSupplierCategories);

        restSupplierCategoriesMockMvc.perform(put("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeUpdate);
        SupplierCategories testSupplierCategories = supplierCategoriesList.get(supplierCategoriesList.size() - 1);
        assertThat(testSupplierCategories.getSupplierCategoryName()).isEqualTo(UPDATED_SUPPLIER_CATEGORY_NAME);
        assertThat(testSupplierCategories.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSupplierCategories.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierCategories() throws Exception {
        int databaseSizeBeforeUpdate = supplierCategoriesRepository.findAll().size();

        // Create the SupplierCategories
        SupplierCategoriesDTO supplierCategoriesDTO = supplierCategoriesMapper.toDto(supplierCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierCategoriesMockMvc.perform(put("/api/supplier-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierCategories in the database
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierCategories() throws Exception {
        // Initialize the database
        supplierCategoriesRepository.saveAndFlush(supplierCategories);

        int databaseSizeBeforeDelete = supplierCategoriesRepository.findAll().size();

        // Delete the supplierCategories
        restSupplierCategoriesMockMvc.perform(delete("/api/supplier-categories/{id}", supplierCategories.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierCategories> supplierCategoriesList = supplierCategoriesRepository.findAll();
        assertThat(supplierCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierCategories.class);
        SupplierCategories supplierCategories1 = new SupplierCategories();
        supplierCategories1.setId(1L);
        SupplierCategories supplierCategories2 = new SupplierCategories();
        supplierCategories2.setId(supplierCategories1.getId());
        assertThat(supplierCategories1).isEqualTo(supplierCategories2);
        supplierCategories2.setId(2L);
        assertThat(supplierCategories1).isNotEqualTo(supplierCategories2);
        supplierCategories1.setId(null);
        assertThat(supplierCategories1).isNotEqualTo(supplierCategories2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierCategoriesDTO.class);
        SupplierCategoriesDTO supplierCategoriesDTO1 = new SupplierCategoriesDTO();
        supplierCategoriesDTO1.setId(1L);
        SupplierCategoriesDTO supplierCategoriesDTO2 = new SupplierCategoriesDTO();
        assertThat(supplierCategoriesDTO1).isNotEqualTo(supplierCategoriesDTO2);
        supplierCategoriesDTO2.setId(supplierCategoriesDTO1.getId());
        assertThat(supplierCategoriesDTO1).isEqualTo(supplierCategoriesDTO2);
        supplierCategoriesDTO2.setId(2L);
        assertThat(supplierCategoriesDTO1).isNotEqualTo(supplierCategoriesDTO2);
        supplierCategoriesDTO1.setId(null);
        assertThat(supplierCategoriesDTO1).isNotEqualTo(supplierCategoriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplierCategoriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplierCategoriesMapper.fromId(null)).isNull();
    }
}
