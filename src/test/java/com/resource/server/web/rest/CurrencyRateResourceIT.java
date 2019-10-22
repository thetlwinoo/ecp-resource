package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.CurrencyRate;
import com.resource.server.repository.CurrencyRateRepository;
import com.resource.server.service.CurrencyRateService;
import com.resource.server.service.dto.CurrencyRateDTO;
import com.resource.server.service.mapper.CurrencyRateMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.CurrencyRateCriteria;
import com.resource.server.service.CurrencyRateQueryService;

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
 * Integration tests for the {@link CurrencyRateResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class CurrencyRateResourceIT {

    private static final LocalDate DEFAULT_CURRENCY_RATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURRENCY_RATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CURRENCY_RATE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_FROM_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FROM_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TO_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TO_CURRENCY_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_AVERAGE_RATE = 1F;
    private static final Float UPDATED_AVERAGE_RATE = 2F;
    private static final Float SMALLER_AVERAGE_RATE = 1F - 1F;

    private static final Float DEFAULT_END_OF_DAY_RATE = 1F;
    private static final Float UPDATED_END_OF_DAY_RATE = 2F;
    private static final Float SMALLER_END_OF_DAY_RATE = 1F - 1F;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_EDITED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EDITED_WHEN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_EDITED_WHEN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private CurrencyRateRepository currencyRateRepository;

    @Autowired
    private CurrencyRateMapper currencyRateMapper;

    @Autowired
    private CurrencyRateService currencyRateService;

    @Autowired
    private CurrencyRateQueryService currencyRateQueryService;

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

    private MockMvc restCurrencyRateMockMvc;

    private CurrencyRate currencyRate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyRateResource currencyRateResource = new CurrencyRateResource(currencyRateService, currencyRateQueryService);
        this.restCurrencyRateMockMvc = MockMvcBuilders.standaloneSetup(currencyRateResource)
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
    public static CurrencyRate createEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currencyRateDate(DEFAULT_CURRENCY_RATE_DATE)
            .fromCurrencyCode(DEFAULT_FROM_CURRENCY_CODE)
            .toCurrencyCode(DEFAULT_TO_CURRENCY_CODE)
            .averageRate(DEFAULT_AVERAGE_RATE)
            .endOfDayRate(DEFAULT_END_OF_DAY_RATE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return currencyRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurrencyRate createUpdatedEntity(EntityManager em) {
        CurrencyRate currencyRate = new CurrencyRate()
            .currencyRateDate(UPDATED_CURRENCY_RATE_DATE)
            .fromCurrencyCode(UPDATED_FROM_CURRENCY_CODE)
            .toCurrencyCode(UPDATED_TO_CURRENCY_CODE)
            .averageRate(UPDATED_AVERAGE_RATE)
            .endOfDayRate(UPDATED_END_OF_DAY_RATE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return currencyRate;
    }

    @BeforeEach
    public void initTest() {
        currencyRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrencyRate() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isCreated());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate + 1);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrencyRateDate()).isEqualTo(DEFAULT_CURRENCY_RATE_DATE);
        assertThat(testCurrencyRate.getFromCurrencyCode()).isEqualTo(DEFAULT_FROM_CURRENCY_CODE);
        assertThat(testCurrencyRate.getToCurrencyCode()).isEqualTo(DEFAULT_TO_CURRENCY_CODE);
        assertThat(testCurrencyRate.getAverageRate()).isEqualTo(DEFAULT_AVERAGE_RATE);
        assertThat(testCurrencyRate.getEndOfDayRate()).isEqualTo(DEFAULT_END_OF_DAY_RATE);
        assertThat(testCurrencyRate.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCurrencyRate.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCurrencyRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate with an existing ID
        currencyRate.setId(1L);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrencyRateDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRateRepository.findAll().size();
        // set the field null
        currencyRate.setCurrencyRateDate(null);

        // Create the CurrencyRate, which fails.
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        restCurrencyRateMockMvc.perform(post("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencyRates() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyRateDate").value(hasItem(DEFAULT_CURRENCY_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromCurrencyCode").value(hasItem(DEFAULT_FROM_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].toCurrencyCode").value(hasItem(DEFAULT_TO_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].averageRate").value(hasItem(DEFAULT_AVERAGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].endOfDayRate").value(hasItem(DEFAULT_END_OF_DAY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", currencyRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currencyRate.getId().intValue()))
            .andExpect(jsonPath("$.currencyRateDate").value(DEFAULT_CURRENCY_RATE_DATE.toString()))
            .andExpect(jsonPath("$.fromCurrencyCode").value(DEFAULT_FROM_CURRENCY_CODE))
            .andExpect(jsonPath("$.toCurrencyCode").value(DEFAULT_TO_CURRENCY_CODE))
            .andExpect(jsonPath("$.averageRate").value(DEFAULT_AVERAGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.endOfDayRate").value(DEFAULT_END_OF_DAY_RATE.doubleValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate equals to DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.equals=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate equals to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.equals=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate not equals to DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.notEquals=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate not equals to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.notEquals=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate in DEFAULT_CURRENCY_RATE_DATE or UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.in=" + DEFAULT_CURRENCY_RATE_DATE + "," + UPDATED_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate equals to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.in=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate is not null
        defaultCurrencyRateShouldBeFound("currencyRateDate.specified=true");

        // Get all the currencyRateList where currencyRateDate is null
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate is greater than or equal to DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.greaterThanOrEqual=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate is greater than or equal to UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.greaterThanOrEqual=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate is less than or equal to DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.lessThanOrEqual=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate is less than or equal to SMALLER_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.lessThanOrEqual=" + SMALLER_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate is less than DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.lessThan=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate is less than UPDATED_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.lessThan=" + UPDATED_CURRENCY_RATE_DATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByCurrencyRateDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where currencyRateDate is greater than DEFAULT_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldNotBeFound("currencyRateDate.greaterThan=" + DEFAULT_CURRENCY_RATE_DATE);

        // Get all the currencyRateList where currencyRateDate is greater than SMALLER_CURRENCY_RATE_DATE
        defaultCurrencyRateShouldBeFound("currencyRateDate.greaterThan=" + SMALLER_CURRENCY_RATE_DATE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByFromCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromCurrencyCode equals to DEFAULT_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("fromCurrencyCode.equals=" + DEFAULT_FROM_CURRENCY_CODE);

        // Get all the currencyRateList where fromCurrencyCode equals to UPDATED_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("fromCurrencyCode.equals=" + UPDATED_FROM_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromCurrencyCode not equals to DEFAULT_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("fromCurrencyCode.notEquals=" + DEFAULT_FROM_CURRENCY_CODE);

        // Get all the currencyRateList where fromCurrencyCode not equals to UPDATED_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("fromCurrencyCode.notEquals=" + UPDATED_FROM_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromCurrencyCode in DEFAULT_FROM_CURRENCY_CODE or UPDATED_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("fromCurrencyCode.in=" + DEFAULT_FROM_CURRENCY_CODE + "," + UPDATED_FROM_CURRENCY_CODE);

        // Get all the currencyRateList where fromCurrencyCode equals to UPDATED_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("fromCurrencyCode.in=" + UPDATED_FROM_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromCurrencyCode is not null
        defaultCurrencyRateShouldBeFound("fromCurrencyCode.specified=true");

        // Get all the currencyRateList where fromCurrencyCode is null
        defaultCurrencyRateShouldNotBeFound("fromCurrencyCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrencyRatesByFromCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromCurrencyCode contains DEFAULT_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("fromCurrencyCode.contains=" + DEFAULT_FROM_CURRENCY_CODE);

        // Get all the currencyRateList where fromCurrencyCode contains UPDATED_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("fromCurrencyCode.contains=" + UPDATED_FROM_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByFromCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where fromCurrencyCode does not contain DEFAULT_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("fromCurrencyCode.doesNotContain=" + DEFAULT_FROM_CURRENCY_CODE);

        // Get all the currencyRateList where fromCurrencyCode does not contain UPDATED_FROM_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("fromCurrencyCode.doesNotContain=" + UPDATED_FROM_CURRENCY_CODE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByToCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where toCurrencyCode equals to DEFAULT_TO_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("toCurrencyCode.equals=" + DEFAULT_TO_CURRENCY_CODE);

        // Get all the currencyRateList where toCurrencyCode equals to UPDATED_TO_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("toCurrencyCode.equals=" + UPDATED_TO_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByToCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where toCurrencyCode not equals to DEFAULT_TO_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("toCurrencyCode.notEquals=" + DEFAULT_TO_CURRENCY_CODE);

        // Get all the currencyRateList where toCurrencyCode not equals to UPDATED_TO_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("toCurrencyCode.notEquals=" + UPDATED_TO_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByToCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where toCurrencyCode in DEFAULT_TO_CURRENCY_CODE or UPDATED_TO_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("toCurrencyCode.in=" + DEFAULT_TO_CURRENCY_CODE + "," + UPDATED_TO_CURRENCY_CODE);

        // Get all the currencyRateList where toCurrencyCode equals to UPDATED_TO_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("toCurrencyCode.in=" + UPDATED_TO_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByToCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where toCurrencyCode is not null
        defaultCurrencyRateShouldBeFound("toCurrencyCode.specified=true");

        // Get all the currencyRateList where toCurrencyCode is null
        defaultCurrencyRateShouldNotBeFound("toCurrencyCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrencyRatesByToCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where toCurrencyCode contains DEFAULT_TO_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("toCurrencyCode.contains=" + DEFAULT_TO_CURRENCY_CODE);

        // Get all the currencyRateList where toCurrencyCode contains UPDATED_TO_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("toCurrencyCode.contains=" + UPDATED_TO_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByToCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where toCurrencyCode does not contain DEFAULT_TO_CURRENCY_CODE
        defaultCurrencyRateShouldNotBeFound("toCurrencyCode.doesNotContain=" + DEFAULT_TO_CURRENCY_CODE);

        // Get all the currencyRateList where toCurrencyCode does not contain UPDATED_TO_CURRENCY_CODE
        defaultCurrencyRateShouldBeFound("toCurrencyCode.doesNotContain=" + UPDATED_TO_CURRENCY_CODE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate equals to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.equals=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate equals to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.equals=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate not equals to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.notEquals=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate not equals to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.notEquals=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate in DEFAULT_AVERAGE_RATE or UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.in=" + DEFAULT_AVERAGE_RATE + "," + UPDATED_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate equals to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.in=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is not null
        defaultCurrencyRateShouldBeFound("averageRate.specified=true");

        // Get all the currencyRateList where averageRate is null
        defaultCurrencyRateShouldNotBeFound("averageRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is greater than or equal to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.greaterThanOrEqual=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is greater than or equal to UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.greaterThanOrEqual=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is less than or equal to DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.lessThanOrEqual=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is less than or equal to SMALLER_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.lessThanOrEqual=" + SMALLER_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is less than DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.lessThan=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is less than UPDATED_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.lessThan=" + UPDATED_AVERAGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByAverageRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where averageRate is greater than DEFAULT_AVERAGE_RATE
        defaultCurrencyRateShouldNotBeFound("averageRate.greaterThan=" + DEFAULT_AVERAGE_RATE);

        // Get all the currencyRateList where averageRate is greater than SMALLER_AVERAGE_RATE
        defaultCurrencyRateShouldBeFound("averageRate.greaterThan=" + SMALLER_AVERAGE_RATE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate equals to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.equals=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate equals to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.equals=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate not equals to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.notEquals=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate not equals to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.notEquals=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate in DEFAULT_END_OF_DAY_RATE or UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.in=" + DEFAULT_END_OF_DAY_RATE + "," + UPDATED_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate equals to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.in=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is not null
        defaultCurrencyRateShouldBeFound("endOfDayRate.specified=true");

        // Get all the currencyRateList where endOfDayRate is null
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is greater than or equal to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.greaterThanOrEqual=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is greater than or equal to UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.greaterThanOrEqual=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is less than or equal to DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.lessThanOrEqual=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is less than or equal to SMALLER_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.lessThanOrEqual=" + SMALLER_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is less than DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.lessThan=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is less than UPDATED_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.lessThan=" + UPDATED_END_OF_DAY_RATE);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByEndOfDayRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where endOfDayRate is greater than DEFAULT_END_OF_DAY_RATE
        defaultCurrencyRateShouldNotBeFound("endOfDayRate.greaterThan=" + DEFAULT_END_OF_DAY_RATE);

        // Get all the currencyRateList where endOfDayRate is greater than SMALLER_END_OF_DAY_RATE
        defaultCurrencyRateShouldBeFound("endOfDayRate.greaterThan=" + SMALLER_END_OF_DAY_RATE);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy is not null
        defaultCurrencyRateShouldBeFound("lastEditedBy.specified=true");

        // Get all the currencyRateList where lastEditedBy is null
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCurrencyRateShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the currencyRateList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCurrencyRateShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen is not null
        defaultCurrencyRateShouldBeFound("lastEditedWhen.specified=true");

        // Get all the currencyRateList where lastEditedWhen is null
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen is greater than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.greaterThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen is greater than or equal to UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.greaterThanOrEqual=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen is less than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.lessThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen is less than or equal to SMALLER_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.lessThanOrEqual=" + SMALLER_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen is less than DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.lessThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen is less than UPDATED_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.lessThan=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCurrencyRatesByLastEditedWhenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        // Get all the currencyRateList where lastEditedWhen is greater than DEFAULT_LAST_EDITED_WHEN
        defaultCurrencyRateShouldNotBeFound("lastEditedWhen.greaterThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the currencyRateList where lastEditedWhen is greater than SMALLER_LAST_EDITED_WHEN
        defaultCurrencyRateShouldBeFound("lastEditedWhen.greaterThan=" + SMALLER_LAST_EDITED_WHEN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyRateShouldBeFound(String filter) throws Exception {
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currencyRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyRateDate").value(hasItem(DEFAULT_CURRENCY_RATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].fromCurrencyCode").value(hasItem(DEFAULT_FROM_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].toCurrencyCode").value(hasItem(DEFAULT_TO_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].averageRate").value(hasItem(DEFAULT_AVERAGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].endOfDayRate").value(hasItem(DEFAULT_END_OF_DAY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyRateShouldNotBeFound(String filter) throws Exception {
        restCurrencyRateMockMvc.perform(get("/api/currency-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurrencyRate() throws Exception {
        // Get the currencyRate
        restCurrencyRateMockMvc.perform(get("/api/currency-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Update the currencyRate
        CurrencyRate updatedCurrencyRate = currencyRateRepository.findById(currencyRate.getId()).get();
        // Disconnect from session so that the updates on updatedCurrencyRate are not directly saved in db
        em.detach(updatedCurrencyRate);
        updatedCurrencyRate
            .currencyRateDate(UPDATED_CURRENCY_RATE_DATE)
            .fromCurrencyCode(UPDATED_FROM_CURRENCY_CODE)
            .toCurrencyCode(UPDATED_TO_CURRENCY_CODE)
            .averageRate(UPDATED_AVERAGE_RATE)
            .endOfDayRate(UPDATED_END_OF_DAY_RATE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(updatedCurrencyRate);

        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isOk());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
        CurrencyRate testCurrencyRate = currencyRateList.get(currencyRateList.size() - 1);
        assertThat(testCurrencyRate.getCurrencyRateDate()).isEqualTo(UPDATED_CURRENCY_RATE_DATE);
        assertThat(testCurrencyRate.getFromCurrencyCode()).isEqualTo(UPDATED_FROM_CURRENCY_CODE);
        assertThat(testCurrencyRate.getToCurrencyCode()).isEqualTo(UPDATED_TO_CURRENCY_CODE);
        assertThat(testCurrencyRate.getAverageRate()).isEqualTo(UPDATED_AVERAGE_RATE);
        assertThat(testCurrencyRate.getEndOfDayRate()).isEqualTo(UPDATED_END_OF_DAY_RATE);
        assertThat(testCurrencyRate.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCurrencyRate.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrencyRate() throws Exception {
        int databaseSizeBeforeUpdate = currencyRateRepository.findAll().size();

        // Create the CurrencyRate
        CurrencyRateDTO currencyRateDTO = currencyRateMapper.toDto(currencyRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyRateMockMvc.perform(put("/api/currency-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurrencyRate in the database
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrencyRate() throws Exception {
        // Initialize the database
        currencyRateRepository.saveAndFlush(currencyRate);

        int databaseSizeBeforeDelete = currencyRateRepository.findAll().size();

        // Delete the currencyRate
        restCurrencyRateMockMvc.perform(delete("/api/currency-rates/{id}", currencyRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        assertThat(currencyRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRate.class);
        CurrencyRate currencyRate1 = new CurrencyRate();
        currencyRate1.setId(1L);
        CurrencyRate currencyRate2 = new CurrencyRate();
        currencyRate2.setId(currencyRate1.getId());
        assertThat(currencyRate1).isEqualTo(currencyRate2);
        currencyRate2.setId(2L);
        assertThat(currencyRate1).isNotEqualTo(currencyRate2);
        currencyRate1.setId(null);
        assertThat(currencyRate1).isNotEqualTo(currencyRate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyRateDTO.class);
        CurrencyRateDTO currencyRateDTO1 = new CurrencyRateDTO();
        currencyRateDTO1.setId(1L);
        CurrencyRateDTO currencyRateDTO2 = new CurrencyRateDTO();
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(currencyRateDTO1.getId());
        assertThat(currencyRateDTO1).isEqualTo(currencyRateDTO2);
        currencyRateDTO2.setId(2L);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
        currencyRateDTO1.setId(null);
        assertThat(currencyRateDTO1).isNotEqualTo(currencyRateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currencyRateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currencyRateMapper.fromId(null)).isNull();
    }
}
