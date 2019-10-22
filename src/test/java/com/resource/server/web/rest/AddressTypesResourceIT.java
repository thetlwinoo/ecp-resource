package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.AddressTypes;
import com.resource.server.repository.AddressTypesRepository;
import com.resource.server.service.AddressTypesService;
import com.resource.server.service.dto.AddressTypesDTO;
import com.resource.server.service.mapper.AddressTypesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.AddressTypesCriteria;
import com.resource.server.service.AddressTypesQueryService;

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
 * Integration tests for the {@link AddressTypesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class AddressTypesResourceIT {

    private static final String DEFAULT_ADDRESS_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_TYPE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REFER = "AAAAAAAAAA";
    private static final String UPDATED_REFER = "BBBBBBBBBB";

    @Autowired
    private AddressTypesRepository addressTypesRepository;

    @Autowired
    private AddressTypesMapper addressTypesMapper;

    @Autowired
    private AddressTypesService addressTypesService;

    @Autowired
    private AddressTypesQueryService addressTypesQueryService;

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

    private MockMvc restAddressTypesMockMvc;

    private AddressTypes addressTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AddressTypesResource addressTypesResource = new AddressTypesResource(addressTypesService, addressTypesQueryService);
        this.restAddressTypesMockMvc = MockMvcBuilders.standaloneSetup(addressTypesResource)
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
    public static AddressTypes createEntity(EntityManager em) {
        AddressTypes addressTypes = new AddressTypes()
            .addressTypeName(DEFAULT_ADDRESS_TYPE_NAME)
            .refer(DEFAULT_REFER);
        return addressTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AddressTypes createUpdatedEntity(EntityManager em) {
        AddressTypes addressTypes = new AddressTypes()
            .addressTypeName(UPDATED_ADDRESS_TYPE_NAME)
            .refer(UPDATED_REFER);
        return addressTypes;
    }

    @BeforeEach
    public void initTest() {
        addressTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddressTypes() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate + 1);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getAddressTypeName()).isEqualTo(DEFAULT_ADDRESS_TYPE_NAME);
        assertThat(testAddressTypes.getRefer()).isEqualTo(DEFAULT_REFER);
    }

    @Test
    @Transactional
    public void createAddressTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressTypesRepository.findAll().size();

        // Create the AddressTypes with an existing ID
        addressTypes.setId(1L);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAddressTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressTypesRepository.findAll().size();
        // set the field null
        addressTypes.setAddressTypeName(null);

        // Create the AddressTypes, which fails.
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        restAddressTypesMockMvc.perform(post("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressTypeName").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].refer").value(hasItem(DEFAULT_REFER)));
    }
    
    @Test
    @Transactional
    public void getAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", addressTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(addressTypes.getId().intValue()))
            .andExpect(jsonPath("$.addressTypeName").value(DEFAULT_ADDRESS_TYPE_NAME))
            .andExpect(jsonPath("$.refer").value(DEFAULT_REFER));
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where addressTypeName equals to DEFAULT_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldBeFound("addressTypeName.equals=" + DEFAULT_ADDRESS_TYPE_NAME);

        // Get all the addressTypesList where addressTypeName equals to UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldNotBeFound("addressTypeName.equals=" + UPDATED_ADDRESS_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where addressTypeName not equals to DEFAULT_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldNotBeFound("addressTypeName.notEquals=" + DEFAULT_ADDRESS_TYPE_NAME);

        // Get all the addressTypesList where addressTypeName not equals to UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldBeFound("addressTypeName.notEquals=" + UPDATED_ADDRESS_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where addressTypeName in DEFAULT_ADDRESS_TYPE_NAME or UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldBeFound("addressTypeName.in=" + DEFAULT_ADDRESS_TYPE_NAME + "," + UPDATED_ADDRESS_TYPE_NAME);

        // Get all the addressTypesList where addressTypeName equals to UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldNotBeFound("addressTypeName.in=" + UPDATED_ADDRESS_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where addressTypeName is not null
        defaultAddressTypesShouldBeFound("addressTypeName.specified=true");

        // Get all the addressTypesList where addressTypeName is null
        defaultAddressTypesShouldNotBeFound("addressTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where addressTypeName contains DEFAULT_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldBeFound("addressTypeName.contains=" + DEFAULT_ADDRESS_TYPE_NAME);

        // Get all the addressTypesList where addressTypeName contains UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldNotBeFound("addressTypeName.contains=" + UPDATED_ADDRESS_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByAddressTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where addressTypeName does not contain DEFAULT_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldNotBeFound("addressTypeName.doesNotContain=" + DEFAULT_ADDRESS_TYPE_NAME);

        // Get all the addressTypesList where addressTypeName does not contain UPDATED_ADDRESS_TYPE_NAME
        defaultAddressTypesShouldBeFound("addressTypeName.doesNotContain=" + UPDATED_ADDRESS_TYPE_NAME);
    }


    @Test
    @Transactional
    public void getAllAddressTypesByReferIsEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer equals to DEFAULT_REFER
        defaultAddressTypesShouldBeFound("refer.equals=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer equals to UPDATED_REFER
        defaultAddressTypesShouldNotBeFound("refer.equals=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer not equals to DEFAULT_REFER
        defaultAddressTypesShouldNotBeFound("refer.notEquals=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer not equals to UPDATED_REFER
        defaultAddressTypesShouldBeFound("refer.notEquals=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferIsInShouldWork() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer in DEFAULT_REFER or UPDATED_REFER
        defaultAddressTypesShouldBeFound("refer.in=" + DEFAULT_REFER + "," + UPDATED_REFER);

        // Get all the addressTypesList where refer equals to UPDATED_REFER
        defaultAddressTypesShouldNotBeFound("refer.in=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer is not null
        defaultAddressTypesShouldBeFound("refer.specified=true");

        // Get all the addressTypesList where refer is null
        defaultAddressTypesShouldNotBeFound("refer.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressTypesByReferContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer contains DEFAULT_REFER
        defaultAddressTypesShouldBeFound("refer.contains=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer contains UPDATED_REFER
        defaultAddressTypesShouldNotBeFound("refer.contains=" + UPDATED_REFER);
    }

    @Test
    @Transactional
    public void getAllAddressTypesByReferNotContainsSomething() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        // Get all the addressTypesList where refer does not contain DEFAULT_REFER
        defaultAddressTypesShouldNotBeFound("refer.doesNotContain=" + DEFAULT_REFER);

        // Get all the addressTypesList where refer does not contain UPDATED_REFER
        defaultAddressTypesShouldBeFound("refer.doesNotContain=" + UPDATED_REFER);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressTypesShouldBeFound(String filter) throws Exception {
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(addressTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressTypeName").value(hasItem(DEFAULT_ADDRESS_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].refer").value(hasItem(DEFAULT_REFER)));

        // Check, that the count call also returns 1
        restAddressTypesMockMvc.perform(get("/api/address-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressTypesShouldNotBeFound(String filter) throws Exception {
        restAddressTypesMockMvc.perform(get("/api/address-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressTypesMockMvc.perform(get("/api/address-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAddressTypes() throws Exception {
        // Get the addressTypes
        restAddressTypesMockMvc.perform(get("/api/address-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Update the addressTypes
        AddressTypes updatedAddressTypes = addressTypesRepository.findById(addressTypes.getId()).get();
        // Disconnect from session so that the updates on updatedAddressTypes are not directly saved in db
        em.detach(updatedAddressTypes);
        updatedAddressTypes
            .addressTypeName(UPDATED_ADDRESS_TYPE_NAME)
            .refer(UPDATED_REFER);
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(updatedAddressTypes);

        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isOk());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
        AddressTypes testAddressTypes = addressTypesList.get(addressTypesList.size() - 1);
        assertThat(testAddressTypes.getAddressTypeName()).isEqualTo(UPDATED_ADDRESS_TYPE_NAME);
        assertThat(testAddressTypes.getRefer()).isEqualTo(UPDATED_REFER);
    }

    @Test
    @Transactional
    public void updateNonExistingAddressTypes() throws Exception {
        int databaseSizeBeforeUpdate = addressTypesRepository.findAll().size();

        // Create the AddressTypes
        AddressTypesDTO addressTypesDTO = addressTypesMapper.toDto(addressTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressTypesMockMvc.perform(put("/api/address-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(addressTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AddressTypes in the database
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddressTypes() throws Exception {
        // Initialize the database
        addressTypesRepository.saveAndFlush(addressTypes);

        int databaseSizeBeforeDelete = addressTypesRepository.findAll().size();

        // Delete the addressTypes
        restAddressTypesMockMvc.perform(delete("/api/address-types/{id}", addressTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AddressTypes> addressTypesList = addressTypesRepository.findAll();
        assertThat(addressTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypes.class);
        AddressTypes addressTypes1 = new AddressTypes();
        addressTypes1.setId(1L);
        AddressTypes addressTypes2 = new AddressTypes();
        addressTypes2.setId(addressTypes1.getId());
        assertThat(addressTypes1).isEqualTo(addressTypes2);
        addressTypes2.setId(2L);
        assertThat(addressTypes1).isNotEqualTo(addressTypes2);
        addressTypes1.setId(null);
        assertThat(addressTypes1).isNotEqualTo(addressTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressTypesDTO.class);
        AddressTypesDTO addressTypesDTO1 = new AddressTypesDTO();
        addressTypesDTO1.setId(1L);
        AddressTypesDTO addressTypesDTO2 = new AddressTypesDTO();
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
        addressTypesDTO2.setId(addressTypesDTO1.getId());
        assertThat(addressTypesDTO1).isEqualTo(addressTypesDTO2);
        addressTypesDTO2.setId(2L);
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
        addressTypesDTO1.setId(null);
        assertThat(addressTypesDTO1).isNotEqualTo(addressTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(addressTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(addressTypesMapper.fromId(null)).isNull();
    }
}
