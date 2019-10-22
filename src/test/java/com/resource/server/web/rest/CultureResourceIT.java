package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.Culture;
import com.resource.server.repository.CultureRepository;
import com.resource.server.service.CultureService;
import com.resource.server.service.dto.CultureDTO;
import com.resource.server.service.mapper.CultureMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.CultureCriteria;
import com.resource.server.service.CultureQueryService;

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
 * Integration tests for the {@link CultureResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class CultureResourceIT {

    private static final String DEFAULT_CULTURE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CULTURE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CULTURE_NAME = "BBBBBBBBBB";

    @Autowired
    private CultureRepository cultureRepository;

    @Autowired
    private CultureMapper cultureMapper;

    @Autowired
    private CultureService cultureService;

    @Autowired
    private CultureQueryService cultureQueryService;

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

    private MockMvc restCultureMockMvc;

    private Culture culture;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CultureResource cultureResource = new CultureResource(cultureService, cultureQueryService);
        this.restCultureMockMvc = MockMvcBuilders.standaloneSetup(cultureResource)
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
    public static Culture createEntity(EntityManager em) {
        Culture culture = new Culture()
            .cultureCode(DEFAULT_CULTURE_CODE)
            .cultureName(DEFAULT_CULTURE_NAME);
        return culture;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Culture createUpdatedEntity(EntityManager em) {
        Culture culture = new Culture()
            .cultureCode(UPDATED_CULTURE_CODE)
            .cultureName(UPDATED_CULTURE_NAME);
        return culture;
    }

    @BeforeEach
    public void initTest() {
        culture = createEntity(em);
    }

    @Test
    @Transactional
    public void createCulture() throws Exception {
        int databaseSizeBeforeCreate = cultureRepository.findAll().size();

        // Create the Culture
        CultureDTO cultureDTO = cultureMapper.toDto(culture);
        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isCreated());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeCreate + 1);
        Culture testCulture = cultureList.get(cultureList.size() - 1);
        assertThat(testCulture.getCultureCode()).isEqualTo(DEFAULT_CULTURE_CODE);
        assertThat(testCulture.getCultureName()).isEqualTo(DEFAULT_CULTURE_NAME);
    }

    @Test
    @Transactional
    public void createCultureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cultureRepository.findAll().size();

        // Create the Culture with an existing ID
        culture.setId(1L);
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCultureCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cultureRepository.findAll().size();
        // set the field null
        culture.setCultureCode(null);

        // Create the Culture, which fails.
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCultureNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cultureRepository.findAll().size();
        // set the field null
        culture.setCultureName(null);

        // Create the Culture, which fails.
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        restCultureMockMvc.perform(post("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCultures() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(culture.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultureCode").value(hasItem(DEFAULT_CULTURE_CODE)))
            .andExpect(jsonPath("$.[*].cultureName").value(hasItem(DEFAULT_CULTURE_NAME)));
    }
    
    @Test
    @Transactional
    public void getCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get the culture
        restCultureMockMvc.perform(get("/api/cultures/{id}", culture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(culture.getId().intValue()))
            .andExpect(jsonPath("$.cultureCode").value(DEFAULT_CULTURE_CODE))
            .andExpect(jsonPath("$.cultureName").value(DEFAULT_CULTURE_NAME));
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureCode equals to DEFAULT_CULTURE_CODE
        defaultCultureShouldBeFound("cultureCode.equals=" + DEFAULT_CULTURE_CODE);

        // Get all the cultureList where cultureCode equals to UPDATED_CULTURE_CODE
        defaultCultureShouldNotBeFound("cultureCode.equals=" + UPDATED_CULTURE_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureCode not equals to DEFAULT_CULTURE_CODE
        defaultCultureShouldNotBeFound("cultureCode.notEquals=" + DEFAULT_CULTURE_CODE);

        // Get all the cultureList where cultureCode not equals to UPDATED_CULTURE_CODE
        defaultCultureShouldBeFound("cultureCode.notEquals=" + UPDATED_CULTURE_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureCode in DEFAULT_CULTURE_CODE or UPDATED_CULTURE_CODE
        defaultCultureShouldBeFound("cultureCode.in=" + DEFAULT_CULTURE_CODE + "," + UPDATED_CULTURE_CODE);

        // Get all the cultureList where cultureCode equals to UPDATED_CULTURE_CODE
        defaultCultureShouldNotBeFound("cultureCode.in=" + UPDATED_CULTURE_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureCode is not null
        defaultCultureShouldBeFound("cultureCode.specified=true");

        // Get all the cultureList where cultureCode is null
        defaultCultureShouldNotBeFound("cultureCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCulturesByCultureCodeContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureCode contains DEFAULT_CULTURE_CODE
        defaultCultureShouldBeFound("cultureCode.contains=" + DEFAULT_CULTURE_CODE);

        // Get all the cultureList where cultureCode contains UPDATED_CULTURE_CODE
        defaultCultureShouldNotBeFound("cultureCode.contains=" + UPDATED_CULTURE_CODE);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureCodeNotContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureCode does not contain DEFAULT_CULTURE_CODE
        defaultCultureShouldNotBeFound("cultureCode.doesNotContain=" + DEFAULT_CULTURE_CODE);

        // Get all the cultureList where cultureCode does not contain UPDATED_CULTURE_CODE
        defaultCultureShouldBeFound("cultureCode.doesNotContain=" + UPDATED_CULTURE_CODE);
    }


    @Test
    @Transactional
    public void getAllCulturesByCultureNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureName equals to DEFAULT_CULTURE_NAME
        defaultCultureShouldBeFound("cultureName.equals=" + DEFAULT_CULTURE_NAME);

        // Get all the cultureList where cultureName equals to UPDATED_CULTURE_NAME
        defaultCultureShouldNotBeFound("cultureName.equals=" + UPDATED_CULTURE_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureName not equals to DEFAULT_CULTURE_NAME
        defaultCultureShouldNotBeFound("cultureName.notEquals=" + DEFAULT_CULTURE_NAME);

        // Get all the cultureList where cultureName not equals to UPDATED_CULTURE_NAME
        defaultCultureShouldBeFound("cultureName.notEquals=" + UPDATED_CULTURE_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureNameIsInShouldWork() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureName in DEFAULT_CULTURE_NAME or UPDATED_CULTURE_NAME
        defaultCultureShouldBeFound("cultureName.in=" + DEFAULT_CULTURE_NAME + "," + UPDATED_CULTURE_NAME);

        // Get all the cultureList where cultureName equals to UPDATED_CULTURE_NAME
        defaultCultureShouldNotBeFound("cultureName.in=" + UPDATED_CULTURE_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureName is not null
        defaultCultureShouldBeFound("cultureName.specified=true");

        // Get all the cultureList where cultureName is null
        defaultCultureShouldNotBeFound("cultureName.specified=false");
    }
                @Test
    @Transactional
    public void getAllCulturesByCultureNameContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureName contains DEFAULT_CULTURE_NAME
        defaultCultureShouldBeFound("cultureName.contains=" + DEFAULT_CULTURE_NAME);

        // Get all the cultureList where cultureName contains UPDATED_CULTURE_NAME
        defaultCultureShouldNotBeFound("cultureName.contains=" + UPDATED_CULTURE_NAME);
    }

    @Test
    @Transactional
    public void getAllCulturesByCultureNameNotContainsSomething() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        // Get all the cultureList where cultureName does not contain DEFAULT_CULTURE_NAME
        defaultCultureShouldNotBeFound("cultureName.doesNotContain=" + DEFAULT_CULTURE_NAME);

        // Get all the cultureList where cultureName does not contain UPDATED_CULTURE_NAME
        defaultCultureShouldBeFound("cultureName.doesNotContain=" + UPDATED_CULTURE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCultureShouldBeFound(String filter) throws Exception {
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(culture.getId().intValue())))
            .andExpect(jsonPath("$.[*].cultureCode").value(hasItem(DEFAULT_CULTURE_CODE)))
            .andExpect(jsonPath("$.[*].cultureName").value(hasItem(DEFAULT_CULTURE_NAME)));

        // Check, that the count call also returns 1
        restCultureMockMvc.perform(get("/api/cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCultureShouldNotBeFound(String filter) throws Exception {
        restCultureMockMvc.perform(get("/api/cultures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCultureMockMvc.perform(get("/api/cultures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCulture() throws Exception {
        // Get the culture
        restCultureMockMvc.perform(get("/api/cultures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        int databaseSizeBeforeUpdate = cultureRepository.findAll().size();

        // Update the culture
        Culture updatedCulture = cultureRepository.findById(culture.getId()).get();
        // Disconnect from session so that the updates on updatedCulture are not directly saved in db
        em.detach(updatedCulture);
        updatedCulture
            .cultureCode(UPDATED_CULTURE_CODE)
            .cultureName(UPDATED_CULTURE_NAME);
        CultureDTO cultureDTO = cultureMapper.toDto(updatedCulture);

        restCultureMockMvc.perform(put("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isOk());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeUpdate);
        Culture testCulture = cultureList.get(cultureList.size() - 1);
        assertThat(testCulture.getCultureCode()).isEqualTo(UPDATED_CULTURE_CODE);
        assertThat(testCulture.getCultureName()).isEqualTo(UPDATED_CULTURE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCulture() throws Exception {
        int databaseSizeBeforeUpdate = cultureRepository.findAll().size();

        // Create the Culture
        CultureDTO cultureDTO = cultureMapper.toDto(culture);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCultureMockMvc.perform(put("/api/cultures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cultureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Culture in the database
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCulture() throws Exception {
        // Initialize the database
        cultureRepository.saveAndFlush(culture);

        int databaseSizeBeforeDelete = cultureRepository.findAll().size();

        // Delete the culture
        restCultureMockMvc.perform(delete("/api/cultures/{id}", culture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Culture> cultureList = cultureRepository.findAll();
        assertThat(cultureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Culture.class);
        Culture culture1 = new Culture();
        culture1.setId(1L);
        Culture culture2 = new Culture();
        culture2.setId(culture1.getId());
        assertThat(culture1).isEqualTo(culture2);
        culture2.setId(2L);
        assertThat(culture1).isNotEqualTo(culture2);
        culture1.setId(null);
        assertThat(culture1).isNotEqualTo(culture2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CultureDTO.class);
        CultureDTO cultureDTO1 = new CultureDTO();
        cultureDTO1.setId(1L);
        CultureDTO cultureDTO2 = new CultureDTO();
        assertThat(cultureDTO1).isNotEqualTo(cultureDTO2);
        cultureDTO2.setId(cultureDTO1.getId());
        assertThat(cultureDTO1).isEqualTo(cultureDTO2);
        cultureDTO2.setId(2L);
        assertThat(cultureDTO1).isNotEqualTo(cultureDTO2);
        cultureDTO1.setId(null);
        assertThat(cultureDTO1).isNotEqualTo(cultureDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cultureMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cultureMapper.fromId(null)).isNull();
    }
}
