package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.PurchaseOrderLines;
import com.resource.server.domain.PackageTypes;
import com.resource.server.domain.StockItems;
import com.resource.server.domain.PurchaseOrders;
import com.resource.server.repository.PurchaseOrderLinesRepository;
import com.resource.server.service.PurchaseOrderLinesService;
import com.resource.server.service.dto.PurchaseOrderLinesDTO;
import com.resource.server.service.mapper.PurchaseOrderLinesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.PurchaseOrderLinesCriteria;
import com.resource.server.service.PurchaseOrderLinesQueryService;

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
 * Integration tests for the {@link PurchaseOrderLinesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class PurchaseOrderLinesResourceIT {

    private static final Integer DEFAULT_ORDERS_OUTERS = 1;
    private static final Integer UPDATED_ORDERS_OUTERS = 2;
    private static final Integer SMALLER_ORDERS_OUTERS = 1 - 1;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_RECEIVED_OUTERS = 1;
    private static final Integer UPDATED_RECEIVED_OUTERS = 2;
    private static final Integer SMALLER_RECEIVED_OUTERS = 1 - 1;

    private static final Float DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER = 1F;
    private static final Float UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER = 2F;
    private static final Float SMALLER_EXPECTED_UNIT_PRICE_PER_OUTER = 1F - 1F;

    private static final LocalDate DEFAULT_LAST_RECEIPT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_RECEIPT_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_RECEIPT_DATE = LocalDate.ofEpochDay(-1L);

    private static final Boolean DEFAULT_IS_ORDER_LINE_FINALIZED = false;
    private static final Boolean UPDATED_IS_ORDER_LINE_FINALIZED = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_EDITED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EDITED_WHEN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_EDITED_WHEN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PurchaseOrderLinesRepository purchaseOrderLinesRepository;

    @Autowired
    private PurchaseOrderLinesMapper purchaseOrderLinesMapper;

    @Autowired
    private PurchaseOrderLinesService purchaseOrderLinesService;

    @Autowired
    private PurchaseOrderLinesQueryService purchaseOrderLinesQueryService;

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

    private MockMvc restPurchaseOrderLinesMockMvc;

    private PurchaseOrderLines purchaseOrderLines;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchaseOrderLinesResource purchaseOrderLinesResource = new PurchaseOrderLinesResource(purchaseOrderLinesService, purchaseOrderLinesQueryService);
        this.restPurchaseOrderLinesMockMvc = MockMvcBuilders.standaloneSetup(purchaseOrderLinesResource)
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
    public static PurchaseOrderLines createEntity(EntityManager em) {
        PurchaseOrderLines purchaseOrderLines = new PurchaseOrderLines()
            .ordersOuters(DEFAULT_ORDERS_OUTERS)
            .description(DEFAULT_DESCRIPTION)
            .receivedOuters(DEFAULT_RECEIVED_OUTERS)
            .expectedUnitPricePerOuter(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER)
            .lastReceiptDate(DEFAULT_LAST_RECEIPT_DATE)
            .isOrderLineFinalized(DEFAULT_IS_ORDER_LINE_FINALIZED)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return purchaseOrderLines;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrderLines createUpdatedEntity(EntityManager em) {
        PurchaseOrderLines purchaseOrderLines = new PurchaseOrderLines()
            .ordersOuters(UPDATED_ORDERS_OUTERS)
            .description(UPDATED_DESCRIPTION)
            .receivedOuters(UPDATED_RECEIVED_OUTERS)
            .expectedUnitPricePerOuter(UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER)
            .lastReceiptDate(UPDATED_LAST_RECEIPT_DATE)
            .isOrderLineFinalized(UPDATED_IS_ORDER_LINE_FINALIZED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return purchaseOrderLines;
    }

    @BeforeEach
    public void initTest() {
        purchaseOrderLines = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrderLines() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderLinesRepository.findAll().size();

        // Create the PurchaseOrderLines
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);
        restPurchaseOrderLinesMockMvc.perform(post("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrderLines testPurchaseOrderLines = purchaseOrderLinesList.get(purchaseOrderLinesList.size() - 1);
        assertThat(testPurchaseOrderLines.getOrdersOuters()).isEqualTo(DEFAULT_ORDERS_OUTERS);
        assertThat(testPurchaseOrderLines.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPurchaseOrderLines.getReceivedOuters()).isEqualTo(DEFAULT_RECEIVED_OUTERS);
        assertThat(testPurchaseOrderLines.getExpectedUnitPricePerOuter()).isEqualTo(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);
        assertThat(testPurchaseOrderLines.getLastReceiptDate()).isEqualTo(DEFAULT_LAST_RECEIPT_DATE);
        assertThat(testPurchaseOrderLines.isIsOrderLineFinalized()).isEqualTo(DEFAULT_IS_ORDER_LINE_FINALIZED);
        assertThat(testPurchaseOrderLines.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testPurchaseOrderLines.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createPurchaseOrderLinesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderLinesRepository.findAll().size();

        // Create the PurchaseOrderLines with an existing ID
        purchaseOrderLines.setId(1L);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderLinesMockMvc.perform(post("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrdersOutersIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrderLinesRepository.findAll().size();
        // set the field null
        purchaseOrderLines.setOrdersOuters(null);

        // Create the PurchaseOrderLines, which fails.
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);

        restPurchaseOrderLinesMockMvc.perform(post("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isBadRequest());

        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordersOuters").value(hasItem(DEFAULT_ORDERS_OUTERS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].receivedOuters").value(hasItem(DEFAULT_RECEIVED_OUTERS)))
            .andExpect(jsonPath("$.[*].expectedUnitPricePerOuter").value(hasItem(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER.doubleValue())))
            .andExpect(jsonPath("$.[*].lastReceiptDate").value(hasItem(DEFAULT_LAST_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOrderLineFinalized").value(hasItem(DEFAULT_IS_ORDER_LINE_FINALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get the purchaseOrderLines
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines/{id}", purchaseOrderLines.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrderLines.getId().intValue()))
            .andExpect(jsonPath("$.ordersOuters").value(DEFAULT_ORDERS_OUTERS))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.receivedOuters").value(DEFAULT_RECEIVED_OUTERS))
            .andExpect(jsonPath("$.expectedUnitPricePerOuter").value(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER.doubleValue()))
            .andExpect(jsonPath("$.lastReceiptDate").value(DEFAULT_LAST_RECEIPT_DATE.toString()))
            .andExpect(jsonPath("$.isOrderLineFinalized").value(DEFAULT_IS_ORDER_LINE_FINALIZED.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters equals to DEFAULT_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.equals=" + DEFAULT_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters equals to UPDATED_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.equals=" + UPDATED_ORDERS_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters not equals to DEFAULT_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.notEquals=" + DEFAULT_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters not equals to UPDATED_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.notEquals=" + UPDATED_ORDERS_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters in DEFAULT_ORDERS_OUTERS or UPDATED_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.in=" + DEFAULT_ORDERS_OUTERS + "," + UPDATED_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters equals to UPDATED_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.in=" + UPDATED_ORDERS_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters is not null
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.specified=true");

        // Get all the purchaseOrderLinesList where ordersOuters is null
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters is greater than or equal to DEFAULT_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.greaterThanOrEqual=" + DEFAULT_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters is greater than or equal to UPDATED_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.greaterThanOrEqual=" + UPDATED_ORDERS_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters is less than or equal to DEFAULT_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.lessThanOrEqual=" + DEFAULT_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters is less than or equal to SMALLER_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.lessThanOrEqual=" + SMALLER_ORDERS_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters is less than DEFAULT_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.lessThan=" + DEFAULT_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters is less than UPDATED_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.lessThan=" + UPDATED_ORDERS_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByOrdersOutersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where ordersOuters is greater than DEFAULT_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("ordersOuters.greaterThan=" + DEFAULT_ORDERS_OUTERS);

        // Get all the purchaseOrderLinesList where ordersOuters is greater than SMALLER_ORDERS_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("ordersOuters.greaterThan=" + SMALLER_ORDERS_OUTERS);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where description equals to DEFAULT_DESCRIPTION
        defaultPurchaseOrderLinesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the purchaseOrderLinesList where description equals to UPDATED_DESCRIPTION
        defaultPurchaseOrderLinesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where description not equals to DEFAULT_DESCRIPTION
        defaultPurchaseOrderLinesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the purchaseOrderLinesList where description not equals to UPDATED_DESCRIPTION
        defaultPurchaseOrderLinesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPurchaseOrderLinesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the purchaseOrderLinesList where description equals to UPDATED_DESCRIPTION
        defaultPurchaseOrderLinesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where description is not null
        defaultPurchaseOrderLinesShouldBeFound("description.specified=true");

        // Get all the purchaseOrderLinesList where description is null
        defaultPurchaseOrderLinesShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrderLinesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where description contains DEFAULT_DESCRIPTION
        defaultPurchaseOrderLinesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the purchaseOrderLinesList where description contains UPDATED_DESCRIPTION
        defaultPurchaseOrderLinesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where description does not contain DEFAULT_DESCRIPTION
        defaultPurchaseOrderLinesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the purchaseOrderLinesList where description does not contain UPDATED_DESCRIPTION
        defaultPurchaseOrderLinesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters equals to DEFAULT_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.equals=" + DEFAULT_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters equals to UPDATED_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.equals=" + UPDATED_RECEIVED_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters not equals to DEFAULT_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.notEquals=" + DEFAULT_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters not equals to UPDATED_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.notEquals=" + UPDATED_RECEIVED_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters in DEFAULT_RECEIVED_OUTERS or UPDATED_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.in=" + DEFAULT_RECEIVED_OUTERS + "," + UPDATED_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters equals to UPDATED_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.in=" + UPDATED_RECEIVED_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters is not null
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.specified=true");

        // Get all the purchaseOrderLinesList where receivedOuters is null
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters is greater than or equal to DEFAULT_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.greaterThanOrEqual=" + DEFAULT_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters is greater than or equal to UPDATED_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.greaterThanOrEqual=" + UPDATED_RECEIVED_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters is less than or equal to DEFAULT_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.lessThanOrEqual=" + DEFAULT_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters is less than or equal to SMALLER_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.lessThanOrEqual=" + SMALLER_RECEIVED_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters is less than DEFAULT_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.lessThan=" + DEFAULT_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters is less than UPDATED_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.lessThan=" + UPDATED_RECEIVED_OUTERS);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByReceivedOutersIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where receivedOuters is greater than DEFAULT_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldNotBeFound("receivedOuters.greaterThan=" + DEFAULT_RECEIVED_OUTERS);

        // Get all the purchaseOrderLinesList where receivedOuters is greater than SMALLER_RECEIVED_OUTERS
        defaultPurchaseOrderLinesShouldBeFound("receivedOuters.greaterThan=" + SMALLER_RECEIVED_OUTERS);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter equals to DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.equals=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter equals to UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.equals=" + UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter not equals to DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.notEquals=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter not equals to UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.notEquals=" + UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter in DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER or UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.in=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER + "," + UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter equals to UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.in=" + UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is not null
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.specified=true");

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is null
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is greater than or equal to DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.greaterThanOrEqual=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is greater than or equal to UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.greaterThanOrEqual=" + UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is less than or equal to DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.lessThanOrEqual=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is less than or equal to SMALLER_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.lessThanOrEqual=" + SMALLER_EXPECTED_UNIT_PRICE_PER_OUTER);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is less than DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.lessThan=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is less than UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.lessThan=" + UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByExpectedUnitPricePerOuterIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is greater than DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldNotBeFound("expectedUnitPricePerOuter.greaterThan=" + DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER);

        // Get all the purchaseOrderLinesList where expectedUnitPricePerOuter is greater than SMALLER_EXPECTED_UNIT_PRICE_PER_OUTER
        defaultPurchaseOrderLinesShouldBeFound("expectedUnitPricePerOuter.greaterThan=" + SMALLER_EXPECTED_UNIT_PRICE_PER_OUTER);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate equals to DEFAULT_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.equals=" + DEFAULT_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate equals to UPDATED_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.equals=" + UPDATED_LAST_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate not equals to DEFAULT_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.notEquals=" + DEFAULT_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate not equals to UPDATED_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.notEquals=" + UPDATED_LAST_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate in DEFAULT_LAST_RECEIPT_DATE or UPDATED_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.in=" + DEFAULT_LAST_RECEIPT_DATE + "," + UPDATED_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate equals to UPDATED_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.in=" + UPDATED_LAST_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate is not null
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.specified=true");

        // Get all the purchaseOrderLinesList where lastReceiptDate is null
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate is greater than or equal to DEFAULT_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.greaterThanOrEqual=" + DEFAULT_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate is greater than or equal to UPDATED_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.greaterThanOrEqual=" + UPDATED_LAST_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate is less than or equal to DEFAULT_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.lessThanOrEqual=" + DEFAULT_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate is less than or equal to SMALLER_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.lessThanOrEqual=" + SMALLER_LAST_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate is less than DEFAULT_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.lessThan=" + DEFAULT_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate is less than UPDATED_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.lessThan=" + UPDATED_LAST_RECEIPT_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastReceiptDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastReceiptDate is greater than DEFAULT_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldNotBeFound("lastReceiptDate.greaterThan=" + DEFAULT_LAST_RECEIPT_DATE);

        // Get all the purchaseOrderLinesList where lastReceiptDate is greater than SMALLER_LAST_RECEIPT_DATE
        defaultPurchaseOrderLinesShouldBeFound("lastReceiptDate.greaterThan=" + SMALLER_LAST_RECEIPT_DATE);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByIsOrderLineFinalizedIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized equals to DEFAULT_IS_ORDER_LINE_FINALIZED
        defaultPurchaseOrderLinesShouldBeFound("isOrderLineFinalized.equals=" + DEFAULT_IS_ORDER_LINE_FINALIZED);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized equals to UPDATED_IS_ORDER_LINE_FINALIZED
        defaultPurchaseOrderLinesShouldNotBeFound("isOrderLineFinalized.equals=" + UPDATED_IS_ORDER_LINE_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByIsOrderLineFinalizedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized not equals to DEFAULT_IS_ORDER_LINE_FINALIZED
        defaultPurchaseOrderLinesShouldNotBeFound("isOrderLineFinalized.notEquals=" + DEFAULT_IS_ORDER_LINE_FINALIZED);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized not equals to UPDATED_IS_ORDER_LINE_FINALIZED
        defaultPurchaseOrderLinesShouldBeFound("isOrderLineFinalized.notEquals=" + UPDATED_IS_ORDER_LINE_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByIsOrderLineFinalizedIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized in DEFAULT_IS_ORDER_LINE_FINALIZED or UPDATED_IS_ORDER_LINE_FINALIZED
        defaultPurchaseOrderLinesShouldBeFound("isOrderLineFinalized.in=" + DEFAULT_IS_ORDER_LINE_FINALIZED + "," + UPDATED_IS_ORDER_LINE_FINALIZED);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized equals to UPDATED_IS_ORDER_LINE_FINALIZED
        defaultPurchaseOrderLinesShouldNotBeFound("isOrderLineFinalized.in=" + UPDATED_IS_ORDER_LINE_FINALIZED);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByIsOrderLineFinalizedIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where isOrderLineFinalized is not null
        defaultPurchaseOrderLinesShouldBeFound("isOrderLineFinalized.specified=true");

        // Get all the purchaseOrderLinesList where isOrderLineFinalized is null
        defaultPurchaseOrderLinesShouldNotBeFound("isOrderLineFinalized.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrderLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrderLinesList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the purchaseOrderLinesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedBy is not null
        defaultPurchaseOrderLinesShouldBeFound("lastEditedBy.specified=true");

        // Get all the purchaseOrderLinesList where lastEditedBy is null
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrderLinesList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the purchaseOrderLinesList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultPurchaseOrderLinesShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen is not null
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.specified=true");

        // Get all the purchaseOrderLinesList where lastEditedWhen is null
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen is greater than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.greaterThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen is greater than or equal to UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.greaterThanOrEqual=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen is less than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.lessThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen is less than or equal to SMALLER_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.lessThanOrEqual=" + SMALLER_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen is less than DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.lessThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen is less than UPDATED_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.lessThan=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByLastEditedWhenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        // Get all the purchaseOrderLinesList where lastEditedWhen is greater than DEFAULT_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldNotBeFound("lastEditedWhen.greaterThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the purchaseOrderLinesList where lastEditedWhen is greater than SMALLER_LAST_EDITED_WHEN
        defaultPurchaseOrderLinesShouldBeFound("lastEditedWhen.greaterThan=" + SMALLER_LAST_EDITED_WHEN);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByPackageTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);
        PackageTypes packageType = PackageTypesResourceIT.createEntity(em);
        em.persist(packageType);
        em.flush();
        purchaseOrderLines.setPackageType(packageType);
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);
        Long packageTypeId = packageType.getId();

        // Get all the purchaseOrderLinesList where packageType equals to packageTypeId
        defaultPurchaseOrderLinesShouldBeFound("packageTypeId.equals=" + packageTypeId);

        // Get all the purchaseOrderLinesList where packageType equals to packageTypeId + 1
        defaultPurchaseOrderLinesShouldNotBeFound("packageTypeId.equals=" + (packageTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByStockItemIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);
        StockItems stockItem = StockItemsResourceIT.createEntity(em);
        em.persist(stockItem);
        em.flush();
        purchaseOrderLines.setStockItem(stockItem);
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);
        Long stockItemId = stockItem.getId();

        // Get all the purchaseOrderLinesList where stockItem equals to stockItemId
        defaultPurchaseOrderLinesShouldBeFound("stockItemId.equals=" + stockItemId);

        // Get all the purchaseOrderLinesList where stockItem equals to stockItemId + 1
        defaultPurchaseOrderLinesShouldNotBeFound("stockItemId.equals=" + (stockItemId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrderLinesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);
        PurchaseOrders purchaseOrder = PurchaseOrdersResourceIT.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        purchaseOrderLines.setPurchaseOrder(purchaseOrder);
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the purchaseOrderLinesList where purchaseOrder equals to purchaseOrderId
        defaultPurchaseOrderLinesShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the purchaseOrderLinesList where purchaseOrder equals to purchaseOrderId + 1
        defaultPurchaseOrderLinesShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPurchaseOrderLinesShouldBeFound(String filter) throws Exception {
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrderLines.getId().intValue())))
            .andExpect(jsonPath("$.[*].ordersOuters").value(hasItem(DEFAULT_ORDERS_OUTERS)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].receivedOuters").value(hasItem(DEFAULT_RECEIVED_OUTERS)))
            .andExpect(jsonPath("$.[*].expectedUnitPricePerOuter").value(hasItem(DEFAULT_EXPECTED_UNIT_PRICE_PER_OUTER.doubleValue())))
            .andExpect(jsonPath("$.[*].lastReceiptDate").value(hasItem(DEFAULT_LAST_RECEIPT_DATE.toString())))
            .andExpect(jsonPath("$.[*].isOrderLineFinalized").value(hasItem(DEFAULT_IS_ORDER_LINE_FINALIZED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPurchaseOrderLinesShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPurchaseOrderLines() throws Exception {
        // Get the purchaseOrderLines
        restPurchaseOrderLinesMockMvc.perform(get("/api/purchase-order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        int databaseSizeBeforeUpdate = purchaseOrderLinesRepository.findAll().size();

        // Update the purchaseOrderLines
        PurchaseOrderLines updatedPurchaseOrderLines = purchaseOrderLinesRepository.findById(purchaseOrderLines.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrderLines are not directly saved in db
        em.detach(updatedPurchaseOrderLines);
        updatedPurchaseOrderLines
            .ordersOuters(UPDATED_ORDERS_OUTERS)
            .description(UPDATED_DESCRIPTION)
            .receivedOuters(UPDATED_RECEIVED_OUTERS)
            .expectedUnitPricePerOuter(UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER)
            .lastReceiptDate(UPDATED_LAST_RECEIPT_DATE)
            .isOrderLineFinalized(UPDATED_IS_ORDER_LINE_FINALIZED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(updatedPurchaseOrderLines);

        restPurchaseOrderLinesMockMvc.perform(put("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrderLines testPurchaseOrderLines = purchaseOrderLinesList.get(purchaseOrderLinesList.size() - 1);
        assertThat(testPurchaseOrderLines.getOrdersOuters()).isEqualTo(UPDATED_ORDERS_OUTERS);
        assertThat(testPurchaseOrderLines.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPurchaseOrderLines.getReceivedOuters()).isEqualTo(UPDATED_RECEIVED_OUTERS);
        assertThat(testPurchaseOrderLines.getExpectedUnitPricePerOuter()).isEqualTo(UPDATED_EXPECTED_UNIT_PRICE_PER_OUTER);
        assertThat(testPurchaseOrderLines.getLastReceiptDate()).isEqualTo(UPDATED_LAST_RECEIPT_DATE);
        assertThat(testPurchaseOrderLines.isIsOrderLineFinalized()).isEqualTo(UPDATED_IS_ORDER_LINE_FINALIZED);
        assertThat(testPurchaseOrderLines.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testPurchaseOrderLines.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrderLines() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderLinesRepository.findAll().size();

        // Create the PurchaseOrderLines
        PurchaseOrderLinesDTO purchaseOrderLinesDTO = purchaseOrderLinesMapper.toDto(purchaseOrderLines);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderLinesMockMvc.perform(put("/api/purchase-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrderLinesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrderLines in the database
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseOrderLines() throws Exception {
        // Initialize the database
        purchaseOrderLinesRepository.saveAndFlush(purchaseOrderLines);

        int databaseSizeBeforeDelete = purchaseOrderLinesRepository.findAll().size();

        // Delete the purchaseOrderLines
        restPurchaseOrderLinesMockMvc.perform(delete("/api/purchase-order-lines/{id}", purchaseOrderLines.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseOrderLines> purchaseOrderLinesList = purchaseOrderLinesRepository.findAll();
        assertThat(purchaseOrderLinesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderLines.class);
        PurchaseOrderLines purchaseOrderLines1 = new PurchaseOrderLines();
        purchaseOrderLines1.setId(1L);
        PurchaseOrderLines purchaseOrderLines2 = new PurchaseOrderLines();
        purchaseOrderLines2.setId(purchaseOrderLines1.getId());
        assertThat(purchaseOrderLines1).isEqualTo(purchaseOrderLines2);
        purchaseOrderLines2.setId(2L);
        assertThat(purchaseOrderLines1).isNotEqualTo(purchaseOrderLines2);
        purchaseOrderLines1.setId(null);
        assertThat(purchaseOrderLines1).isNotEqualTo(purchaseOrderLines2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchaseOrderLinesDTO.class);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO1 = new PurchaseOrderLinesDTO();
        purchaseOrderLinesDTO1.setId(1L);
        PurchaseOrderLinesDTO purchaseOrderLinesDTO2 = new PurchaseOrderLinesDTO();
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO2.setId(purchaseOrderLinesDTO1.getId());
        assertThat(purchaseOrderLinesDTO1).isEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO2.setId(2L);
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
        purchaseOrderLinesDTO1.setId(null);
        assertThat(purchaseOrderLinesDTO1).isNotEqualTo(purchaseOrderLinesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchaseOrderLinesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchaseOrderLinesMapper.fromId(null)).isNull();
    }
}
