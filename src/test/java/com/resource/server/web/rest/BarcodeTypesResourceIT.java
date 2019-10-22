package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.BarcodeTypes;
import com.resource.server.repository.BarcodeTypesRepository;
import com.resource.server.service.BarcodeTypesService;
import com.resource.server.service.dto.BarcodeTypesDTO;
import com.resource.server.service.mapper.BarcodeTypesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.BarcodeTypesCriteria;
import com.resource.server.service.BarcodeTypesQueryService;

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
 * Integration tests for the {@link BarcodeTypesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class BarcodeTypesResourceIT {

    private static final String DEFAULT_BARCODE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE_TYPE_NAME = "BBBBBBBBBB";

    @Autowired
    private BarcodeTypesRepository barcodeTypesRepository;

    @Autowired
    private BarcodeTypesMapper barcodeTypesMapper;

    @Autowired
    private BarcodeTypesService barcodeTypesService;

    @Autowired
    private BarcodeTypesQueryService barcodeTypesQueryService;

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

    private MockMvc restBarcodeTypesMockMvc;

    private BarcodeTypes barcodeTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BarcodeTypesResource barcodeTypesResource = new BarcodeTypesResource(barcodeTypesService, barcodeTypesQueryService);
        this.restBarcodeTypesMockMvc = MockMvcBuilders.standaloneSetup(barcodeTypesResource)
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
    public static BarcodeTypes createEntity(EntityManager em) {
        BarcodeTypes barcodeTypes = new BarcodeTypes()
            .barcodeTypeName(DEFAULT_BARCODE_TYPE_NAME);
        return barcodeTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BarcodeTypes createUpdatedEntity(EntityManager em) {
        BarcodeTypes barcodeTypes = new BarcodeTypes()
            .barcodeTypeName(UPDATED_BARCODE_TYPE_NAME);
        return barcodeTypes;
    }

    @BeforeEach
    public void initTest() {
        barcodeTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createBarcodeTypes() throws Exception {
        int databaseSizeBeforeCreate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);
        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeCreate + 1);
        BarcodeTypes testBarcodeTypes = barcodeTypesList.get(barcodeTypesList.size() - 1);
        assertThat(testBarcodeTypes.getBarcodeTypeName()).isEqualTo(DEFAULT_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void createBarcodeTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes with an existing ID
        barcodeTypes.setId(1L);
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBarcodeTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = barcodeTypesRepository.findAll().size();
        // set the field null
        barcodeTypes.setBarcodeTypeName(null);

        // Create the BarcodeTypes, which fails.
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        restBarcodeTypesMockMvc.perform(post("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].barcodeTypeName").value(hasItem(DEFAULT_BARCODE_TYPE_NAME)));
    }
    
    @Test
    @Transactional
    public void getBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/{id}", barcodeTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(barcodeTypes.getId().intValue()))
            .andExpect(jsonPath("$.barcodeTypeName").value(DEFAULT_BARCODE_TYPE_NAME));
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByBarcodeTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where barcodeTypeName equals to DEFAULT_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldBeFound("barcodeTypeName.equals=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the barcodeTypesList where barcodeTypeName equals to UPDATED_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldNotBeFound("barcodeTypeName.equals=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByBarcodeTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where barcodeTypeName not equals to DEFAULT_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldNotBeFound("barcodeTypeName.notEquals=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the barcodeTypesList where barcodeTypeName not equals to UPDATED_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldBeFound("barcodeTypeName.notEquals=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByBarcodeTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where barcodeTypeName in DEFAULT_BARCODE_TYPE_NAME or UPDATED_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldBeFound("barcodeTypeName.in=" + DEFAULT_BARCODE_TYPE_NAME + "," + UPDATED_BARCODE_TYPE_NAME);

        // Get all the barcodeTypesList where barcodeTypeName equals to UPDATED_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldNotBeFound("barcodeTypeName.in=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByBarcodeTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where barcodeTypeName is not null
        defaultBarcodeTypesShouldBeFound("barcodeTypeName.specified=true");

        // Get all the barcodeTypesList where barcodeTypeName is null
        defaultBarcodeTypesShouldNotBeFound("barcodeTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllBarcodeTypesByBarcodeTypeNameContainsSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where barcodeTypeName contains DEFAULT_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldBeFound("barcodeTypeName.contains=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the barcodeTypesList where barcodeTypeName contains UPDATED_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldNotBeFound("barcodeTypeName.contains=" + UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllBarcodeTypesByBarcodeTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        // Get all the barcodeTypesList where barcodeTypeName does not contain DEFAULT_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldNotBeFound("barcodeTypeName.doesNotContain=" + DEFAULT_BARCODE_TYPE_NAME);

        // Get all the barcodeTypesList where barcodeTypeName does not contain UPDATED_BARCODE_TYPE_NAME
        defaultBarcodeTypesShouldBeFound("barcodeTypeName.doesNotContain=" + UPDATED_BARCODE_TYPE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBarcodeTypesShouldBeFound(String filter) throws Exception {
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(barcodeTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].barcodeTypeName").value(hasItem(DEFAULT_BARCODE_TYPE_NAME)));

        // Check, that the count call also returns 1
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBarcodeTypesShouldNotBeFound(String filter) throws Exception {
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBarcodeTypes() throws Exception {
        // Get the barcodeTypes
        restBarcodeTypesMockMvc.perform(get("/api/barcode-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        int databaseSizeBeforeUpdate = barcodeTypesRepository.findAll().size();

        // Update the barcodeTypes
        BarcodeTypes updatedBarcodeTypes = barcodeTypesRepository.findById(barcodeTypes.getId()).get();
        // Disconnect from session so that the updates on updatedBarcodeTypes are not directly saved in db
        em.detach(updatedBarcodeTypes);
        updatedBarcodeTypes
            .barcodeTypeName(UPDATED_BARCODE_TYPE_NAME);
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(updatedBarcodeTypes);

        restBarcodeTypesMockMvc.perform(put("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isOk());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeUpdate);
        BarcodeTypes testBarcodeTypes = barcodeTypesList.get(barcodeTypesList.size() - 1);
        assertThat(testBarcodeTypes.getBarcodeTypeName()).isEqualTo(UPDATED_BARCODE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBarcodeTypes() throws Exception {
        int databaseSizeBeforeUpdate = barcodeTypesRepository.findAll().size();

        // Create the BarcodeTypes
        BarcodeTypesDTO barcodeTypesDTO = barcodeTypesMapper.toDto(barcodeTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBarcodeTypesMockMvc.perform(put("/api/barcode-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(barcodeTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BarcodeTypes in the database
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBarcodeTypes() throws Exception {
        // Initialize the database
        barcodeTypesRepository.saveAndFlush(barcodeTypes);

        int databaseSizeBeforeDelete = barcodeTypesRepository.findAll().size();

        // Delete the barcodeTypes
        restBarcodeTypesMockMvc.perform(delete("/api/barcode-types/{id}", barcodeTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BarcodeTypes> barcodeTypesList = barcodeTypesRepository.findAll();
        assertThat(barcodeTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeTypes.class);
        BarcodeTypes barcodeTypes1 = new BarcodeTypes();
        barcodeTypes1.setId(1L);
        BarcodeTypes barcodeTypes2 = new BarcodeTypes();
        barcodeTypes2.setId(barcodeTypes1.getId());
        assertThat(barcodeTypes1).isEqualTo(barcodeTypes2);
        barcodeTypes2.setId(2L);
        assertThat(barcodeTypes1).isNotEqualTo(barcodeTypes2);
        barcodeTypes1.setId(null);
        assertThat(barcodeTypes1).isNotEqualTo(barcodeTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BarcodeTypesDTO.class);
        BarcodeTypesDTO barcodeTypesDTO1 = new BarcodeTypesDTO();
        barcodeTypesDTO1.setId(1L);
        BarcodeTypesDTO barcodeTypesDTO2 = new BarcodeTypesDTO();
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO2.setId(barcodeTypesDTO1.getId());
        assertThat(barcodeTypesDTO1).isEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO2.setId(2L);
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
        barcodeTypesDTO1.setId(null);
        assertThat(barcodeTypesDTO1).isNotEqualTo(barcodeTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(barcodeTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(barcodeTypesMapper.fromId(null)).isNull();
    }
}
