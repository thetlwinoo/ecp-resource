package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.PaymentMethods;
import com.resource.server.repository.PaymentMethodsRepository;
import com.resource.server.service.PaymentMethodsService;
import com.resource.server.service.dto.PaymentMethodsDTO;
import com.resource.server.service.mapper.PaymentMethodsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.PaymentMethodsCriteria;
import com.resource.server.service.PaymentMethodsQueryService;

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
 * Integration tests for the {@link PaymentMethodsResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class PaymentMethodsResourceIT {

    private static final String DEFAULT_PAYMENT_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_METHOD_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    @Autowired
    private PaymentMethodsMapper paymentMethodsMapper;

    @Autowired
    private PaymentMethodsService paymentMethodsService;

    @Autowired
    private PaymentMethodsQueryService paymentMethodsQueryService;

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

    private MockMvc restPaymentMethodsMockMvc;

    private PaymentMethods paymentMethods;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentMethodsResource paymentMethodsResource = new PaymentMethodsResource(paymentMethodsService, paymentMethodsQueryService);
        this.restPaymentMethodsMockMvc = MockMvcBuilders.standaloneSetup(paymentMethodsResource)
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
    public static PaymentMethods createEntity(EntityManager em) {
        PaymentMethods paymentMethods = new PaymentMethods()
            .paymentMethodName(DEFAULT_PAYMENT_METHOD_NAME)
            .activeInd(DEFAULT_ACTIVE_IND)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return paymentMethods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentMethods createUpdatedEntity(EntityManager em) {
        PaymentMethods paymentMethods = new PaymentMethods()
            .paymentMethodName(UPDATED_PAYMENT_METHOD_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return paymentMethods;
    }

    @BeforeEach
    public void initTest() {
        paymentMethods = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentMethods() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);
        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentMethods testPaymentMethods = paymentMethodsList.get(paymentMethodsList.size() - 1);
        assertThat(testPaymentMethods.getPaymentMethodName()).isEqualTo(DEFAULT_PAYMENT_METHOD_NAME);
        assertThat(testPaymentMethods.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testPaymentMethods.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPaymentMethods.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPaymentMethodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods with an existing ID
        paymentMethods.setId(1L);
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPaymentMethodNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setPaymentMethodName(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIndIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setActiveInd(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setValidFrom(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentMethodsRepository.findAll().size();
        // set the field null
        paymentMethods.setValidTo(null);

        // Create the PaymentMethods, which fails.
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        restPaymentMethodsMockMvc.perform(post("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethodName").value(hasItem(DEFAULT_PAYMENT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get the paymentMethods
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/{id}", paymentMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentMethods.getId().intValue()))
            .andExpect(jsonPath("$.paymentMethodName").value(DEFAULT_PAYMENT_METHOD_NAME))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByPaymentMethodNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where paymentMethodName equals to DEFAULT_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldBeFound("paymentMethodName.equals=" + DEFAULT_PAYMENT_METHOD_NAME);

        // Get all the paymentMethodsList where paymentMethodName equals to UPDATED_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldNotBeFound("paymentMethodName.equals=" + UPDATED_PAYMENT_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByPaymentMethodNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where paymentMethodName not equals to DEFAULT_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldNotBeFound("paymentMethodName.notEquals=" + DEFAULT_PAYMENT_METHOD_NAME);

        // Get all the paymentMethodsList where paymentMethodName not equals to UPDATED_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldBeFound("paymentMethodName.notEquals=" + UPDATED_PAYMENT_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByPaymentMethodNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where paymentMethodName in DEFAULT_PAYMENT_METHOD_NAME or UPDATED_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldBeFound("paymentMethodName.in=" + DEFAULT_PAYMENT_METHOD_NAME + "," + UPDATED_PAYMENT_METHOD_NAME);

        // Get all the paymentMethodsList where paymentMethodName equals to UPDATED_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldNotBeFound("paymentMethodName.in=" + UPDATED_PAYMENT_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByPaymentMethodNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where paymentMethodName is not null
        defaultPaymentMethodsShouldBeFound("paymentMethodName.specified=true");

        // Get all the paymentMethodsList where paymentMethodName is null
        defaultPaymentMethodsShouldNotBeFound("paymentMethodName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentMethodsByPaymentMethodNameContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where paymentMethodName contains DEFAULT_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldBeFound("paymentMethodName.contains=" + DEFAULT_PAYMENT_METHOD_NAME);

        // Get all the paymentMethodsList where paymentMethodName contains UPDATED_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldNotBeFound("paymentMethodName.contains=" + UPDATED_PAYMENT_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByPaymentMethodNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where paymentMethodName does not contain DEFAULT_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldNotBeFound("paymentMethodName.doesNotContain=" + DEFAULT_PAYMENT_METHOD_NAME);

        // Get all the paymentMethodsList where paymentMethodName does not contain UPDATED_PAYMENT_METHOD_NAME
        defaultPaymentMethodsShouldBeFound("paymentMethodName.doesNotContain=" + UPDATED_PAYMENT_METHOD_NAME);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultPaymentMethodsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the paymentMethodsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultPaymentMethodsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the paymentMethodsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the paymentMethodsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultPaymentMethodsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where activeInd is not null
        defaultPaymentMethodsShouldBeFound("activeInd.specified=true");

        // Get all the paymentMethodsList where activeInd is null
        defaultPaymentMethodsShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom equals to DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom not equals to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the paymentMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom is not null
        defaultPaymentMethodsShouldBeFound("validFrom.specified=true");

        // Get all the paymentMethodsList where validFrom is null
        defaultPaymentMethodsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom is less than DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom is less than UPDATED_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validFrom is greater than DEFAULT_VALID_FROM
        defaultPaymentMethodsShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the paymentMethodsList where validFrom is greater than SMALLER_VALID_FROM
        defaultPaymentMethodsShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo equals to DEFAULT_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo equals to UPDATED_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo not equals to DEFAULT_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo not equals to UPDATED_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the paymentMethodsList where validTo equals to UPDATED_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo is not null
        defaultPaymentMethodsShouldBeFound("validTo.specified=true");

        // Get all the paymentMethodsList where validTo is null
        defaultPaymentMethodsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo is less than or equal to SMALLER_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo is less than DEFAULT_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo is less than UPDATED_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPaymentMethodsByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        // Get all the paymentMethodsList where validTo is greater than DEFAULT_VALID_TO
        defaultPaymentMethodsShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the paymentMethodsList where validTo is greater than SMALLER_VALID_TO
        defaultPaymentMethodsShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentMethodsShouldBeFound(String filter) throws Exception {
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentMethodName").value(hasItem(DEFAULT_PAYMENT_METHOD_NAME)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentMethodsShouldNotBeFound(String filter) throws Exception {
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaymentMethods() throws Exception {
        // Get the paymentMethods
        restPaymentMethodsMockMvc.perform(get("/api/payment-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        int databaseSizeBeforeUpdate = paymentMethodsRepository.findAll().size();

        // Update the paymentMethods
        PaymentMethods updatedPaymentMethods = paymentMethodsRepository.findById(paymentMethods.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentMethods are not directly saved in db
        em.detach(updatedPaymentMethods);
        updatedPaymentMethods
            .paymentMethodName(UPDATED_PAYMENT_METHOD_NAME)
            .activeInd(UPDATED_ACTIVE_IND)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(updatedPaymentMethods);

        restPaymentMethodsMockMvc.perform(put("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeUpdate);
        PaymentMethods testPaymentMethods = paymentMethodsList.get(paymentMethodsList.size() - 1);
        assertThat(testPaymentMethods.getPaymentMethodName()).isEqualTo(UPDATED_PAYMENT_METHOD_NAME);
        assertThat(testPaymentMethods.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testPaymentMethods.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPaymentMethods.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentMethods() throws Exception {
        int databaseSizeBeforeUpdate = paymentMethodsRepository.findAll().size();

        // Create the PaymentMethods
        PaymentMethodsDTO paymentMethodsDTO = paymentMethodsMapper.toDto(paymentMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMethodsMockMvc.perform(put("/api/payment-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentMethods in the database
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentMethods() throws Exception {
        // Initialize the database
        paymentMethodsRepository.saveAndFlush(paymentMethods);

        int databaseSizeBeforeDelete = paymentMethodsRepository.findAll().size();

        // Delete the paymentMethods
        restPaymentMethodsMockMvc.perform(delete("/api/payment-methods/{id}", paymentMethods.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentMethods> paymentMethodsList = paymentMethodsRepository.findAll();
        assertThat(paymentMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethods.class);
        PaymentMethods paymentMethods1 = new PaymentMethods();
        paymentMethods1.setId(1L);
        PaymentMethods paymentMethods2 = new PaymentMethods();
        paymentMethods2.setId(paymentMethods1.getId());
        assertThat(paymentMethods1).isEqualTo(paymentMethods2);
        paymentMethods2.setId(2L);
        assertThat(paymentMethods1).isNotEqualTo(paymentMethods2);
        paymentMethods1.setId(null);
        assertThat(paymentMethods1).isNotEqualTo(paymentMethods2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethodsDTO.class);
        PaymentMethodsDTO paymentMethodsDTO1 = new PaymentMethodsDTO();
        paymentMethodsDTO1.setId(1L);
        PaymentMethodsDTO paymentMethodsDTO2 = new PaymentMethodsDTO();
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO2.setId(paymentMethodsDTO1.getId());
        assertThat(paymentMethodsDTO1).isEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO2.setId(2L);
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
        paymentMethodsDTO1.setId(null);
        assertThat(paymentMethodsDTO1).isNotEqualTo(paymentMethodsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentMethodsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentMethodsMapper.fromId(null)).isNull();
    }
}
