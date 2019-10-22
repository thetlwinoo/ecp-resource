package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.UnitMeasure;
import com.resource.server.repository.UnitMeasureRepository;
import com.resource.server.service.UnitMeasureService;
import com.resource.server.service.dto.UnitMeasureDTO;
import com.resource.server.service.mapper.UnitMeasureMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.UnitMeasureCriteria;
import com.resource.server.service.UnitMeasureQueryService;

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
 * Integration tests for the {@link UnitMeasureResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class UnitMeasureResourceIT {

    private static final String DEFAULT_UNIT_MEASURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_MEASURE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT_MEASURE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_MEASURE_NAME = "BBBBBBBBBB";

    @Autowired
    private UnitMeasureRepository unitMeasureRepository;

    @Autowired
    private UnitMeasureMapper unitMeasureMapper;

    @Autowired
    private UnitMeasureService unitMeasureService;

    @Autowired
    private UnitMeasureQueryService unitMeasureQueryService;

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

    private MockMvc restUnitMeasureMockMvc;

    private UnitMeasure unitMeasure;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitMeasureResource unitMeasureResource = new UnitMeasureResource(unitMeasureService, unitMeasureQueryService);
        this.restUnitMeasureMockMvc = MockMvcBuilders.standaloneSetup(unitMeasureResource)
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
    public static UnitMeasure createEntity(EntityManager em) {
        UnitMeasure unitMeasure = new UnitMeasure()
            .unitMeasureCode(DEFAULT_UNIT_MEASURE_CODE)
            .unitMeasureName(DEFAULT_UNIT_MEASURE_NAME);
        return unitMeasure;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnitMeasure createUpdatedEntity(EntityManager em) {
        UnitMeasure unitMeasure = new UnitMeasure()
            .unitMeasureCode(UPDATED_UNIT_MEASURE_CODE)
            .unitMeasureName(UPDATED_UNIT_MEASURE_NAME);
        return unitMeasure;
    }

    @BeforeEach
    public void initTest() {
        unitMeasure = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitMeasure() throws Exception {
        int databaseSizeBeforeCreate = unitMeasureRepository.findAll().size();

        // Create the UnitMeasure
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);
        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isCreated());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        UnitMeasure testUnitMeasure = unitMeasureList.get(unitMeasureList.size() - 1);
        assertThat(testUnitMeasure.getUnitMeasureCode()).isEqualTo(DEFAULT_UNIT_MEASURE_CODE);
        assertThat(testUnitMeasure.getUnitMeasureName()).isEqualTo(DEFAULT_UNIT_MEASURE_NAME);
    }

    @Test
    @Transactional
    public void createUnitMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitMeasureRepository.findAll().size();

        // Create the UnitMeasure with an existing ID
        unitMeasure.setId(1L);
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUnitMeasureCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitMeasureRepository.findAll().size();
        // set the field null
        unitMeasure.setUnitMeasureCode(null);

        // Create the UnitMeasure, which fails.
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUnitMeasureNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitMeasureRepository.findAll().size();
        // set the field null
        unitMeasure.setUnitMeasureName(null);

        // Create the UnitMeasure, which fails.
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        restUnitMeasureMockMvc.perform(post("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnitMeasures() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList
        restUnitMeasureMockMvc.perform(get("/api/unit-measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitMeasureCode").value(hasItem(DEFAULT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].unitMeasureName").value(hasItem(DEFAULT_UNIT_MEASURE_NAME)));
    }
    
    @Test
    @Transactional
    public void getUnitMeasure() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get the unitMeasure
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/{id}", unitMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unitMeasure.getId().intValue()))
            .andExpect(jsonPath("$.unitMeasureCode").value(DEFAULT_UNIT_MEASURE_CODE))
            .andExpect(jsonPath("$.unitMeasureName").value(DEFAULT_UNIT_MEASURE_NAME));
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureCode equals to DEFAULT_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldBeFound("unitMeasureCode.equals=" + DEFAULT_UNIT_MEASURE_CODE);

        // Get all the unitMeasureList where unitMeasureCode equals to UPDATED_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldNotBeFound("unitMeasureCode.equals=" + UPDATED_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureCode not equals to DEFAULT_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldNotBeFound("unitMeasureCode.notEquals=" + DEFAULT_UNIT_MEASURE_CODE);

        // Get all the unitMeasureList where unitMeasureCode not equals to UPDATED_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldBeFound("unitMeasureCode.notEquals=" + UPDATED_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureCode in DEFAULT_UNIT_MEASURE_CODE or UPDATED_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldBeFound("unitMeasureCode.in=" + DEFAULT_UNIT_MEASURE_CODE + "," + UPDATED_UNIT_MEASURE_CODE);

        // Get all the unitMeasureList where unitMeasureCode equals to UPDATED_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldNotBeFound("unitMeasureCode.in=" + UPDATED_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureCode is not null
        defaultUnitMeasureShouldBeFound("unitMeasureCode.specified=true");

        // Get all the unitMeasureList where unitMeasureCode is null
        defaultUnitMeasureShouldNotBeFound("unitMeasureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureCodeContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureCode contains DEFAULT_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldBeFound("unitMeasureCode.contains=" + DEFAULT_UNIT_MEASURE_CODE);

        // Get all the unitMeasureList where unitMeasureCode contains UPDATED_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldNotBeFound("unitMeasureCode.contains=" + UPDATED_UNIT_MEASURE_CODE);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureCode does not contain DEFAULT_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldNotBeFound("unitMeasureCode.doesNotContain=" + DEFAULT_UNIT_MEASURE_CODE);

        // Get all the unitMeasureList where unitMeasureCode does not contain UPDATED_UNIT_MEASURE_CODE
        defaultUnitMeasureShouldBeFound("unitMeasureCode.doesNotContain=" + UPDATED_UNIT_MEASURE_CODE);
    }


    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureNameIsEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureName equals to DEFAULT_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldBeFound("unitMeasureName.equals=" + DEFAULT_UNIT_MEASURE_NAME);

        // Get all the unitMeasureList where unitMeasureName equals to UPDATED_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldNotBeFound("unitMeasureName.equals=" + UPDATED_UNIT_MEASURE_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureName not equals to DEFAULT_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldNotBeFound("unitMeasureName.notEquals=" + DEFAULT_UNIT_MEASURE_NAME);

        // Get all the unitMeasureList where unitMeasureName not equals to UPDATED_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldBeFound("unitMeasureName.notEquals=" + UPDATED_UNIT_MEASURE_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureNameIsInShouldWork() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureName in DEFAULT_UNIT_MEASURE_NAME or UPDATED_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldBeFound("unitMeasureName.in=" + DEFAULT_UNIT_MEASURE_NAME + "," + UPDATED_UNIT_MEASURE_NAME);

        // Get all the unitMeasureList where unitMeasureName equals to UPDATED_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldNotBeFound("unitMeasureName.in=" + UPDATED_UNIT_MEASURE_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureName is not null
        defaultUnitMeasureShouldBeFound("unitMeasureName.specified=true");

        // Get all the unitMeasureList where unitMeasureName is null
        defaultUnitMeasureShouldNotBeFound("unitMeasureName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureNameContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureName contains DEFAULT_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldBeFound("unitMeasureName.contains=" + DEFAULT_UNIT_MEASURE_NAME);

        // Get all the unitMeasureList where unitMeasureName contains UPDATED_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldNotBeFound("unitMeasureName.contains=" + UPDATED_UNIT_MEASURE_NAME);
    }

    @Test
    @Transactional
    public void getAllUnitMeasuresByUnitMeasureNameNotContainsSomething() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        // Get all the unitMeasureList where unitMeasureName does not contain DEFAULT_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldNotBeFound("unitMeasureName.doesNotContain=" + DEFAULT_UNIT_MEASURE_NAME);

        // Get all the unitMeasureList where unitMeasureName does not contain UPDATED_UNIT_MEASURE_NAME
        defaultUnitMeasureShouldBeFound("unitMeasureName.doesNotContain=" + UPDATED_UNIT_MEASURE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUnitMeasureShouldBeFound(String filter) throws Exception {
        restUnitMeasureMockMvc.perform(get("/api/unit-measures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].unitMeasureCode").value(hasItem(DEFAULT_UNIT_MEASURE_CODE)))
            .andExpect(jsonPath("$.[*].unitMeasureName").value(hasItem(DEFAULT_UNIT_MEASURE_NAME)));

        // Check, that the count call also returns 1
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUnitMeasureShouldNotBeFound(String filter) throws Exception {
        restUnitMeasureMockMvc.perform(get("/api/unit-measures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUnitMeasure() throws Exception {
        // Get the unitMeasure
        restUnitMeasureMockMvc.perform(get("/api/unit-measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitMeasure() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        int databaseSizeBeforeUpdate = unitMeasureRepository.findAll().size();

        // Update the unitMeasure
        UnitMeasure updatedUnitMeasure = unitMeasureRepository.findById(unitMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedUnitMeasure are not directly saved in db
        em.detach(updatedUnitMeasure);
        updatedUnitMeasure
            .unitMeasureCode(UPDATED_UNIT_MEASURE_CODE)
            .unitMeasureName(UPDATED_UNIT_MEASURE_NAME);
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(updatedUnitMeasure);

        restUnitMeasureMockMvc.perform(put("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isOk());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeUpdate);
        UnitMeasure testUnitMeasure = unitMeasureList.get(unitMeasureList.size() - 1);
        assertThat(testUnitMeasure.getUnitMeasureCode()).isEqualTo(UPDATED_UNIT_MEASURE_CODE);
        assertThat(testUnitMeasure.getUnitMeasureName()).isEqualTo(UPDATED_UNIT_MEASURE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitMeasure() throws Exception {
        int databaseSizeBeforeUpdate = unitMeasureRepository.findAll().size();

        // Create the UnitMeasure
        UnitMeasureDTO unitMeasureDTO = unitMeasureMapper.toDto(unitMeasure);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMeasureMockMvc.perform(put("/api/unit-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitMeasureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UnitMeasure in the database
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitMeasure() throws Exception {
        // Initialize the database
        unitMeasureRepository.saveAndFlush(unitMeasure);

        int databaseSizeBeforeDelete = unitMeasureRepository.findAll().size();

        // Delete the unitMeasure
        restUnitMeasureMockMvc.perform(delete("/api/unit-measures/{id}", unitMeasure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnitMeasure> unitMeasureList = unitMeasureRepository.findAll();
        assertThat(unitMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitMeasure.class);
        UnitMeasure unitMeasure1 = new UnitMeasure();
        unitMeasure1.setId(1L);
        UnitMeasure unitMeasure2 = new UnitMeasure();
        unitMeasure2.setId(unitMeasure1.getId());
        assertThat(unitMeasure1).isEqualTo(unitMeasure2);
        unitMeasure2.setId(2L);
        assertThat(unitMeasure1).isNotEqualTo(unitMeasure2);
        unitMeasure1.setId(null);
        assertThat(unitMeasure1).isNotEqualTo(unitMeasure2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitMeasureDTO.class);
        UnitMeasureDTO unitMeasureDTO1 = new UnitMeasureDTO();
        unitMeasureDTO1.setId(1L);
        UnitMeasureDTO unitMeasureDTO2 = new UnitMeasureDTO();
        assertThat(unitMeasureDTO1).isNotEqualTo(unitMeasureDTO2);
        unitMeasureDTO2.setId(unitMeasureDTO1.getId());
        assertThat(unitMeasureDTO1).isEqualTo(unitMeasureDTO2);
        unitMeasureDTO2.setId(2L);
        assertThat(unitMeasureDTO1).isNotEqualTo(unitMeasureDTO2);
        unitMeasureDTO1.setId(null);
        assertThat(unitMeasureDTO1).isNotEqualTo(unitMeasureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(unitMeasureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(unitMeasureMapper.fromId(null)).isNull();
    }
}
