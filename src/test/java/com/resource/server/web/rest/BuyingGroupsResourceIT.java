package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.BuyingGroups;
import com.resource.server.repository.BuyingGroupsRepository;
import com.resource.server.service.BuyingGroupsService;
import com.resource.server.service.dto.BuyingGroupsDTO;
import com.resource.server.service.mapper.BuyingGroupsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.BuyingGroupsCriteria;
import com.resource.server.service.BuyingGroupsQueryService;

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
 * Integration tests for the {@link BuyingGroupsResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class BuyingGroupsResourceIT {

    private static final String DEFAULT_BUYING_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUYING_GROUP_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private BuyingGroupsRepository buyingGroupsRepository;

    @Autowired
    private BuyingGroupsMapper buyingGroupsMapper;

    @Autowired
    private BuyingGroupsService buyingGroupsService;

    @Autowired
    private BuyingGroupsQueryService buyingGroupsQueryService;

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

    private MockMvc restBuyingGroupsMockMvc;

    private BuyingGroups buyingGroups;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuyingGroupsResource buyingGroupsResource = new BuyingGroupsResource(buyingGroupsService, buyingGroupsQueryService);
        this.restBuyingGroupsMockMvc = MockMvcBuilders.standaloneSetup(buyingGroupsResource)
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
    public static BuyingGroups createEntity(EntityManager em) {
        BuyingGroups buyingGroups = new BuyingGroups()
            .buyingGroupName(DEFAULT_BUYING_GROUP_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return buyingGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyingGroups createUpdatedEntity(EntityManager em) {
        BuyingGroups buyingGroups = new BuyingGroups()
            .buyingGroupName(UPDATED_BUYING_GROUP_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return buyingGroups;
    }

    @BeforeEach
    public void initTest() {
        buyingGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuyingGroups() throws Exception {
        int databaseSizeBeforeCreate = buyingGroupsRepository.findAll().size();

        // Create the BuyingGroups
        BuyingGroupsDTO buyingGroupsDTO = buyingGroupsMapper.toDto(buyingGroups);
        restBuyingGroupsMockMvc.perform(post("/api/buying-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyingGroupsDTO)))
            .andExpect(status().isCreated());

        // Validate the BuyingGroups in the database
        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        BuyingGroups testBuyingGroups = buyingGroupsList.get(buyingGroupsList.size() - 1);
        assertThat(testBuyingGroups.getBuyingGroupName()).isEqualTo(DEFAULT_BUYING_GROUP_NAME);
        assertThat(testBuyingGroups.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testBuyingGroups.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createBuyingGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buyingGroupsRepository.findAll().size();

        // Create the BuyingGroups with an existing ID
        buyingGroups.setId(1L);
        BuyingGroupsDTO buyingGroupsDTO = buyingGroupsMapper.toDto(buyingGroups);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyingGroupsMockMvc.perform(post("/api/buying-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyingGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuyingGroups in the database
        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingGroupsRepository.findAll().size();
        // set the field null
        buyingGroups.setValidFrom(null);

        // Create the BuyingGroups, which fails.
        BuyingGroupsDTO buyingGroupsDTO = buyingGroupsMapper.toDto(buyingGroups);

        restBuyingGroupsMockMvc.perform(post("/api/buying-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyingGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = buyingGroupsRepository.findAll().size();
        // set the field null
        buyingGroups.setValidTo(null);

        // Create the BuyingGroups, which fails.
        BuyingGroupsDTO buyingGroupsDTO = buyingGroupsMapper.toDto(buyingGroups);

        restBuyingGroupsMockMvc.perform(post("/api/buying-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyingGroupsDTO)))
            .andExpect(status().isBadRequest());

        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBuyingGroups() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyingGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].buyingGroupName").value(hasItem(DEFAULT_BUYING_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getBuyingGroups() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get the buyingGroups
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups/{id}", buyingGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buyingGroups.getId().intValue()))
            .andExpect(jsonPath("$.buyingGroupName").value(DEFAULT_BUYING_GROUP_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByBuyingGroupNameIsEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where buyingGroupName equals to DEFAULT_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldBeFound("buyingGroupName.equals=" + DEFAULT_BUYING_GROUP_NAME);

        // Get all the buyingGroupsList where buyingGroupName equals to UPDATED_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldNotBeFound("buyingGroupName.equals=" + UPDATED_BUYING_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByBuyingGroupNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where buyingGroupName not equals to DEFAULT_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldNotBeFound("buyingGroupName.notEquals=" + DEFAULT_BUYING_GROUP_NAME);

        // Get all the buyingGroupsList where buyingGroupName not equals to UPDATED_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldBeFound("buyingGroupName.notEquals=" + UPDATED_BUYING_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByBuyingGroupNameIsInShouldWork() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where buyingGroupName in DEFAULT_BUYING_GROUP_NAME or UPDATED_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldBeFound("buyingGroupName.in=" + DEFAULT_BUYING_GROUP_NAME + "," + UPDATED_BUYING_GROUP_NAME);

        // Get all the buyingGroupsList where buyingGroupName equals to UPDATED_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldNotBeFound("buyingGroupName.in=" + UPDATED_BUYING_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByBuyingGroupNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where buyingGroupName is not null
        defaultBuyingGroupsShouldBeFound("buyingGroupName.specified=true");

        // Get all the buyingGroupsList where buyingGroupName is null
        defaultBuyingGroupsShouldNotBeFound("buyingGroupName.specified=false");
    }
                @Test
    @Transactional
    public void getAllBuyingGroupsByBuyingGroupNameContainsSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where buyingGroupName contains DEFAULT_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldBeFound("buyingGroupName.contains=" + DEFAULT_BUYING_GROUP_NAME);

        // Get all the buyingGroupsList where buyingGroupName contains UPDATED_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldNotBeFound("buyingGroupName.contains=" + UPDATED_BUYING_GROUP_NAME);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByBuyingGroupNameNotContainsSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where buyingGroupName does not contain DEFAULT_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldNotBeFound("buyingGroupName.doesNotContain=" + DEFAULT_BUYING_GROUP_NAME);

        // Get all the buyingGroupsList where buyingGroupName does not contain UPDATED_BUYING_GROUP_NAME
        defaultBuyingGroupsShouldBeFound("buyingGroupName.doesNotContain=" + UPDATED_BUYING_GROUP_NAME);
    }


    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom equals to DEFAULT_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the buyingGroupsList where validFrom equals to UPDATED_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the buyingGroupsList where validFrom not equals to UPDATED_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the buyingGroupsList where validFrom equals to UPDATED_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom is not null
        defaultBuyingGroupsShouldBeFound("validFrom.specified=true");

        // Get all the buyingGroupsList where validFrom is null
        defaultBuyingGroupsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the buyingGroupsList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the buyingGroupsList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom is less than DEFAULT_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the buyingGroupsList where validFrom is less than UPDATED_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validFrom is greater than DEFAULT_VALID_FROM
        defaultBuyingGroupsShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the buyingGroupsList where validFrom is greater than SMALLER_VALID_FROM
        defaultBuyingGroupsShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo equals to DEFAULT_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the buyingGroupsList where validTo equals to UPDATED_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo not equals to DEFAULT_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the buyingGroupsList where validTo not equals to UPDATED_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the buyingGroupsList where validTo equals to UPDATED_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo is not null
        defaultBuyingGroupsShouldBeFound("validTo.specified=true");

        // Get all the buyingGroupsList where validTo is null
        defaultBuyingGroupsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the buyingGroupsList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the buyingGroupsList where validTo is less than or equal to SMALLER_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo is less than DEFAULT_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the buyingGroupsList where validTo is less than UPDATED_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllBuyingGroupsByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        // Get all the buyingGroupsList where validTo is greater than DEFAULT_VALID_TO
        defaultBuyingGroupsShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the buyingGroupsList where validTo is greater than SMALLER_VALID_TO
        defaultBuyingGroupsShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBuyingGroupsShouldBeFound(String filter) throws Exception {
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyingGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].buyingGroupName").value(hasItem(DEFAULT_BUYING_GROUP_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBuyingGroupsShouldNotBeFound(String filter) throws Exception {
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBuyingGroups() throws Exception {
        // Get the buyingGroups
        restBuyingGroupsMockMvc.perform(get("/api/buying-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuyingGroups() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        int databaseSizeBeforeUpdate = buyingGroupsRepository.findAll().size();

        // Update the buyingGroups
        BuyingGroups updatedBuyingGroups = buyingGroupsRepository.findById(buyingGroups.getId()).get();
        // Disconnect from session so that the updates on updatedBuyingGroups are not directly saved in db
        em.detach(updatedBuyingGroups);
        updatedBuyingGroups
            .buyingGroupName(UPDATED_BUYING_GROUP_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        BuyingGroupsDTO buyingGroupsDTO = buyingGroupsMapper.toDto(updatedBuyingGroups);

        restBuyingGroupsMockMvc.perform(put("/api/buying-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyingGroupsDTO)))
            .andExpect(status().isOk());

        // Validate the BuyingGroups in the database
        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeUpdate);
        BuyingGroups testBuyingGroups = buyingGroupsList.get(buyingGroupsList.size() - 1);
        assertThat(testBuyingGroups.getBuyingGroupName()).isEqualTo(UPDATED_BUYING_GROUP_NAME);
        assertThat(testBuyingGroups.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testBuyingGroups.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingBuyingGroups() throws Exception {
        int databaseSizeBeforeUpdate = buyingGroupsRepository.findAll().size();

        // Create the BuyingGroups
        BuyingGroupsDTO buyingGroupsDTO = buyingGroupsMapper.toDto(buyingGroups);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyingGroupsMockMvc.perform(put("/api/buying-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyingGroupsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuyingGroups in the database
        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBuyingGroups() throws Exception {
        // Initialize the database
        buyingGroupsRepository.saveAndFlush(buyingGroups);

        int databaseSizeBeforeDelete = buyingGroupsRepository.findAll().size();

        // Delete the buyingGroups
        restBuyingGroupsMockMvc.perform(delete("/api/buying-groups/{id}", buyingGroups.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BuyingGroups> buyingGroupsList = buyingGroupsRepository.findAll();
        assertThat(buyingGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingGroups.class);
        BuyingGroups buyingGroups1 = new BuyingGroups();
        buyingGroups1.setId(1L);
        BuyingGroups buyingGroups2 = new BuyingGroups();
        buyingGroups2.setId(buyingGroups1.getId());
        assertThat(buyingGroups1).isEqualTo(buyingGroups2);
        buyingGroups2.setId(2L);
        assertThat(buyingGroups1).isNotEqualTo(buyingGroups2);
        buyingGroups1.setId(null);
        assertThat(buyingGroups1).isNotEqualTo(buyingGroups2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyingGroupsDTO.class);
        BuyingGroupsDTO buyingGroupsDTO1 = new BuyingGroupsDTO();
        buyingGroupsDTO1.setId(1L);
        BuyingGroupsDTO buyingGroupsDTO2 = new BuyingGroupsDTO();
        assertThat(buyingGroupsDTO1).isNotEqualTo(buyingGroupsDTO2);
        buyingGroupsDTO2.setId(buyingGroupsDTO1.getId());
        assertThat(buyingGroupsDTO1).isEqualTo(buyingGroupsDTO2);
        buyingGroupsDTO2.setId(2L);
        assertThat(buyingGroupsDTO1).isNotEqualTo(buyingGroupsDTO2);
        buyingGroupsDTO1.setId(null);
        assertThat(buyingGroupsDTO1).isNotEqualTo(buyingGroupsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(buyingGroupsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(buyingGroupsMapper.fromId(null)).isNull();
    }
}
