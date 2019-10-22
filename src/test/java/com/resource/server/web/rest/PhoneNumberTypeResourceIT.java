package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.PhoneNumberType;
import com.resource.server.repository.PhoneNumberTypeRepository;
import com.resource.server.service.PhoneNumberTypeService;
import com.resource.server.service.dto.PhoneNumberTypeDTO;
import com.resource.server.service.mapper.PhoneNumberTypeMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.PhoneNumberTypeCriteria;
import com.resource.server.service.PhoneNumberTypeQueryService;

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
 * Integration tests for the {@link PhoneNumberTypeResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class PhoneNumberTypeResourceIT {

    private static final String DEFAULT_PHONE_NUMBER_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private PhoneNumberTypeRepository phoneNumberTypeRepository;

    @Autowired
    private PhoneNumberTypeMapper phoneNumberTypeMapper;

    @Autowired
    private PhoneNumberTypeService phoneNumberTypeService;

    @Autowired
    private PhoneNumberTypeQueryService phoneNumberTypeQueryService;

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

    private MockMvc restPhoneNumberTypeMockMvc;

    private PhoneNumberType phoneNumberType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PhoneNumberTypeResource phoneNumberTypeResource = new PhoneNumberTypeResource(phoneNumberTypeService, phoneNumberTypeQueryService);
        this.restPhoneNumberTypeMockMvc = MockMvcBuilders.standaloneSetup(phoneNumberTypeResource)
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
    public static PhoneNumberType createEntity(EntityManager em) {
        PhoneNumberType phoneNumberType = new PhoneNumberType()
            .phoneNumberTypeName(DEFAULT_PHONE_NUMBER_TYPE_NAME);
        return phoneNumberType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PhoneNumberType createUpdatedEntity(EntityManager em) {
        PhoneNumberType phoneNumberType = new PhoneNumberType()
            .phoneNumberTypeName(UPDATED_PHONE_NUMBER_TYPE_NAME);
        return phoneNumberType;
    }

    @BeforeEach
    public void initTest() {
        phoneNumberType = createEntity(em);
    }

    @Test
    @Transactional
    public void createPhoneNumberType() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);
        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeCreate + 1);
        PhoneNumberType testPhoneNumberType = phoneNumberTypeList.get(phoneNumberTypeList.size() - 1);
        assertThat(testPhoneNumberType.getPhoneNumberTypeName()).isEqualTo(DEFAULT_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createPhoneNumberTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType with an existing ID
        phoneNumberType.setId(1L);
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPhoneNumberTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = phoneNumberTypeRepository.findAll().size();
        // set the field null
        phoneNumberType.setPhoneNumberTypeName(null);

        // Create the PhoneNumberType, which fails.
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        restPhoneNumberTypeMockMvc.perform(post("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypes() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumberType.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberTypeName").value(hasItem(DEFAULT_PHONE_NUMBER_TYPE_NAME)));
    }
    
    @Test
    @Transactional
    public void getPhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/{id}", phoneNumberType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(phoneNumberType.getId().intValue()))
            .andExpect(jsonPath("$.phoneNumberTypeName").value(DEFAULT_PHONE_NUMBER_TYPE_NAME));
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByPhoneNumberTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where phoneNumberTypeName equals to DEFAULT_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldBeFound("phoneNumberTypeName.equals=" + DEFAULT_PHONE_NUMBER_TYPE_NAME);

        // Get all the phoneNumberTypeList where phoneNumberTypeName equals to UPDATED_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldNotBeFound("phoneNumberTypeName.equals=" + UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByPhoneNumberTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where phoneNumberTypeName not equals to DEFAULT_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldNotBeFound("phoneNumberTypeName.notEquals=" + DEFAULT_PHONE_NUMBER_TYPE_NAME);

        // Get all the phoneNumberTypeList where phoneNumberTypeName not equals to UPDATED_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldBeFound("phoneNumberTypeName.notEquals=" + UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByPhoneNumberTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where phoneNumberTypeName in DEFAULT_PHONE_NUMBER_TYPE_NAME or UPDATED_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldBeFound("phoneNumberTypeName.in=" + DEFAULT_PHONE_NUMBER_TYPE_NAME + "," + UPDATED_PHONE_NUMBER_TYPE_NAME);

        // Get all the phoneNumberTypeList where phoneNumberTypeName equals to UPDATED_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldNotBeFound("phoneNumberTypeName.in=" + UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByPhoneNumberTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where phoneNumberTypeName is not null
        defaultPhoneNumberTypeShouldBeFound("phoneNumberTypeName.specified=true");

        // Get all the phoneNumberTypeList where phoneNumberTypeName is null
        defaultPhoneNumberTypeShouldNotBeFound("phoneNumberTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPhoneNumberTypesByPhoneNumberTypeNameContainsSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where phoneNumberTypeName contains DEFAULT_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldBeFound("phoneNumberTypeName.contains=" + DEFAULT_PHONE_NUMBER_TYPE_NAME);

        // Get all the phoneNumberTypeList where phoneNumberTypeName contains UPDATED_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldNotBeFound("phoneNumberTypeName.contains=" + UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPhoneNumberTypesByPhoneNumberTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        // Get all the phoneNumberTypeList where phoneNumberTypeName does not contain DEFAULT_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldNotBeFound("phoneNumberTypeName.doesNotContain=" + DEFAULT_PHONE_NUMBER_TYPE_NAME);

        // Get all the phoneNumberTypeList where phoneNumberTypeName does not contain UPDATED_PHONE_NUMBER_TYPE_NAME
        defaultPhoneNumberTypeShouldBeFound("phoneNumberTypeName.doesNotContain=" + UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPhoneNumberTypeShouldBeFound(String filter) throws Exception {
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phoneNumberType.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberTypeName").value(hasItem(DEFAULT_PHONE_NUMBER_TYPE_NAME)));

        // Check, that the count call also returns 1
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPhoneNumberTypeShouldNotBeFound(String filter) throws Exception {
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPhoneNumberType() throws Exception {
        // Get the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(get("/api/phone-number-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        int databaseSizeBeforeUpdate = phoneNumberTypeRepository.findAll().size();

        // Update the phoneNumberType
        PhoneNumberType updatedPhoneNumberType = phoneNumberTypeRepository.findById(phoneNumberType.getId()).get();
        // Disconnect from session so that the updates on updatedPhoneNumberType are not directly saved in db
        em.detach(updatedPhoneNumberType);
        updatedPhoneNumberType
            .phoneNumberTypeName(UPDATED_PHONE_NUMBER_TYPE_NAME);
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(updatedPhoneNumberType);

        restPhoneNumberTypeMockMvc.perform(put("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isOk());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeUpdate);
        PhoneNumberType testPhoneNumberType = phoneNumberTypeList.get(phoneNumberTypeList.size() - 1);
        assertThat(testPhoneNumberType.getPhoneNumberTypeName()).isEqualTo(UPDATED_PHONE_NUMBER_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPhoneNumberType() throws Exception {
        int databaseSizeBeforeUpdate = phoneNumberTypeRepository.findAll().size();

        // Create the PhoneNumberType
        PhoneNumberTypeDTO phoneNumberTypeDTO = phoneNumberTypeMapper.toDto(phoneNumberType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhoneNumberTypeMockMvc.perform(put("/api/phone-number-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(phoneNumberTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PhoneNumberType in the database
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePhoneNumberType() throws Exception {
        // Initialize the database
        phoneNumberTypeRepository.saveAndFlush(phoneNumberType);

        int databaseSizeBeforeDelete = phoneNumberTypeRepository.findAll().size();

        // Delete the phoneNumberType
        restPhoneNumberTypeMockMvc.perform(delete("/api/phone-number-types/{id}", phoneNumberType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PhoneNumberType> phoneNumberTypeList = phoneNumberTypeRepository.findAll();
        assertThat(phoneNumberTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberType.class);
        PhoneNumberType phoneNumberType1 = new PhoneNumberType();
        phoneNumberType1.setId(1L);
        PhoneNumberType phoneNumberType2 = new PhoneNumberType();
        phoneNumberType2.setId(phoneNumberType1.getId());
        assertThat(phoneNumberType1).isEqualTo(phoneNumberType2);
        phoneNumberType2.setId(2L);
        assertThat(phoneNumberType1).isNotEqualTo(phoneNumberType2);
        phoneNumberType1.setId(null);
        assertThat(phoneNumberType1).isNotEqualTo(phoneNumberType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhoneNumberTypeDTO.class);
        PhoneNumberTypeDTO phoneNumberTypeDTO1 = new PhoneNumberTypeDTO();
        phoneNumberTypeDTO1.setId(1L);
        PhoneNumberTypeDTO phoneNumberTypeDTO2 = new PhoneNumberTypeDTO();
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO2.setId(phoneNumberTypeDTO1.getId());
        assertThat(phoneNumberTypeDTO1).isEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO2.setId(2L);
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
        phoneNumberTypeDTO1.setId(null);
        assertThat(phoneNumberTypeDTO1).isNotEqualTo(phoneNumberTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(phoneNumberTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(phoneNumberTypeMapper.fromId(null)).isNull();
    }
}
