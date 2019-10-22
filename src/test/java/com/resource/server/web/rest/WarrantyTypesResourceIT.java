package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.WarrantyTypes;
import com.resource.server.repository.WarrantyTypesRepository;
import com.resource.server.service.WarrantyTypesService;
import com.resource.server.service.dto.WarrantyTypesDTO;
import com.resource.server.service.mapper.WarrantyTypesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.WarrantyTypesCriteria;
import com.resource.server.service.WarrantyTypesQueryService;

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
 * Integration tests for the {@link WarrantyTypesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class WarrantyTypesResourceIT {

    private static final String DEFAULT_WARRANTY_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WARRANTY_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private WarrantyTypesRepository warrantyTypesRepository;

    @Autowired
    private WarrantyTypesMapper warrantyTypesMapper;

    @Autowired
    private WarrantyTypesService warrantyTypesService;

    @Autowired
    private WarrantyTypesQueryService warrantyTypesQueryService;

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

    private MockMvc restWarrantyTypesMockMvc;

    private WarrantyTypes warrantyTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WarrantyTypesResource warrantyTypesResource = new WarrantyTypesResource(warrantyTypesService, warrantyTypesQueryService);
        this.restWarrantyTypesMockMvc = MockMvcBuilders.standaloneSetup(warrantyTypesResource)
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
    public static WarrantyTypes createEntity(EntityManager em) {
        WarrantyTypes warrantyTypes = new WarrantyTypes()
            .warrantyTypeName(DEFAULT_WARRANTY_TYPE_NAME);
        return warrantyTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WarrantyTypes createUpdatedEntity(EntityManager em) {
        WarrantyTypes warrantyTypes = new WarrantyTypes()
            .warrantyTypeName(UPDATED_WARRANTY_TYPE_NAME);
        return warrantyTypes;
    }

    @BeforeEach
    public void initTest() {
        warrantyTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createWarrantyTypes() throws Exception {
        int databaseSizeBeforeCreate = warrantyTypesRepository.findAll().size();

        // Create the WarrantyTypes
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);
        restWarrantyTypesMockMvc.perform(post("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeCreate + 1);
        WarrantyTypes testWarrantyTypes = warrantyTypesList.get(warrantyTypesList.size() - 1);
        assertThat(testWarrantyTypes.getWarrantyTypeName()).isEqualTo(DEFAULT_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createWarrantyTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = warrantyTypesRepository.findAll().size();

        // Create the WarrantyTypes with an existing ID
        warrantyTypes.setId(1L);
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarrantyTypesMockMvc.perform(post("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkWarrantyTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = warrantyTypesRepository.findAll().size();
        // set the field null
        warrantyTypes.setWarrantyTypeName(null);

        // Create the WarrantyTypes, which fails.
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);

        restWarrantyTypesMockMvc.perform(post("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isBadRequest());

        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].warrantyTypeName").value(hasItem(DEFAULT_WARRANTY_TYPE_NAME)));
    }
    
    @Test
    @Transactional
    public void getWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get the warrantyTypes
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/{id}", warrantyTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(warrantyTypes.getId().intValue()))
            .andExpect(jsonPath("$.warrantyTypeName").value(DEFAULT_WARRANTY_TYPE_NAME));
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByWarrantyTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where warrantyTypeName equals to DEFAULT_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldBeFound("warrantyTypeName.equals=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the warrantyTypesList where warrantyTypeName equals to UPDATED_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldNotBeFound("warrantyTypeName.equals=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByWarrantyTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where warrantyTypeName not equals to DEFAULT_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldNotBeFound("warrantyTypeName.notEquals=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the warrantyTypesList where warrantyTypeName not equals to UPDATED_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldBeFound("warrantyTypeName.notEquals=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByWarrantyTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where warrantyTypeName in DEFAULT_WARRANTY_TYPE_NAME or UPDATED_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldBeFound("warrantyTypeName.in=" + DEFAULT_WARRANTY_TYPE_NAME + "," + UPDATED_WARRANTY_TYPE_NAME);

        // Get all the warrantyTypesList where warrantyTypeName equals to UPDATED_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldNotBeFound("warrantyTypeName.in=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByWarrantyTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where warrantyTypeName is not null
        defaultWarrantyTypesShouldBeFound("warrantyTypeName.specified=true");

        // Get all the warrantyTypesList where warrantyTypeName is null
        defaultWarrantyTypesShouldNotBeFound("warrantyTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllWarrantyTypesByWarrantyTypeNameContainsSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where warrantyTypeName contains DEFAULT_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldBeFound("warrantyTypeName.contains=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the warrantyTypesList where warrantyTypeName contains UPDATED_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldNotBeFound("warrantyTypeName.contains=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllWarrantyTypesByWarrantyTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        // Get all the warrantyTypesList where warrantyTypeName does not contain DEFAULT_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldNotBeFound("warrantyTypeName.doesNotContain=" + DEFAULT_WARRANTY_TYPE_NAME);

        // Get all the warrantyTypesList where warrantyTypeName does not contain UPDATED_WARRANTY_TYPE_NAME
        defaultWarrantyTypesShouldBeFound("warrantyTypeName.doesNotContain=" + UPDATED_WARRANTY_TYPE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWarrantyTypesShouldBeFound(String filter) throws Exception {
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warrantyTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].warrantyTypeName").value(hasItem(DEFAULT_WARRANTY_TYPE_NAME)));

        // Check, that the count call also returns 1
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWarrantyTypesShouldNotBeFound(String filter) throws Exception {
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingWarrantyTypes() throws Exception {
        // Get the warrantyTypes
        restWarrantyTypesMockMvc.perform(get("/api/warranty-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        int databaseSizeBeforeUpdate = warrantyTypesRepository.findAll().size();

        // Update the warrantyTypes
        WarrantyTypes updatedWarrantyTypes = warrantyTypesRepository.findById(warrantyTypes.getId()).get();
        // Disconnect from session so that the updates on updatedWarrantyTypes are not directly saved in db
        em.detach(updatedWarrantyTypes);
        updatedWarrantyTypes
            .warrantyTypeName(UPDATED_WARRANTY_TYPE_NAME);
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(updatedWarrantyTypes);

        restWarrantyTypesMockMvc.perform(put("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isOk());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeUpdate);
        WarrantyTypes testWarrantyTypes = warrantyTypesList.get(warrantyTypesList.size() - 1);
        assertThat(testWarrantyTypes.getWarrantyTypeName()).isEqualTo(UPDATED_WARRANTY_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingWarrantyTypes() throws Exception {
        int databaseSizeBeforeUpdate = warrantyTypesRepository.findAll().size();

        // Create the WarrantyTypes
        WarrantyTypesDTO warrantyTypesDTO = warrantyTypesMapper.toDto(warrantyTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarrantyTypesMockMvc.perform(put("/api/warranty-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(warrantyTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WarrantyTypes in the database
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWarrantyTypes() throws Exception {
        // Initialize the database
        warrantyTypesRepository.saveAndFlush(warrantyTypes);

        int databaseSizeBeforeDelete = warrantyTypesRepository.findAll().size();

        // Delete the warrantyTypes
        restWarrantyTypesMockMvc.perform(delete("/api/warranty-types/{id}", warrantyTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WarrantyTypes> warrantyTypesList = warrantyTypesRepository.findAll();
        assertThat(warrantyTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyTypes.class);
        WarrantyTypes warrantyTypes1 = new WarrantyTypes();
        warrantyTypes1.setId(1L);
        WarrantyTypes warrantyTypes2 = new WarrantyTypes();
        warrantyTypes2.setId(warrantyTypes1.getId());
        assertThat(warrantyTypes1).isEqualTo(warrantyTypes2);
        warrantyTypes2.setId(2L);
        assertThat(warrantyTypes1).isNotEqualTo(warrantyTypes2);
        warrantyTypes1.setId(null);
        assertThat(warrantyTypes1).isNotEqualTo(warrantyTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WarrantyTypesDTO.class);
        WarrantyTypesDTO warrantyTypesDTO1 = new WarrantyTypesDTO();
        warrantyTypesDTO1.setId(1L);
        WarrantyTypesDTO warrantyTypesDTO2 = new WarrantyTypesDTO();
        assertThat(warrantyTypesDTO1).isNotEqualTo(warrantyTypesDTO2);
        warrantyTypesDTO2.setId(warrantyTypesDTO1.getId());
        assertThat(warrantyTypesDTO1).isEqualTo(warrantyTypesDTO2);
        warrantyTypesDTO2.setId(2L);
        assertThat(warrantyTypesDTO1).isNotEqualTo(warrantyTypesDTO2);
        warrantyTypesDTO1.setId(null);
        assertThat(warrantyTypesDTO1).isNotEqualTo(warrantyTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(warrantyTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(warrantyTypesMapper.fromId(null)).isNull();
    }
}
