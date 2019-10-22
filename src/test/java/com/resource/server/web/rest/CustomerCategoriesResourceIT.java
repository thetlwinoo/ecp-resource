package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.CustomerCategories;
import com.resource.server.repository.CustomerCategoriesRepository;
import com.resource.server.service.CustomerCategoriesService;
import com.resource.server.service.dto.CustomerCategoriesDTO;
import com.resource.server.service.mapper.CustomerCategoriesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.CustomerCategoriesCriteria;
import com.resource.server.service.CustomerCategoriesQueryService;

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
 * Integration tests for the {@link CustomerCategoriesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class CustomerCategoriesResourceIT {

    private static final String DEFAULT_CUSTOMER_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_CATEGORY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private CustomerCategoriesRepository customerCategoriesRepository;

    @Autowired
    private CustomerCategoriesMapper customerCategoriesMapper;

    @Autowired
    private CustomerCategoriesService customerCategoriesService;

    @Autowired
    private CustomerCategoriesQueryService customerCategoriesQueryService;

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

    private MockMvc restCustomerCategoriesMockMvc;

    private CustomerCategories customerCategories;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomerCategoriesResource customerCategoriesResource = new CustomerCategoriesResource(customerCategoriesService, customerCategoriesQueryService);
        this.restCustomerCategoriesMockMvc = MockMvcBuilders.standaloneSetup(customerCategoriesResource)
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
    public static CustomerCategories createEntity(EntityManager em) {
        CustomerCategories customerCategories = new CustomerCategories()
            .customerCategoryName(DEFAULT_CUSTOMER_CATEGORY_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return customerCategories;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerCategories createUpdatedEntity(EntityManager em) {
        CustomerCategories customerCategories = new CustomerCategories()
            .customerCategoryName(UPDATED_CUSTOMER_CATEGORY_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return customerCategories;
    }

    @BeforeEach
    public void initTest() {
        customerCategories = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerCategories() throws Exception {
        int databaseSizeBeforeCreate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);
        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerCategories testCustomerCategories = customerCategoriesList.get(customerCategoriesList.size() - 1);
        assertThat(testCustomerCategories.getCustomerCategoryName()).isEqualTo(DEFAULT_CUSTOMER_CATEGORY_NAME);
        assertThat(testCustomerCategories.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustomerCategories.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCustomerCategoriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories with an existing ID
        customerCategories.setId(1L);
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoriesRepository.findAll().size();
        // set the field null
        customerCategories.setValidFrom(null);

        // Create the CustomerCategories, which fails.
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerCategoriesRepository.findAll().size();
        // set the field null
        customerCategories.setValidTo(null);

        // Create the CustomerCategories, which fails.
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        restCustomerCategoriesMockMvc.perform(post("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerCategoryName").value(hasItem(DEFAULT_CUSTOMER_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get the customerCategories
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/{id}", customerCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customerCategories.getId().intValue()))
            .andExpect(jsonPath("$.customerCategoryName").value(DEFAULT_CUSTOMER_CATEGORY_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCustomerCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where customerCategoryName equals to DEFAULT_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldBeFound("customerCategoryName.equals=" + DEFAULT_CUSTOMER_CATEGORY_NAME);

        // Get all the customerCategoriesList where customerCategoryName equals to UPDATED_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldNotBeFound("customerCategoryName.equals=" + UPDATED_CUSTOMER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCustomerCategoryNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where customerCategoryName not equals to DEFAULT_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldNotBeFound("customerCategoryName.notEquals=" + DEFAULT_CUSTOMER_CATEGORY_NAME);

        // Get all the customerCategoriesList where customerCategoryName not equals to UPDATED_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldBeFound("customerCategoryName.notEquals=" + UPDATED_CUSTOMER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCustomerCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where customerCategoryName in DEFAULT_CUSTOMER_CATEGORY_NAME or UPDATED_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldBeFound("customerCategoryName.in=" + DEFAULT_CUSTOMER_CATEGORY_NAME + "," + UPDATED_CUSTOMER_CATEGORY_NAME);

        // Get all the customerCategoriesList where customerCategoryName equals to UPDATED_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldNotBeFound("customerCategoryName.in=" + UPDATED_CUSTOMER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCustomerCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where customerCategoryName is not null
        defaultCustomerCategoriesShouldBeFound("customerCategoryName.specified=true");

        // Get all the customerCategoriesList where customerCategoryName is null
        defaultCustomerCategoriesShouldNotBeFound("customerCategoryName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerCategoriesByCustomerCategoryNameContainsSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where customerCategoryName contains DEFAULT_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldBeFound("customerCategoryName.contains=" + DEFAULT_CUSTOMER_CATEGORY_NAME);

        // Get all the customerCategoriesList where customerCategoryName contains UPDATED_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldNotBeFound("customerCategoryName.contains=" + UPDATED_CUSTOMER_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByCustomerCategoryNameNotContainsSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where customerCategoryName does not contain DEFAULT_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldNotBeFound("customerCategoryName.doesNotContain=" + DEFAULT_CUSTOMER_CATEGORY_NAME);

        // Get all the customerCategoriesList where customerCategoryName does not contain UPDATED_CUSTOMER_CATEGORY_NAME
        defaultCustomerCategoriesShouldBeFound("customerCategoryName.doesNotContain=" + UPDATED_CUSTOMER_CATEGORY_NAME);
    }


    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom equals to DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom not equals to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the customerCategoriesList where validFrom equals to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom is not null
        defaultCustomerCategoriesShouldBeFound("validFrom.specified=true");

        // Get all the customerCategoriesList where validFrom is null
        defaultCustomerCategoriesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom is less than DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom is less than UPDATED_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validFrom is greater than DEFAULT_VALID_FROM
        defaultCustomerCategoriesShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the customerCategoriesList where validFrom is greater than SMALLER_VALID_FROM
        defaultCustomerCategoriesShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo equals to DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo not equals to DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo not equals to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the customerCategoriesList where validTo equals to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo is not null
        defaultCustomerCategoriesShouldBeFound("validTo.specified=true");

        // Get all the customerCategoriesList where validTo is null
        defaultCustomerCategoriesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo is less than or equal to SMALLER_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo is less than DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo is less than UPDATED_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomerCategoriesByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        // Get all the customerCategoriesList where validTo is greater than DEFAULT_VALID_TO
        defaultCustomerCategoriesShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the customerCategoriesList where validTo is greater than SMALLER_VALID_TO
        defaultCustomerCategoriesShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerCategoriesShouldBeFound(String filter) throws Exception {
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerCategoryName").value(hasItem(DEFAULT_CUSTOMER_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerCategoriesShouldNotBeFound(String filter) throws Exception {
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCustomerCategories() throws Exception {
        // Get the customerCategories
        restCustomerCategoriesMockMvc.perform(get("/api/customer-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        int databaseSizeBeforeUpdate = customerCategoriesRepository.findAll().size();

        // Update the customerCategories
        CustomerCategories updatedCustomerCategories = customerCategoriesRepository.findById(customerCategories.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerCategories are not directly saved in db
        em.detach(updatedCustomerCategories);
        updatedCustomerCategories
            .customerCategoryName(UPDATED_CUSTOMER_CATEGORY_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(updatedCustomerCategories);

        restCustomerCategoriesMockMvc.perform(put("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CustomerCategories testCustomerCategories = customerCategoriesList.get(customerCategoriesList.size() - 1);
        assertThat(testCustomerCategories.getCustomerCategoryName()).isEqualTo(UPDATED_CUSTOMER_CATEGORY_NAME);
        assertThat(testCustomerCategories.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustomerCategories.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerCategories() throws Exception {
        int databaseSizeBeforeUpdate = customerCategoriesRepository.findAll().size();

        // Create the CustomerCategories
        CustomerCategoriesDTO customerCategoriesDTO = customerCategoriesMapper.toDto(customerCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerCategoriesMockMvc.perform(put("/api/customer-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customerCategoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerCategories in the database
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerCategories() throws Exception {
        // Initialize the database
        customerCategoriesRepository.saveAndFlush(customerCategories);

        int databaseSizeBeforeDelete = customerCategoriesRepository.findAll().size();

        // Delete the customerCategories
        restCustomerCategoriesMockMvc.perform(delete("/api/customer-categories/{id}", customerCategories.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerCategories> customerCategoriesList = customerCategoriesRepository.findAll();
        assertThat(customerCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategories.class);
        CustomerCategories customerCategories1 = new CustomerCategories();
        customerCategories1.setId(1L);
        CustomerCategories customerCategories2 = new CustomerCategories();
        customerCategories2.setId(customerCategories1.getId());
        assertThat(customerCategories1).isEqualTo(customerCategories2);
        customerCategories2.setId(2L);
        assertThat(customerCategories1).isNotEqualTo(customerCategories2);
        customerCategories1.setId(null);
        assertThat(customerCategories1).isNotEqualTo(customerCategories2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerCategoriesDTO.class);
        CustomerCategoriesDTO customerCategoriesDTO1 = new CustomerCategoriesDTO();
        customerCategoriesDTO1.setId(1L);
        CustomerCategoriesDTO customerCategoriesDTO2 = new CustomerCategoriesDTO();
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO2.setId(customerCategoriesDTO1.getId());
        assertThat(customerCategoriesDTO1).isEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO2.setId(2L);
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
        customerCategoriesDTO1.setId(null);
        assertThat(customerCategoriesDTO1).isNotEqualTo(customerCategoriesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customerCategoriesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customerCategoriesMapper.fromId(null)).isNull();
    }
}
