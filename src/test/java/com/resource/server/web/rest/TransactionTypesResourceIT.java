package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.TransactionTypes;
import com.resource.server.repository.TransactionTypesRepository;
import com.resource.server.service.TransactionTypesService;
import com.resource.server.service.dto.TransactionTypesDTO;
import com.resource.server.service.mapper.TransactionTypesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.TransactionTypesCriteria;
import com.resource.server.service.TransactionTypesQueryService;

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
 * Integration tests for the {@link TransactionTypesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class TransactionTypesResourceIT {

    private static final String DEFAULT_TRANSACTION_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_TYPE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TransactionTypesRepository transactionTypesRepository;

    @Autowired
    private TransactionTypesMapper transactionTypesMapper;

    @Autowired
    private TransactionTypesService transactionTypesService;

    @Autowired
    private TransactionTypesQueryService transactionTypesQueryService;

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

    private MockMvc restTransactionTypesMockMvc;

    private TransactionTypes transactionTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionTypesResource transactionTypesResource = new TransactionTypesResource(transactionTypesService, transactionTypesQueryService);
        this.restTransactionTypesMockMvc = MockMvcBuilders.standaloneSetup(transactionTypesResource)
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
    public static TransactionTypes createEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .transactionTypeName(DEFAULT_TRANSACTION_TYPE_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return transactionTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionTypes createUpdatedEntity(EntityManager em) {
        TransactionTypes transactionTypes = new TransactionTypes()
            .transactionTypeName(UPDATED_TRANSACTION_TYPE_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return transactionTypes;
    }

    @BeforeEach
    public void initTest() {
        transactionTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionTypes() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);
        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getTransactionTypeName()).isEqualTo(DEFAULT_TRANSACTION_TYPE_NAME);
        assertThat(testTransactionTypes.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testTransactionTypes.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createTransactionTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes with an existing ID
        transactionTypes.setId(1L);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTransactionTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setTransactionTypeName(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setValidFrom(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionTypesRepository.findAll().size();
        // set the field null
        transactionTypes.setValidTo(null);

        // Create the TransactionTypes, which fails.
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        restTransactionTypesMockMvc.perform(post("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionTypeName").value(hasItem(DEFAULT_TRANSACTION_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/{id}", transactionTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionTypes.getId().intValue()))
            .andExpect(jsonPath("$.transactionTypeName").value(DEFAULT_TRANSACTION_TYPE_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByTransactionTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where transactionTypeName equals to DEFAULT_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldBeFound("transactionTypeName.equals=" + DEFAULT_TRANSACTION_TYPE_NAME);

        // Get all the transactionTypesList where transactionTypeName equals to UPDATED_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldNotBeFound("transactionTypeName.equals=" + UPDATED_TRANSACTION_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByTransactionTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where transactionTypeName not equals to DEFAULT_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldNotBeFound("transactionTypeName.notEquals=" + DEFAULT_TRANSACTION_TYPE_NAME);

        // Get all the transactionTypesList where transactionTypeName not equals to UPDATED_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldBeFound("transactionTypeName.notEquals=" + UPDATED_TRANSACTION_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByTransactionTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where transactionTypeName in DEFAULT_TRANSACTION_TYPE_NAME or UPDATED_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldBeFound("transactionTypeName.in=" + DEFAULT_TRANSACTION_TYPE_NAME + "," + UPDATED_TRANSACTION_TYPE_NAME);

        // Get all the transactionTypesList where transactionTypeName equals to UPDATED_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldNotBeFound("transactionTypeName.in=" + UPDATED_TRANSACTION_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByTransactionTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where transactionTypeName is not null
        defaultTransactionTypesShouldBeFound("transactionTypeName.specified=true");

        // Get all the transactionTypesList where transactionTypeName is null
        defaultTransactionTypesShouldNotBeFound("transactionTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionTypesByTransactionTypeNameContainsSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where transactionTypeName contains DEFAULT_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldBeFound("transactionTypeName.contains=" + DEFAULT_TRANSACTION_TYPE_NAME);

        // Get all the transactionTypesList where transactionTypeName contains UPDATED_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldNotBeFound("transactionTypeName.contains=" + UPDATED_TRANSACTION_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByTransactionTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where transactionTypeName does not contain DEFAULT_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldNotBeFound("transactionTypeName.doesNotContain=" + DEFAULT_TRANSACTION_TYPE_NAME);

        // Get all the transactionTypesList where transactionTypeName does not contain UPDATED_TRANSACTION_TYPE_NAME
        defaultTransactionTypesShouldBeFound("transactionTypeName.doesNotContain=" + UPDATED_TRANSACTION_TYPE_NAME);
    }


    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom equals to DEFAULT_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom not equals to UPDATED_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the transactionTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom is not null
        defaultTransactionTypesShouldBeFound("validFrom.specified=true");

        // Get all the transactionTypesList where validFrom is null
        defaultTransactionTypesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom is less than DEFAULT_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom is less than UPDATED_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validFrom is greater than DEFAULT_VALID_FROM
        defaultTransactionTypesShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the transactionTypesList where validFrom is greater than SMALLER_VALID_FROM
        defaultTransactionTypesShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo equals to DEFAULT_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo equals to UPDATED_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo not equals to DEFAULT_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo not equals to UPDATED_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the transactionTypesList where validTo equals to UPDATED_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo is not null
        defaultTransactionTypesShouldBeFound("validTo.specified=true");

        // Get all the transactionTypesList where validTo is null
        defaultTransactionTypesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo is less than or equal to SMALLER_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo is less than DEFAULT_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo is less than UPDATED_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionTypesByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        // Get all the transactionTypesList where validTo is greater than DEFAULT_VALID_TO
        defaultTransactionTypesShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the transactionTypesList where validTo is greater than SMALLER_VALID_TO
        defaultTransactionTypesShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionTypesShouldBeFound(String filter) throws Exception {
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionTypeName").value(hasItem(DEFAULT_TRANSACTION_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionTypesShouldNotBeFound(String filter) throws Exception {
        restTransactionTypesMockMvc.perform(get("/api/transaction-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionTypes() throws Exception {
        // Get the transactionTypes
        restTransactionTypesMockMvc.perform(get("/api/transaction-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Update the transactionTypes
        TransactionTypes updatedTransactionTypes = transactionTypesRepository.findById(transactionTypes.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionTypes are not directly saved in db
        em.detach(updatedTransactionTypes);
        updatedTransactionTypes
            .transactionTypeName(UPDATED_TRANSACTION_TYPE_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(updatedTransactionTypes);

        restTransactionTypesMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
        TransactionTypes testTransactionTypes = transactionTypesList.get(transactionTypesList.size() - 1);
        assertThat(testTransactionTypes.getTransactionTypeName()).isEqualTo(UPDATED_TRANSACTION_TYPE_NAME);
        assertThat(testTransactionTypes.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testTransactionTypes.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionTypes() throws Exception {
        int databaseSizeBeforeUpdate = transactionTypesRepository.findAll().size();

        // Create the TransactionTypes
        TransactionTypesDTO transactionTypesDTO = transactionTypesMapper.toDto(transactionTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionTypesMockMvc.perform(put("/api/transaction-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionTypes in the database
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionTypes() throws Exception {
        // Initialize the database
        transactionTypesRepository.saveAndFlush(transactionTypes);

        int databaseSizeBeforeDelete = transactionTypesRepository.findAll().size();

        // Delete the transactionTypes
        restTransactionTypesMockMvc.perform(delete("/api/transaction-types/{id}", transactionTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionTypes> transactionTypesList = transactionTypesRepository.findAll();
        assertThat(transactionTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypes.class);
        TransactionTypes transactionTypes1 = new TransactionTypes();
        transactionTypes1.setId(1L);
        TransactionTypes transactionTypes2 = new TransactionTypes();
        transactionTypes2.setId(transactionTypes1.getId());
        assertThat(transactionTypes1).isEqualTo(transactionTypes2);
        transactionTypes2.setId(2L);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
        transactionTypes1.setId(null);
        assertThat(transactionTypes1).isNotEqualTo(transactionTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionTypesDTO.class);
        TransactionTypesDTO transactionTypesDTO1 = new TransactionTypesDTO();
        transactionTypesDTO1.setId(1L);
        TransactionTypesDTO transactionTypesDTO2 = new TransactionTypesDTO();
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
        transactionTypesDTO2.setId(transactionTypesDTO1.getId());
        assertThat(transactionTypesDTO1).isEqualTo(transactionTypesDTO2);
        transactionTypesDTO2.setId(2L);
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
        transactionTypesDTO1.setId(null);
        assertThat(transactionTypesDTO1).isNotEqualTo(transactionTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionTypesMapper.fromId(null)).isNull();
    }
}
