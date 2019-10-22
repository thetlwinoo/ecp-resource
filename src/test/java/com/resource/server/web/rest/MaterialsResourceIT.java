package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.Materials;
import com.resource.server.repository.MaterialsRepository;
import com.resource.server.service.MaterialsService;
import com.resource.server.service.dto.MaterialsDTO;
import com.resource.server.service.mapper.MaterialsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.MaterialsCriteria;
import com.resource.server.service.MaterialsQueryService;

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
 * Integration tests for the {@link MaterialsResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class MaterialsResourceIT {

    private static final String DEFAULT_MATERIAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_NAME = "BBBBBBBBBB";

    @Autowired
    private MaterialsRepository materialsRepository;

    @Autowired
    private MaterialsMapper materialsMapper;

    @Autowired
    private MaterialsService materialsService;

    @Autowired
    private MaterialsQueryService materialsQueryService;

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

    private MockMvc restMaterialsMockMvc;

    private Materials materials;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MaterialsResource materialsResource = new MaterialsResource(materialsService, materialsQueryService);
        this.restMaterialsMockMvc = MockMvcBuilders.standaloneSetup(materialsResource)
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
    public static Materials createEntity(EntityManager em) {
        Materials materials = new Materials()
            .materialName(DEFAULT_MATERIAL_NAME);
        return materials;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materials createUpdatedEntity(EntityManager em) {
        Materials materials = new Materials()
            .materialName(UPDATED_MATERIAL_NAME);
        return materials;
    }

    @BeforeEach
    public void initTest() {
        materials = createEntity(em);
    }

    @Test
    @Transactional
    public void createMaterials() throws Exception {
        int databaseSizeBeforeCreate = materialsRepository.findAll().size();

        // Create the Materials
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);
        restMaterialsMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isCreated());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeCreate + 1);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getMaterialName()).isEqualTo(DEFAULT_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void createMaterialsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materialsRepository.findAll().size();

        // Create the Materials with an existing ID
        materials.setId(1L);
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialsMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMaterialNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialsRepository.findAll().size();
        // set the field null
        materials.setMaterialName(null);

        // Create the Materials, which fails.
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);

        restMaterialsMockMvc.perform(post("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isBadRequest());

        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList
        restMaterialsMockMvc.perform(get("/api/materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materials.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)));
    }
    
    @Test
    @Transactional
    public void getMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get the materials
        restMaterialsMockMvc.perform(get("/api/materials/{id}", materials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(materials.getId().intValue()))
            .andExpect(jsonPath("$.materialName").value(DEFAULT_MATERIAL_NAME));
    }

    @Test
    @Transactional
    public void getAllMaterialsByMaterialNameIsEqualToSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where materialName equals to DEFAULT_MATERIAL_NAME
        defaultMaterialsShouldBeFound("materialName.equals=" + DEFAULT_MATERIAL_NAME);

        // Get all the materialsList where materialName equals to UPDATED_MATERIAL_NAME
        defaultMaterialsShouldNotBeFound("materialName.equals=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByMaterialNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where materialName not equals to DEFAULT_MATERIAL_NAME
        defaultMaterialsShouldNotBeFound("materialName.notEquals=" + DEFAULT_MATERIAL_NAME);

        // Get all the materialsList where materialName not equals to UPDATED_MATERIAL_NAME
        defaultMaterialsShouldBeFound("materialName.notEquals=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByMaterialNameIsInShouldWork() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where materialName in DEFAULT_MATERIAL_NAME or UPDATED_MATERIAL_NAME
        defaultMaterialsShouldBeFound("materialName.in=" + DEFAULT_MATERIAL_NAME + "," + UPDATED_MATERIAL_NAME);

        // Get all the materialsList where materialName equals to UPDATED_MATERIAL_NAME
        defaultMaterialsShouldNotBeFound("materialName.in=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByMaterialNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where materialName is not null
        defaultMaterialsShouldBeFound("materialName.specified=true");

        // Get all the materialsList where materialName is null
        defaultMaterialsShouldNotBeFound("materialName.specified=false");
    }
                @Test
    @Transactional
    public void getAllMaterialsByMaterialNameContainsSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where materialName contains DEFAULT_MATERIAL_NAME
        defaultMaterialsShouldBeFound("materialName.contains=" + DEFAULT_MATERIAL_NAME);

        // Get all the materialsList where materialName contains UPDATED_MATERIAL_NAME
        defaultMaterialsShouldNotBeFound("materialName.contains=" + UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void getAllMaterialsByMaterialNameNotContainsSomething() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList where materialName does not contain DEFAULT_MATERIAL_NAME
        defaultMaterialsShouldNotBeFound("materialName.doesNotContain=" + DEFAULT_MATERIAL_NAME);

        // Get all the materialsList where materialName does not contain UPDATED_MATERIAL_NAME
        defaultMaterialsShouldBeFound("materialName.doesNotContain=" + UPDATED_MATERIAL_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMaterialsShouldBeFound(String filter) throws Exception {
        restMaterialsMockMvc.perform(get("/api/materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materials.getId().intValue())))
            .andExpect(jsonPath("$.[*].materialName").value(hasItem(DEFAULT_MATERIAL_NAME)));

        // Check, that the count call also returns 1
        restMaterialsMockMvc.perform(get("/api/materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMaterialsShouldNotBeFound(String filter) throws Exception {
        restMaterialsMockMvc.perform(get("/api/materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMaterialsMockMvc.perform(get("/api/materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMaterials() throws Exception {
        // Get the materials
        restMaterialsMockMvc.perform(get("/api/materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Update the materials
        Materials updatedMaterials = materialsRepository.findById(materials.getId()).get();
        // Disconnect from session so that the updates on updatedMaterials are not directly saved in db
        em.detach(updatedMaterials);
        updatedMaterials
            .materialName(UPDATED_MATERIAL_NAME);
        MaterialsDTO materialsDTO = materialsMapper.toDto(updatedMaterials);

        restMaterialsMockMvc.perform(put("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isOk());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getMaterialName()).isEqualTo(UPDATED_MATERIAL_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Create the Materials
        MaterialsDTO materialsDTO = materialsMapper.toDto(materials);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialsMockMvc.perform(put("/api/materials")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(materialsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeDelete = materialsRepository.findAll().size();

        // Delete the materials
        restMaterialsMockMvc.perform(delete("/api/materials/{id}", materials.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Materials.class);
        Materials materials1 = new Materials();
        materials1.setId(1L);
        Materials materials2 = new Materials();
        materials2.setId(materials1.getId());
        assertThat(materials1).isEqualTo(materials2);
        materials2.setId(2L);
        assertThat(materials1).isNotEqualTo(materials2);
        materials1.setId(null);
        assertThat(materials1).isNotEqualTo(materials2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MaterialsDTO.class);
        MaterialsDTO materialsDTO1 = new MaterialsDTO();
        materialsDTO1.setId(1L);
        MaterialsDTO materialsDTO2 = new MaterialsDTO();
        assertThat(materialsDTO1).isNotEqualTo(materialsDTO2);
        materialsDTO2.setId(materialsDTO1.getId());
        assertThat(materialsDTO1).isEqualTo(materialsDTO2);
        materialsDTO2.setId(2L);
        assertThat(materialsDTO1).isNotEqualTo(materialsDTO2);
        materialsDTO1.setId(null);
        assertThat(materialsDTO1).isNotEqualTo(materialsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(materialsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(materialsMapper.fromId(null)).isNull();
    }
}
