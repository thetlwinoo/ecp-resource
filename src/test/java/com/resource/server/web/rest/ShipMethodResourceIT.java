package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.ShipMethod;
import com.resource.server.repository.ShipMethodRepository;
import com.resource.server.service.ShipMethodService;
import com.resource.server.service.dto.ShipMethodDTO;
import com.resource.server.service.mapper.ShipMethodMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ShipMethodCriteria;
import com.resource.server.service.ShipMethodQueryService;

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
 * Integration tests for the {@link ShipMethodResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ShipMethodResourceIT {

    private static final String DEFAULT_SHIP_METHOD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_METHOD_NAME = "BBBBBBBBBB";

    @Autowired
    private ShipMethodRepository shipMethodRepository;

    @Autowired
    private ShipMethodMapper shipMethodMapper;

    @Autowired
    private ShipMethodService shipMethodService;

    @Autowired
    private ShipMethodQueryService shipMethodQueryService;

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

    private MockMvc restShipMethodMockMvc;

    private ShipMethod shipMethod;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShipMethodResource shipMethodResource = new ShipMethodResource(shipMethodService, shipMethodQueryService);
        this.restShipMethodMockMvc = MockMvcBuilders.standaloneSetup(shipMethodResource)
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
    public static ShipMethod createEntity(EntityManager em) {
        ShipMethod shipMethod = new ShipMethod()
            .shipMethodName(DEFAULT_SHIP_METHOD_NAME);
        return shipMethod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipMethod createUpdatedEntity(EntityManager em) {
        ShipMethod shipMethod = new ShipMethod()
            .shipMethodName(UPDATED_SHIP_METHOD_NAME);
        return shipMethod;
    }

    @BeforeEach
    public void initTest() {
        shipMethod = createEntity(em);
    }

    @Test
    @Transactional
    public void createShipMethod() throws Exception {
        int databaseSizeBeforeCreate = shipMethodRepository.findAll().size();

        // Create the ShipMethod
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(shipMethod);
        restShipMethodMockMvc.perform(post("/api/ship-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isCreated());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeCreate + 1);
        ShipMethod testShipMethod = shipMethodList.get(shipMethodList.size() - 1);
        assertThat(testShipMethod.getShipMethodName()).isEqualTo(DEFAULT_SHIP_METHOD_NAME);
    }

    @Test
    @Transactional
    public void createShipMethodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shipMethodRepository.findAll().size();

        // Create the ShipMethod with an existing ID
        shipMethod.setId(1L);
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(shipMethod);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipMethodMockMvc.perform(post("/api/ship-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShipMethods() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList
        restShipMethodMockMvc.perform(get("/api/ship-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipMethodName").value(hasItem(DEFAULT_SHIP_METHOD_NAME)));
    }
    
    @Test
    @Transactional
    public void getShipMethod() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get the shipMethod
        restShipMethodMockMvc.perform(get("/api/ship-methods/{id}", shipMethod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shipMethod.getId().intValue()))
            .andExpect(jsonPath("$.shipMethodName").value(DEFAULT_SHIP_METHOD_NAME));
    }

    @Test
    @Transactional
    public void getAllShipMethodsByShipMethodNameIsEqualToSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where shipMethodName equals to DEFAULT_SHIP_METHOD_NAME
        defaultShipMethodShouldBeFound("shipMethodName.equals=" + DEFAULT_SHIP_METHOD_NAME);

        // Get all the shipMethodList where shipMethodName equals to UPDATED_SHIP_METHOD_NAME
        defaultShipMethodShouldNotBeFound("shipMethodName.equals=" + UPDATED_SHIP_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByShipMethodNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where shipMethodName not equals to DEFAULT_SHIP_METHOD_NAME
        defaultShipMethodShouldNotBeFound("shipMethodName.notEquals=" + DEFAULT_SHIP_METHOD_NAME);

        // Get all the shipMethodList where shipMethodName not equals to UPDATED_SHIP_METHOD_NAME
        defaultShipMethodShouldBeFound("shipMethodName.notEquals=" + UPDATED_SHIP_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByShipMethodNameIsInShouldWork() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where shipMethodName in DEFAULT_SHIP_METHOD_NAME or UPDATED_SHIP_METHOD_NAME
        defaultShipMethodShouldBeFound("shipMethodName.in=" + DEFAULT_SHIP_METHOD_NAME + "," + UPDATED_SHIP_METHOD_NAME);

        // Get all the shipMethodList where shipMethodName equals to UPDATED_SHIP_METHOD_NAME
        defaultShipMethodShouldNotBeFound("shipMethodName.in=" + UPDATED_SHIP_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByShipMethodNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where shipMethodName is not null
        defaultShipMethodShouldBeFound("shipMethodName.specified=true");

        // Get all the shipMethodList where shipMethodName is null
        defaultShipMethodShouldNotBeFound("shipMethodName.specified=false");
    }
                @Test
    @Transactional
    public void getAllShipMethodsByShipMethodNameContainsSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where shipMethodName contains DEFAULT_SHIP_METHOD_NAME
        defaultShipMethodShouldBeFound("shipMethodName.contains=" + DEFAULT_SHIP_METHOD_NAME);

        // Get all the shipMethodList where shipMethodName contains UPDATED_SHIP_METHOD_NAME
        defaultShipMethodShouldNotBeFound("shipMethodName.contains=" + UPDATED_SHIP_METHOD_NAME);
    }

    @Test
    @Transactional
    public void getAllShipMethodsByShipMethodNameNotContainsSomething() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        // Get all the shipMethodList where shipMethodName does not contain DEFAULT_SHIP_METHOD_NAME
        defaultShipMethodShouldNotBeFound("shipMethodName.doesNotContain=" + DEFAULT_SHIP_METHOD_NAME);

        // Get all the shipMethodList where shipMethodName does not contain UPDATED_SHIP_METHOD_NAME
        defaultShipMethodShouldBeFound("shipMethodName.doesNotContain=" + UPDATED_SHIP_METHOD_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultShipMethodShouldBeFound(String filter) throws Exception {
        restShipMethodMockMvc.perform(get("/api/ship-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipMethod.getId().intValue())))
            .andExpect(jsonPath("$.[*].shipMethodName").value(hasItem(DEFAULT_SHIP_METHOD_NAME)));

        // Check, that the count call also returns 1
        restShipMethodMockMvc.perform(get("/api/ship-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultShipMethodShouldNotBeFound(String filter) throws Exception {
        restShipMethodMockMvc.perform(get("/api/ship-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restShipMethodMockMvc.perform(get("/api/ship-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingShipMethod() throws Exception {
        // Get the shipMethod
        restShipMethodMockMvc.perform(get("/api/ship-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShipMethod() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        int databaseSizeBeforeUpdate = shipMethodRepository.findAll().size();

        // Update the shipMethod
        ShipMethod updatedShipMethod = shipMethodRepository.findById(shipMethod.getId()).get();
        // Disconnect from session so that the updates on updatedShipMethod are not directly saved in db
        em.detach(updatedShipMethod);
        updatedShipMethod
            .shipMethodName(UPDATED_SHIP_METHOD_NAME);
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(updatedShipMethod);

        restShipMethodMockMvc.perform(put("/api/ship-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isOk());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeUpdate);
        ShipMethod testShipMethod = shipMethodList.get(shipMethodList.size() - 1);
        assertThat(testShipMethod.getShipMethodName()).isEqualTo(UPDATED_SHIP_METHOD_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingShipMethod() throws Exception {
        int databaseSizeBeforeUpdate = shipMethodRepository.findAll().size();

        // Create the ShipMethod
        ShipMethodDTO shipMethodDTO = shipMethodMapper.toDto(shipMethod);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipMethodMockMvc.perform(put("/api/ship-methods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shipMethodDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShipMethod in the database
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShipMethod() throws Exception {
        // Initialize the database
        shipMethodRepository.saveAndFlush(shipMethod);

        int databaseSizeBeforeDelete = shipMethodRepository.findAll().size();

        // Delete the shipMethod
        restShipMethodMockMvc.perform(delete("/api/ship-methods/{id}", shipMethod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipMethod> shipMethodList = shipMethodRepository.findAll();
        assertThat(shipMethodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipMethod.class);
        ShipMethod shipMethod1 = new ShipMethod();
        shipMethod1.setId(1L);
        ShipMethod shipMethod2 = new ShipMethod();
        shipMethod2.setId(shipMethod1.getId());
        assertThat(shipMethod1).isEqualTo(shipMethod2);
        shipMethod2.setId(2L);
        assertThat(shipMethod1).isNotEqualTo(shipMethod2);
        shipMethod1.setId(null);
        assertThat(shipMethod1).isNotEqualTo(shipMethod2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipMethodDTO.class);
        ShipMethodDTO shipMethodDTO1 = new ShipMethodDTO();
        shipMethodDTO1.setId(1L);
        ShipMethodDTO shipMethodDTO2 = new ShipMethodDTO();
        assertThat(shipMethodDTO1).isNotEqualTo(shipMethodDTO2);
        shipMethodDTO2.setId(shipMethodDTO1.getId());
        assertThat(shipMethodDTO1).isEqualTo(shipMethodDTO2);
        shipMethodDTO2.setId(2L);
        assertThat(shipMethodDTO1).isNotEqualTo(shipMethodDTO2);
        shipMethodDTO1.setId(null);
        assertThat(shipMethodDTO1).isNotEqualTo(shipMethodDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shipMethodMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shipMethodMapper.fromId(null)).isNull();
    }
}
