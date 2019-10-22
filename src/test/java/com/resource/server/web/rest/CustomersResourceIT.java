package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.Customers;
import com.resource.server.domain.People;
import com.resource.server.repository.CustomersRepository;
import com.resource.server.service.CustomersService;
import com.resource.server.service.dto.CustomersDTO;
import com.resource.server.service.mapper.CustomersMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.CustomersCriteria;
import com.resource.server.service.CustomersQueryService;

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
 * Integration tests for the {@link CustomersResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class CustomersResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private CustomersService customersService;

    @Autowired
    private CustomersQueryService customersQueryService;

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

    private MockMvc restCustomersMockMvc;

    private Customers customers;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CustomersResource customersResource = new CustomersResource(customersService, customersQueryService);
        this.restCustomersMockMvc = MockMvcBuilders.standaloneSetup(customersResource)
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
    public static Customers createEntity(EntityManager em) {
        Customers customers = new Customers()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER);
        return customers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createUpdatedEntity(EntityManager em) {
        Customers customers = new Customers()
            .accountNumber(UPDATED_ACCOUNT_NUMBER);
        return customers;
    }

    @BeforeEach
    public void initTest() {
        customers = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomers() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);
        restCustomersMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isCreated());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate + 1);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void createCustomersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // Create the Customers with an existing ID
        customers.setId(1L);
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setAccountNumber(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        restCustomersMockMvc.perform(post("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get the customers
        restCustomersMockMvc.perform(get("/api/customers/{id}", customers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(customers.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER));
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber is not null
        defaultCustomersShouldBeFound("accountNumber.specified=true");

        // Get all the customersList where accountNumber is null
        defaultCustomersShouldNotBeFound("accountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCustomersByPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        People person = PeopleResourceIT.createEntity(em);
        em.persist(person);
        em.flush();
        customers.setPerson(person);
        customersRepository.saveAndFlush(customers);
        Long personId = person.getId();

        // Get all the customersList where person equals to personId
        defaultCustomersShouldBeFound("personId.equals=" + personId);

        // Get all the customersList where person equals to personId + 1
        defaultCustomersShouldNotBeFound("personId.equals=" + (personId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomersShouldBeFound(String filter) throws Exception {
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)));

        // Check, that the count call also returns 1
        restCustomersMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomersShouldNotBeFound(String filter) throws Exception {
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomersMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCustomers() throws Exception {
        // Get the customers
        restCustomersMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers
        Customers updatedCustomers = customersRepository.findById(customers.getId()).get();
        // Disconnect from session so that the updates on updatedCustomers are not directly saved in db
        em.detach(updatedCustomers);
        updatedCustomers
            .accountNumber(UPDATED_ACCOUNT_NUMBER);
        CustomersDTO customersDTO = customersMapper.toDto(updatedCustomers);

        restCustomersMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc.perform(put("/api/customers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeDelete = customersRepository.findAll().size();

        // Delete the customers
        restCustomersMockMvc.perform(delete("/api/customers/{id}", customers.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Customers.class);
        Customers customers1 = new Customers();
        customers1.setId(1L);
        Customers customers2 = new Customers();
        customers2.setId(customers1.getId());
        assertThat(customers1).isEqualTo(customers2);
        customers2.setId(2L);
        assertThat(customers1).isNotEqualTo(customers2);
        customers1.setId(null);
        assertThat(customers1).isNotEqualTo(customers2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomersDTO.class);
        CustomersDTO customersDTO1 = new CustomersDTO();
        customersDTO1.setId(1L);
        CustomersDTO customersDTO2 = new CustomersDTO();
        assertThat(customersDTO1).isNotEqualTo(customersDTO2);
        customersDTO2.setId(customersDTO1.getId());
        assertThat(customersDTO1).isEqualTo(customersDTO2);
        customersDTO2.setId(2L);
        assertThat(customersDTO1).isNotEqualTo(customersDTO2);
        customersDTO1.setId(null);
        assertThat(customersDTO1).isNotEqualTo(customersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(customersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(customersMapper.fromId(null)).isNull();
    }
}
