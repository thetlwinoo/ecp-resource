package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.Currency;
import com.resource.server.repository.CurrencyRepository;
import com.resource.server.service.CurrencyService;
import com.resource.server.service.dto.CurrencyDTO;
import com.resource.server.service.mapper.CurrencyMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.CurrencyCriteria;
import com.resource.server.service.CurrencyQueryService;

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
 * Integration tests for the {@link CurrencyResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class CurrencyResourceIT {

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private CurrencyMapper currencyMapper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyQueryService currencyQueryService;

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

    private MockMvc restCurrencyMockMvc;

    private Currency currency;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurrencyResource currencyResource = new CurrencyResource(currencyService, currencyQueryService);
        this.restCurrencyMockMvc = MockMvcBuilders.standaloneSetup(currencyResource)
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
    public static Currency createEntity(EntityManager em) {
        Currency currency = new Currency()
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .currencyName(DEFAULT_CURRENCY_NAME);
        return currency;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Currency createUpdatedEntity(EntityManager em) {
        Currency currency = new Currency()
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME);
        return currency;
    }

    @BeforeEach
    public void initTest() {
        currency = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurrency() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isCreated());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate + 1);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void createCurrencyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = currencyRepository.findAll().size();

        // Create the Currency with an existing ID
        currency.setId(1L);
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCurrencyCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = currencyRepository.findAll().size();
        // set the field null
        currency.setCurrencyCode(null);

        // Create the Currency, which fails.
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        restCurrencyMockMvc.perform(post("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurrencies() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)));
    }
    
    @Test
    @Transactional
    public void getCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", currency.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(currency.getId().intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME));
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode equals to DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.equals=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.equals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode not equals to DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.notEquals=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode not equals to UPDATED_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.notEquals=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyCodeIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode in DEFAULT_CURRENCY_CODE or UPDATED_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.in=" + DEFAULT_CURRENCY_CODE + "," + UPDATED_CURRENCY_CODE);

        // Get all the currencyList where currencyCode equals to UPDATED_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.in=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode is not null
        defaultCurrencyShouldBeFound("currencyCode.specified=true");

        // Get all the currencyList where currencyCode is null
        defaultCurrencyShouldNotBeFound("currencyCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencyCodeContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode contains DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.contains=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode contains UPDATED_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.contains=" + UPDATED_CURRENCY_CODE);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyCodeNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyCode does not contain DEFAULT_CURRENCY_CODE
        defaultCurrencyShouldNotBeFound("currencyCode.doesNotContain=" + DEFAULT_CURRENCY_CODE);

        // Get all the currencyList where currencyCode does not contain UPDATED_CURRENCY_CODE
        defaultCurrencyShouldBeFound("currencyCode.doesNotContain=" + UPDATED_CURRENCY_CODE);
    }


    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.equals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.equals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName not equals to DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.notEquals=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName not equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.notEquals=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsInShouldWork() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName in DEFAULT_CURRENCY_NAME or UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.in=" + DEFAULT_CURRENCY_NAME + "," + UPDATED_CURRENCY_NAME);

        // Get all the currencyList where currencyName equals to UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.in=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName is not null
        defaultCurrencyShouldBeFound("currencyName.specified=true");

        // Get all the currencyList where currencyName is null
        defaultCurrencyShouldNotBeFound("currencyName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName contains DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.contains=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName contains UPDATED_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.contains=" + UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrenciesByCurrencyNameNotContainsSomething() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        // Get all the currencyList where currencyName does not contain DEFAULT_CURRENCY_NAME
        defaultCurrencyShouldNotBeFound("currencyName.doesNotContain=" + DEFAULT_CURRENCY_NAME);

        // Get all the currencyList where currencyName does not contain UPDATED_CURRENCY_NAME
        defaultCurrencyShouldBeFound("currencyName.doesNotContain=" + UPDATED_CURRENCY_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurrencyShouldBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(currency.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)));

        // Check, that the count call also returns 1
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurrencyShouldNotBeFound(String filter) throws Exception {
        restCurrencyMockMvc.perform(get("/api/currencies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurrencyMockMvc.perform(get("/api/currencies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurrency() throws Exception {
        // Get the currency
        restCurrencyMockMvc.perform(get("/api/currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Update the currency
        Currency updatedCurrency = currencyRepository.findById(currency.getId()).get();
        // Disconnect from session so that the updates on updatedCurrency are not directly saved in db
        em.detach(updatedCurrency);
        updatedCurrency
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME);
        CurrencyDTO currencyDTO = currencyMapper.toDto(updatedCurrency);

        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isOk());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
        Currency testCurrency = currencyList.get(currencyList.size() - 1);
        assertThat(testCurrency.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurrency.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCurrency() throws Exception {
        int databaseSizeBeforeUpdate = currencyRepository.findAll().size();

        // Create the Currency
        CurrencyDTO currencyDTO = currencyMapper.toDto(currency);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurrencyMockMvc.perform(put("/api/currencies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(currencyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Currency in the database
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurrency() throws Exception {
        // Initialize the database
        currencyRepository.saveAndFlush(currency);

        int databaseSizeBeforeDelete = currencyRepository.findAll().size();

        // Delete the currency
        restCurrencyMockMvc.perform(delete("/api/currencies/{id}", currency.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Currency> currencyList = currencyRepository.findAll();
        assertThat(currencyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Currency.class);
        Currency currency1 = new Currency();
        currency1.setId(1L);
        Currency currency2 = new Currency();
        currency2.setId(currency1.getId());
        assertThat(currency1).isEqualTo(currency2);
        currency2.setId(2L);
        assertThat(currency1).isNotEqualTo(currency2);
        currency1.setId(null);
        assertThat(currency1).isNotEqualTo(currency2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurrencyDTO.class);
        CurrencyDTO currencyDTO1 = new CurrencyDTO();
        currencyDTO1.setId(1L);
        CurrencyDTO currencyDTO2 = new CurrencyDTO();
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO2.setId(currencyDTO1.getId());
        assertThat(currencyDTO1).isEqualTo(currencyDTO2);
        currencyDTO2.setId(2L);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
        currencyDTO1.setId(null);
        assertThat(currencyDTO1).isNotEqualTo(currencyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(currencyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(currencyMapper.fromId(null)).isNull();
    }
}
