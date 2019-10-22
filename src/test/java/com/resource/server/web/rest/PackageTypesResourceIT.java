package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.PackageTypes;
import com.resource.server.repository.PackageTypesRepository;
import com.resource.server.service.PackageTypesService;
import com.resource.server.service.dto.PackageTypesDTO;
import com.resource.server.service.mapper.PackageTypesMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.PackageTypesCriteria;
import com.resource.server.service.PackageTypesQueryService;

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
 * Integration tests for the {@link PackageTypesResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class PackageTypesResourceIT {

    private static final String DEFAULT_PACKAGE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_TYPE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_VALID_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PackageTypesRepository packageTypesRepository;

    @Autowired
    private PackageTypesMapper packageTypesMapper;

    @Autowired
    private PackageTypesService packageTypesService;

    @Autowired
    private PackageTypesQueryService packageTypesQueryService;

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

    private MockMvc restPackageTypesMockMvc;

    private PackageTypes packageTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PackageTypesResource packageTypesResource = new PackageTypesResource(packageTypesService, packageTypesQueryService);
        this.restPackageTypesMockMvc = MockMvcBuilders.standaloneSetup(packageTypesResource)
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
    public static PackageTypes createEntity(EntityManager em) {
        PackageTypes packageTypes = new PackageTypes()
            .packageTypeName(DEFAULT_PACKAGE_TYPE_NAME)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return packageTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageTypes createUpdatedEntity(EntityManager em) {
        PackageTypes packageTypes = new PackageTypes()
            .packageTypeName(UPDATED_PACKAGE_TYPE_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return packageTypes;
    }

    @BeforeEach
    public void initTest() {
        packageTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createPackageTypes() throws Exception {
        int databaseSizeBeforeCreate = packageTypesRepository.findAll().size();

        // Create the PackageTypes
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);
        restPackageTypesMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeCreate + 1);
        PackageTypes testPackageTypes = packageTypesList.get(packageTypesList.size() - 1);
        assertThat(testPackageTypes.getPackageTypeName()).isEqualTo(DEFAULT_PACKAGE_TYPE_NAME);
        assertThat(testPackageTypes.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testPackageTypes.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createPackageTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = packageTypesRepository.findAll().size();

        // Create the PackageTypes with an existing ID
        packageTypes.setId(1L);
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageTypesMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPackageTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypesRepository.findAll().size();
        // set the field null
        packageTypes.setPackageTypeName(null);

        // Create the PackageTypes, which fails.
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        restPackageTypesMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypesRepository.findAll().size();
        // set the field null
        packageTypes.setValidFrom(null);

        // Create the PackageTypes, which fails.
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        restPackageTypesMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = packageTypesRepository.findAll().size();
        // set the field null
        packageTypes.setValidTo(null);

        // Create the PackageTypes, which fails.
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        restPackageTypesMockMvc.perform(post("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList
        restPackageTypesMockMvc.perform(get("/api/package-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].packageTypeName").value(hasItem(DEFAULT_PACKAGE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getPackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get the packageTypes
        restPackageTypesMockMvc.perform(get("/api/package-types/{id}", packageTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(packageTypes.getId().intValue()))
            .andExpect(jsonPath("$.packageTypeName").value(DEFAULT_PACKAGE_TYPE_NAME))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }

    @Test
    @Transactional
    public void getAllPackageTypesByPackageTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where packageTypeName equals to DEFAULT_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldBeFound("packageTypeName.equals=" + DEFAULT_PACKAGE_TYPE_NAME);

        // Get all the packageTypesList where packageTypeName equals to UPDATED_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldNotBeFound("packageTypeName.equals=" + UPDATED_PACKAGE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByPackageTypeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where packageTypeName not equals to DEFAULT_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldNotBeFound("packageTypeName.notEquals=" + DEFAULT_PACKAGE_TYPE_NAME);

        // Get all the packageTypesList where packageTypeName not equals to UPDATED_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldBeFound("packageTypeName.notEquals=" + UPDATED_PACKAGE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByPackageTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where packageTypeName in DEFAULT_PACKAGE_TYPE_NAME or UPDATED_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldBeFound("packageTypeName.in=" + DEFAULT_PACKAGE_TYPE_NAME + "," + UPDATED_PACKAGE_TYPE_NAME);

        // Get all the packageTypesList where packageTypeName equals to UPDATED_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldNotBeFound("packageTypeName.in=" + UPDATED_PACKAGE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByPackageTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where packageTypeName is not null
        defaultPackageTypesShouldBeFound("packageTypeName.specified=true");

        // Get all the packageTypesList where packageTypeName is null
        defaultPackageTypesShouldNotBeFound("packageTypeName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPackageTypesByPackageTypeNameContainsSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where packageTypeName contains DEFAULT_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldBeFound("packageTypeName.contains=" + DEFAULT_PACKAGE_TYPE_NAME);

        // Get all the packageTypesList where packageTypeName contains UPDATED_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldNotBeFound("packageTypeName.contains=" + UPDATED_PACKAGE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByPackageTypeNameNotContainsSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where packageTypeName does not contain DEFAULT_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldNotBeFound("packageTypeName.doesNotContain=" + DEFAULT_PACKAGE_TYPE_NAME);

        // Get all the packageTypesList where packageTypeName does not contain UPDATED_PACKAGE_TYPE_NAME
        defaultPackageTypesShouldBeFound("packageTypeName.doesNotContain=" + UPDATED_PACKAGE_TYPE_NAME);
    }


    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom equals to DEFAULT_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom not equals to DEFAULT_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom not equals to UPDATED_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the packageTypesList where validFrom equals to UPDATED_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom is not null
        defaultPackageTypesShouldBeFound("validFrom.specified=true");

        // Get all the packageTypesList where validFrom is null
        defaultPackageTypesShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom is greater than or equal to DEFAULT_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.greaterThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom is greater than or equal to UPDATED_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.greaterThanOrEqual=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom is less than or equal to DEFAULT_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.lessThanOrEqual=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom is less than or equal to SMALLER_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.lessThanOrEqual=" + SMALLER_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsLessThanSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom is less than DEFAULT_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.lessThan=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom is less than UPDATED_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.lessThan=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validFrom is greater than DEFAULT_VALID_FROM
        defaultPackageTypesShouldNotBeFound("validFrom.greaterThan=" + DEFAULT_VALID_FROM);

        // Get all the packageTypesList where validFrom is greater than SMALLER_VALID_FROM
        defaultPackageTypesShouldBeFound("validFrom.greaterThan=" + SMALLER_VALID_FROM);
    }


    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo equals to DEFAULT_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo equals to UPDATED_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo not equals to DEFAULT_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo not equals to UPDATED_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the packageTypesList where validTo equals to UPDATED_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo is not null
        defaultPackageTypesShouldBeFound("validTo.specified=true");

        // Get all the packageTypesList where validTo is null
        defaultPackageTypesShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo is greater than or equal to DEFAULT_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.greaterThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo is greater than or equal to UPDATED_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.greaterThanOrEqual=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo is less than or equal to DEFAULT_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.lessThanOrEqual=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo is less than or equal to SMALLER_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.lessThanOrEqual=" + SMALLER_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsLessThanSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo is less than DEFAULT_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.lessThan=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo is less than UPDATED_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.lessThan=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllPackageTypesByValidToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        // Get all the packageTypesList where validTo is greater than DEFAULT_VALID_TO
        defaultPackageTypesShouldNotBeFound("validTo.greaterThan=" + DEFAULT_VALID_TO);

        // Get all the packageTypesList where validTo is greater than SMALLER_VALID_TO
        defaultPackageTypesShouldBeFound("validTo.greaterThan=" + SMALLER_VALID_TO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPackageTypesShouldBeFound(String filter) throws Exception {
        restPackageTypesMockMvc.perform(get("/api/package-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].packageTypeName").value(hasItem(DEFAULT_PACKAGE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restPackageTypesMockMvc.perform(get("/api/package-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPackageTypesShouldNotBeFound(String filter) throws Exception {
        restPackageTypesMockMvc.perform(get("/api/package-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPackageTypesMockMvc.perform(get("/api/package-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPackageTypes() throws Exception {
        // Get the packageTypes
        restPackageTypesMockMvc.perform(get("/api/package-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        int databaseSizeBeforeUpdate = packageTypesRepository.findAll().size();

        // Update the packageTypes
        PackageTypes updatedPackageTypes = packageTypesRepository.findById(packageTypes.getId()).get();
        // Disconnect from session so that the updates on updatedPackageTypes are not directly saved in db
        em.detach(updatedPackageTypes);
        updatedPackageTypes
            .packageTypeName(UPDATED_PACKAGE_TYPE_NAME)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(updatedPackageTypes);

        restPackageTypesMockMvc.perform(put("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isOk());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeUpdate);
        PackageTypes testPackageTypes = packageTypesList.get(packageTypesList.size() - 1);
        assertThat(testPackageTypes.getPackageTypeName()).isEqualTo(UPDATED_PACKAGE_TYPE_NAME);
        assertThat(testPackageTypes.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testPackageTypes.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingPackageTypes() throws Exception {
        int databaseSizeBeforeUpdate = packageTypesRepository.findAll().size();

        // Create the PackageTypes
        PackageTypesDTO packageTypesDTO = packageTypesMapper.toDto(packageTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageTypesMockMvc.perform(put("/api/package-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(packageTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PackageTypes in the database
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePackageTypes() throws Exception {
        // Initialize the database
        packageTypesRepository.saveAndFlush(packageTypes);

        int databaseSizeBeforeDelete = packageTypesRepository.findAll().size();

        // Delete the packageTypes
        restPackageTypesMockMvc.perform(delete("/api/package-types/{id}", packageTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackageTypes> packageTypesList = packageTypesRepository.findAll();
        assertThat(packageTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageTypes.class);
        PackageTypes packageTypes1 = new PackageTypes();
        packageTypes1.setId(1L);
        PackageTypes packageTypes2 = new PackageTypes();
        packageTypes2.setId(packageTypes1.getId());
        assertThat(packageTypes1).isEqualTo(packageTypes2);
        packageTypes2.setId(2L);
        assertThat(packageTypes1).isNotEqualTo(packageTypes2);
        packageTypes1.setId(null);
        assertThat(packageTypes1).isNotEqualTo(packageTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageTypesDTO.class);
        PackageTypesDTO packageTypesDTO1 = new PackageTypesDTO();
        packageTypesDTO1.setId(1L);
        PackageTypesDTO packageTypesDTO2 = new PackageTypesDTO();
        assertThat(packageTypesDTO1).isNotEqualTo(packageTypesDTO2);
        packageTypesDTO2.setId(packageTypesDTO1.getId());
        assertThat(packageTypesDTO1).isEqualTo(packageTypesDTO2);
        packageTypesDTO2.setId(2L);
        assertThat(packageTypesDTO1).isNotEqualTo(packageTypesDTO2);
        packageTypesDTO1.setId(null);
        assertThat(packageTypesDTO1).isNotEqualTo(packageTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(packageTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(packageTypesMapper.fromId(null)).isNull();
    }
}
