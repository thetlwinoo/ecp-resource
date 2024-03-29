package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.Products;
import com.resource.server.domain.ProductDocument;
import com.resource.server.domain.StockItems;
import com.resource.server.domain.Suppliers;
import com.resource.server.domain.ProductCategory;
import com.resource.server.domain.ProductBrand;
import com.resource.server.repository.ProductsRepository;
import com.resource.server.service.ProductsService;
import com.resource.server.service.dto.ProductsDTO;
import com.resource.server.service.mapper.ProductsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ProductsCriteria;
import com.resource.server.service.ProductsQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link ProductsResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ProductsResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DETAILS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;
    private static final Integer SMALLER_SELL_COUNT = 1 - 1;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_EDITED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EDITED_WHEN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_EDITED_WHEN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductsQueryService productsQueryService;

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

    private MockMvc restProductsMockMvc;

    private Products products;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductsResource productsResource = new ProductsResource(productsService, productsQueryService);
        this.restProductsMockMvc = MockMvcBuilders.standaloneSetup(productsResource)
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
    public static Products createEntity(EntityManager em) {
        Products products = new Products()
            .productName(DEFAULT_PRODUCT_NAME)
            .handle(DEFAULT_HANDLE)
            .productNumber(DEFAULT_PRODUCT_NUMBER)
            .searchDetails(DEFAULT_SEARCH_DETAILS)
            .sellCount(DEFAULT_SELL_COUNT)
            .activeInd(DEFAULT_ACTIVE_IND)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return products;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createUpdatedEntity(EntityManager em) {
        Products products = new Products()
            .productName(UPDATED_PRODUCT_NAME)
            .handle(UPDATED_HANDLE)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .sellCount(UPDATED_SELL_COUNT)
            .activeInd(UPDATED_ACTIVE_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return products;
    }

    @BeforeEach
    public void initTest() {
        products = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducts() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);
        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isCreated());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate + 1);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProducts.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testProducts.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testProducts.getSearchDetails()).isEqualTo(DEFAULT_SEARCH_DETAILS);
        assertThat(testProducts.getSellCount()).isEqualTo(DEFAULT_SELL_COUNT);
        assertThat(testProducts.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testProducts.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testProducts.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products with an existing ID
        products.setId(1L);
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setProductName(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);

        restProductsMockMvc.perform(post("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList
        restProductsMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", products.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(products.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))
            .andExpect(jsonPath("$.productNumber").value(DEFAULT_PRODUCT_NUMBER))
            .andExpect(jsonPath("$.searchDetails").value(DEFAULT_SEARCH_DETAILS.toString()))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName equals to DEFAULT_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productsList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName not equals to DEFAULT_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.notEquals=" + DEFAULT_PRODUCT_NAME);

        // Get all the productsList where productName not equals to UPDATED_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.notEquals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the productsList where productName equals to UPDATED_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName is not null
        defaultProductsShouldBeFound("productName.specified=true");

        // Get all the productsList where productName is null
        defaultProductsShouldNotBeFound("productName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByProductNameContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName contains DEFAULT_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the productsList where productName contains UPDATED_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultProductsShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the productsList where productName does not contain UPDATED_PRODUCT_NAME
        defaultProductsShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByHandleIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle equals to DEFAULT_HANDLE
        defaultProductsShouldBeFound("handle.equals=" + DEFAULT_HANDLE);

        // Get all the productsList where handle equals to UPDATED_HANDLE
        defaultProductsShouldNotBeFound("handle.equals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle not equals to DEFAULT_HANDLE
        defaultProductsShouldNotBeFound("handle.notEquals=" + DEFAULT_HANDLE);

        // Get all the productsList where handle not equals to UPDATED_HANDLE
        defaultProductsShouldBeFound("handle.notEquals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle in DEFAULT_HANDLE or UPDATED_HANDLE
        defaultProductsShouldBeFound("handle.in=" + DEFAULT_HANDLE + "," + UPDATED_HANDLE);

        // Get all the productsList where handle equals to UPDATED_HANDLE
        defaultProductsShouldNotBeFound("handle.in=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle is not null
        defaultProductsShouldBeFound("handle.specified=true");

        // Get all the productsList where handle is null
        defaultProductsShouldNotBeFound("handle.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByHandleContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle contains DEFAULT_HANDLE
        defaultProductsShouldBeFound("handle.contains=" + DEFAULT_HANDLE);

        // Get all the productsList where handle contains UPDATED_HANDLE
        defaultProductsShouldNotBeFound("handle.contains=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle does not contain DEFAULT_HANDLE
        defaultProductsShouldNotBeFound("handle.doesNotContain=" + DEFAULT_HANDLE);

        // Get all the productsList where handle does not contain UPDATED_HANDLE
        defaultProductsShouldBeFound("handle.doesNotContain=" + UPDATED_HANDLE);
    }


    @Test
    @Transactional
    public void getAllProductsByProductNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber equals to DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.equals=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.equals=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber not equals to DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.notEquals=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber not equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.notEquals=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber in DEFAULT_PRODUCT_NUMBER or UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.in=" + DEFAULT_PRODUCT_NUMBER + "," + UPDATED_PRODUCT_NUMBER);

        // Get all the productsList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.in=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber is not null
        defaultProductsShouldBeFound("productNumber.specified=true");

        // Get all the productsList where productNumber is null
        defaultProductsShouldNotBeFound("productNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByProductNumberContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber contains DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.contains=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber contains UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.contains=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber does not contain DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.doesNotContain=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber does not contain UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.doesNotContain=" + UPDATED_PRODUCT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllProductsBySellCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount equals to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.equals=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.equals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount not equals to DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.notEquals=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount not equals to UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.notEquals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount in DEFAULT_SELL_COUNT or UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.in=" + DEFAULT_SELL_COUNT + "," + UPDATED_SELL_COUNT);

        // Get all the productsList where sellCount equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.in=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is not null
        defaultProductsShouldBeFound("sellCount.specified=true");

        // Get all the productsList where sellCount is null
        defaultProductsShouldNotBeFound("sellCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is greater than or equal to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.greaterThanOrEqual=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is greater than or equal to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.greaterThanOrEqual=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is less than or equal to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.lessThanOrEqual=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is less than or equal to SMALLER_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.lessThanOrEqual=" + SMALLER_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is less than DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.lessThan=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is less than UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.lessThan=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is greater than DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.greaterThan=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is greater than SMALLER_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.greaterThan=" + SMALLER_SELL_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultProductsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the productsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultProductsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultProductsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the productsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultProductsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultProductsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the productsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultProductsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd is not null
        defaultProductsShouldBeFound("activeInd.specified=true");

        // Get all the productsList where activeInd is null
        defaultProductsShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy is not null
        defaultProductsShouldBeFound("lastEditedBy.specified=true");

        // Get all the productsList where lastEditedBy is null
        defaultProductsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen is not null
        defaultProductsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the productsList where lastEditedWhen is null
        defaultProductsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen is greater than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.greaterThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen is greater than or equal to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.greaterThanOrEqual=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen is less than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.lessThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen is less than or equal to SMALLER_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.lessThanOrEqual=" + SMALLER_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen is less than DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.lessThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen is less than UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.lessThan=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen is greater than DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.greaterThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen is greater than SMALLER_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.greaterThan=" + SMALLER_LAST_EDITED_WHEN);
    }


    @Test
    @Transactional
    public void getAllProductsByDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        ProductDocument document = ProductDocumentResourceIT.createEntity(em);
        em.persist(document);
        em.flush();
        products.setDocument(document);
        productsRepository.saveAndFlush(products);
        Long documentId = document.getId();

        // Get all the productsList where document equals to documentId
        defaultProductsShouldBeFound("documentId.equals=" + documentId);

        // Get all the productsList where document equals to documentId + 1
        defaultProductsShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByStockItemListIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        StockItems stockItemList = StockItemsResourceIT.createEntity(em);
        em.persist(stockItemList);
        em.flush();
        products.addStockItemList(stockItemList);
        productsRepository.saveAndFlush(products);
        Long stockItemListId = stockItemList.getId();

        // Get all the productsList where stockItemList equals to stockItemListId
        defaultProductsShouldBeFound("stockItemListId.equals=" + stockItemListId);

        // Get all the productsList where stockItemList equals to stockItemListId + 1
        defaultProductsShouldNotBeFound("stockItemListId.equals=" + (stockItemListId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        products.setSupplier(supplier);
        productsRepository.saveAndFlush(products);
        Long supplierId = supplier.getId();

        // Get all the productsList where supplier equals to supplierId
        defaultProductsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productsList where supplier equals to supplierId + 1
        defaultProductsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        products.setProductCategory(productCategory);
        productsRepository.saveAndFlush(products);
        Long productCategoryId = productCategory.getId();

        // Get all the productsList where productCategory equals to productCategoryId
        defaultProductsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productsList where productCategory equals to productCategoryId + 1
        defaultProductsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        ProductBrand productBrand = ProductBrandResourceIT.createEntity(em);
        em.persist(productBrand);
        em.flush();
        products.setProductBrand(productBrand);
        productsRepository.saveAndFlush(products);
        Long productBrandId = productBrand.getId();

        // Get all the productsList where productBrand equals to productBrandId
        defaultProductsShouldBeFound("productBrandId.equals=" + productBrandId);

        // Get all the productsList where productBrand equals to productBrandId + 1
        defaultProductsShouldNotBeFound("productBrandId.equals=" + (productBrandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductsShouldBeFound(String filter) throws Exception {
        restProductsMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restProductsMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductsShouldNotBeFound(String filter) throws Exception {
        restProductsMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductsMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProducts() throws Exception {
        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products
        Products updatedProducts = productsRepository.findById(products.getId()).get();
        // Disconnect from session so that the updates on updatedProducts are not directly saved in db
        em.detach(updatedProducts);
        updatedProducts
            .productName(UPDATED_PRODUCT_NAME)
            .handle(UPDATED_HANDLE)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .sellCount(UPDATED_SELL_COUNT)
            .activeInd(UPDATED_ACTIVE_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ProductsDTO productsDTO = productsMapper.toDto(updatedProducts);

        restProductsMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProducts.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testProducts.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testProducts.getSearchDetails()).isEqualTo(UPDATED_SEARCH_DETAILS);
        assertThat(testProducts.getSellCount()).isEqualTo(UPDATED_SELL_COUNT);
        assertThat(testProducts.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testProducts.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testProducts.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsMockMvc.perform(put("/api/products")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeDelete = productsRepository.findAll().size();

        // Delete the products
        restProductsMockMvc.perform(delete("/api/products/{id}", products.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Products.class);
        Products products1 = new Products();
        products1.setId(1L);
        Products products2 = new Products();
        products2.setId(products1.getId());
        assertThat(products1).isEqualTo(products2);
        products2.setId(2L);
        assertThat(products1).isNotEqualTo(products2);
        products1.setId(null);
        assertThat(products1).isNotEqualTo(products2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductsDTO.class);
        ProductsDTO productsDTO1 = new ProductsDTO();
        productsDTO1.setId(1L);
        ProductsDTO productsDTO2 = new ProductsDTO();
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
        productsDTO2.setId(productsDTO1.getId());
        assertThat(productsDTO1).isEqualTo(productsDTO2);
        productsDTO2.setId(2L);
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
        productsDTO1.setId(null);
        assertThat(productsDTO1).isNotEqualTo(productsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productsMapper.fromId(null)).isNull();
    }
}
