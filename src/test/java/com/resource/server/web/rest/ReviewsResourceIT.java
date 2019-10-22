package com.resource.server.web.rest;

import com.resource.server.ResourceApp;
import com.resource.server.domain.Reviews;
import com.resource.server.domain.ReviewLines;
import com.resource.server.domain.Orders;
import com.resource.server.repository.ReviewsRepository;
import com.resource.server.service.ReviewsService;
import com.resource.server.service.dto.ReviewsDTO;
import com.resource.server.service.mapper.ReviewsMapper;
import com.resource.server.web.rest.errors.ExceptionTranslator;
import com.resource.server.service.dto.ReviewsCriteria;
import com.resource.server.service.ReviewsQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@link ReviewsResource} REST controller.
 */
@SpringBootTest(classes = ResourceApp.class)
public class ReviewsResourceIT {

    private static final String DEFAULT_REVIEWER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ADDRESS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_REVIEW_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REVIEW_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_REVIEW_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_OVER_ALL_SELLER_RATING = 1;
    private static final Integer UPDATED_OVER_ALL_SELLER_RATING = 2;
    private static final Integer SMALLER_OVER_ALL_SELLER_RATING = 1 - 1;

    private static final String DEFAULT_OVER_ALL_SELLER_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVER_ALL_SELLER_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_OVER_ALL_DELIVERY_RATING = 1;
    private static final Integer UPDATED_OVER_ALL_DELIVERY_RATING = 2;
    private static final Integer SMALLER_OVER_ALL_DELIVERY_RATING = 1 - 1;

    private static final String DEFAULT_OVER_ALL_DELIVERY_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_OVER_ALL_DELIVERY_REVIEW = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REVIEW_AS_ANONYMOUS = false;
    private static final Boolean UPDATED_REVIEW_AS_ANONYMOUS = true;

    private static final Boolean DEFAULT_COMPLETED_REVIEW = false;
    private static final Boolean UPDATED_COMPLETED_REVIEW = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LAST_EDITED_WHEN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_EDITED_WHEN = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_EDITED_WHEN = LocalDate.ofEpochDay(-1L);

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private ReviewsMapper reviewsMapper;

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private ReviewsQueryService reviewsQueryService;

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

    private MockMvc restReviewsMockMvc;

    private Reviews reviews;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReviewsResource reviewsResource = new ReviewsResource(reviewsService, reviewsQueryService);
        this.restReviewsMockMvc = MockMvcBuilders.standaloneSetup(reviewsResource)
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
    public static Reviews createEntity(EntityManager em) {
        Reviews reviews = new Reviews()
            .reviewerName(DEFAULT_REVIEWER_NAME)
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .reviewDate(DEFAULT_REVIEW_DATE)
            .overAllSellerRating(DEFAULT_OVER_ALL_SELLER_RATING)
            .overAllSellerReview(DEFAULT_OVER_ALL_SELLER_REVIEW)
            .overAllDeliveryRating(DEFAULT_OVER_ALL_DELIVERY_RATING)
            .overAllDeliveryReview(DEFAULT_OVER_ALL_DELIVERY_REVIEW)
            .reviewAsAnonymous(DEFAULT_REVIEW_AS_ANONYMOUS)
            .completedReview(DEFAULT_COMPLETED_REVIEW)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return reviews;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reviews createUpdatedEntity(EntityManager em) {
        Reviews reviews = new Reviews()
            .reviewerName(UPDATED_REVIEWER_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .reviewDate(UPDATED_REVIEW_DATE)
            .overAllSellerRating(UPDATED_OVER_ALL_SELLER_RATING)
            .overAllSellerReview(UPDATED_OVER_ALL_SELLER_REVIEW)
            .overAllDeliveryRating(UPDATED_OVER_ALL_DELIVERY_RATING)
            .overAllDeliveryReview(UPDATED_OVER_ALL_DELIVERY_REVIEW)
            .reviewAsAnonymous(UPDATED_REVIEW_AS_ANONYMOUS)
            .completedReview(UPDATED_COMPLETED_REVIEW)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return reviews;
    }

    @BeforeEach
    public void initTest() {
        reviews = createEntity(em);
    }

    @Test
    @Transactional
    public void createReviews() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();

        // Create the Reviews
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(reviews);
        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isCreated());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate + 1);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getReviewerName()).isEqualTo(DEFAULT_REVIEWER_NAME);
        assertThat(testReviews.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testReviews.getReviewDate()).isEqualTo(DEFAULT_REVIEW_DATE);
        assertThat(testReviews.getOverAllSellerRating()).isEqualTo(DEFAULT_OVER_ALL_SELLER_RATING);
        assertThat(testReviews.getOverAllSellerReview()).isEqualTo(DEFAULT_OVER_ALL_SELLER_REVIEW);
        assertThat(testReviews.getOverAllDeliveryRating()).isEqualTo(DEFAULT_OVER_ALL_DELIVERY_RATING);
        assertThat(testReviews.getOverAllDeliveryReview()).isEqualTo(DEFAULT_OVER_ALL_DELIVERY_REVIEW);
        assertThat(testReviews.isReviewAsAnonymous()).isEqualTo(DEFAULT_REVIEW_AS_ANONYMOUS);
        assertThat(testReviews.isCompletedReview()).isEqualTo(DEFAULT_COMPLETED_REVIEW);
        assertThat(testReviews.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testReviews.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createReviewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reviewsRepository.findAll().size();

        // Create the Reviews with an existing ID
        reviews.setId(1L);
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(reviews);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReviewsMockMvc.perform(post("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList
        restReviewsMockMvc.perform(get("/api/reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviews.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewerName").value(hasItem(DEFAULT_REVIEWER_NAME)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].overAllSellerRating").value(hasItem(DEFAULT_OVER_ALL_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].overAllSellerReview").value(hasItem(DEFAULT_OVER_ALL_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].overAllDeliveryRating").value(hasItem(DEFAULT_OVER_ALL_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].overAllDeliveryReview").value(hasItem(DEFAULT_OVER_ALL_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].reviewAsAnonymous").value(hasItem(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].completedReview").value(hasItem(DEFAULT_COMPLETED_REVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get the reviews
        restReviewsMockMvc.perform(get("/api/reviews/{id}", reviews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reviews.getId().intValue()))
            .andExpect(jsonPath("$.reviewerName").value(DEFAULT_REVIEWER_NAME))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.reviewDate").value(DEFAULT_REVIEW_DATE.toString()))
            .andExpect(jsonPath("$.overAllSellerRating").value(DEFAULT_OVER_ALL_SELLER_RATING))
            .andExpect(jsonPath("$.overAllSellerReview").value(DEFAULT_OVER_ALL_SELLER_REVIEW.toString()))
            .andExpect(jsonPath("$.overAllDeliveryRating").value(DEFAULT_OVER_ALL_DELIVERY_RATING))
            .andExpect(jsonPath("$.overAllDeliveryReview").value(DEFAULT_OVER_ALL_DELIVERY_REVIEW.toString()))
            .andExpect(jsonPath("$.reviewAsAnonymous").value(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue()))
            .andExpect(jsonPath("$.completedReview").value(DEFAULT_COMPLETED_REVIEW.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewerName equals to DEFAULT_REVIEWER_NAME
        defaultReviewsShouldBeFound("reviewerName.equals=" + DEFAULT_REVIEWER_NAME);

        // Get all the reviewsList where reviewerName equals to UPDATED_REVIEWER_NAME
        defaultReviewsShouldNotBeFound("reviewerName.equals=" + UPDATED_REVIEWER_NAME);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewerName not equals to DEFAULT_REVIEWER_NAME
        defaultReviewsShouldNotBeFound("reviewerName.notEquals=" + DEFAULT_REVIEWER_NAME);

        // Get all the reviewsList where reviewerName not equals to UPDATED_REVIEWER_NAME
        defaultReviewsShouldBeFound("reviewerName.notEquals=" + UPDATED_REVIEWER_NAME);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewerNameIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewerName in DEFAULT_REVIEWER_NAME or UPDATED_REVIEWER_NAME
        defaultReviewsShouldBeFound("reviewerName.in=" + DEFAULT_REVIEWER_NAME + "," + UPDATED_REVIEWER_NAME);

        // Get all the reviewsList where reviewerName equals to UPDATED_REVIEWER_NAME
        defaultReviewsShouldNotBeFound("reviewerName.in=" + UPDATED_REVIEWER_NAME);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewerName is not null
        defaultReviewsShouldBeFound("reviewerName.specified=true");

        // Get all the reviewsList where reviewerName is null
        defaultReviewsShouldNotBeFound("reviewerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllReviewsByReviewerNameContainsSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewerName contains DEFAULT_REVIEWER_NAME
        defaultReviewsShouldBeFound("reviewerName.contains=" + DEFAULT_REVIEWER_NAME);

        // Get all the reviewsList where reviewerName contains UPDATED_REVIEWER_NAME
        defaultReviewsShouldNotBeFound("reviewerName.contains=" + UPDATED_REVIEWER_NAME);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewerNameNotContainsSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewerName does not contain DEFAULT_REVIEWER_NAME
        defaultReviewsShouldNotBeFound("reviewerName.doesNotContain=" + DEFAULT_REVIEWER_NAME);

        // Get all the reviewsList where reviewerName does not contain UPDATED_REVIEWER_NAME
        defaultReviewsShouldBeFound("reviewerName.doesNotContain=" + UPDATED_REVIEWER_NAME);
    }


    @Test
    @Transactional
    public void getAllReviewsByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultReviewsShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the reviewsList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultReviewsShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReviewsByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultReviewsShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the reviewsList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultReviewsShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReviewsByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultReviewsShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the reviewsList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultReviewsShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReviewsByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where emailAddress is not null
        defaultReviewsShouldBeFound("emailAddress.specified=true");

        // Get all the reviewsList where emailAddress is null
        defaultReviewsShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllReviewsByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultReviewsShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the reviewsList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultReviewsShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllReviewsByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultReviewsShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the reviewsList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultReviewsShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate equals to DEFAULT_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.equals=" + DEFAULT_REVIEW_DATE);

        // Get all the reviewsList where reviewDate equals to UPDATED_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.equals=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate not equals to DEFAULT_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.notEquals=" + DEFAULT_REVIEW_DATE);

        // Get all the reviewsList where reviewDate not equals to UPDATED_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.notEquals=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate in DEFAULT_REVIEW_DATE or UPDATED_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.in=" + DEFAULT_REVIEW_DATE + "," + UPDATED_REVIEW_DATE);

        // Get all the reviewsList where reviewDate equals to UPDATED_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.in=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate is not null
        defaultReviewsShouldBeFound("reviewDate.specified=true");

        // Get all the reviewsList where reviewDate is null
        defaultReviewsShouldNotBeFound("reviewDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate is greater than or equal to DEFAULT_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.greaterThanOrEqual=" + DEFAULT_REVIEW_DATE);

        // Get all the reviewsList where reviewDate is greater than or equal to UPDATED_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.greaterThanOrEqual=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate is less than or equal to DEFAULT_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.lessThanOrEqual=" + DEFAULT_REVIEW_DATE);

        // Get all the reviewsList where reviewDate is less than or equal to SMALLER_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.lessThanOrEqual=" + SMALLER_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate is less than DEFAULT_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.lessThan=" + DEFAULT_REVIEW_DATE);

        // Get all the reviewsList where reviewDate is less than UPDATED_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.lessThan=" + UPDATED_REVIEW_DATE);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewDate is greater than DEFAULT_REVIEW_DATE
        defaultReviewsShouldNotBeFound("reviewDate.greaterThan=" + DEFAULT_REVIEW_DATE);

        // Get all the reviewsList where reviewDate is greater than SMALLER_REVIEW_DATE
        defaultReviewsShouldBeFound("reviewDate.greaterThan=" + SMALLER_REVIEW_DATE);
    }


    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating equals to DEFAULT_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.equals=" + DEFAULT_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating equals to UPDATED_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.equals=" + UPDATED_OVER_ALL_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating not equals to DEFAULT_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.notEquals=" + DEFAULT_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating not equals to UPDATED_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.notEquals=" + UPDATED_OVER_ALL_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating in DEFAULT_OVER_ALL_SELLER_RATING or UPDATED_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.in=" + DEFAULT_OVER_ALL_SELLER_RATING + "," + UPDATED_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating equals to UPDATED_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.in=" + UPDATED_OVER_ALL_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating is not null
        defaultReviewsShouldBeFound("overAllSellerRating.specified=true");

        // Get all the reviewsList where overAllSellerRating is null
        defaultReviewsShouldNotBeFound("overAllSellerRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating is greater than or equal to DEFAULT_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.greaterThanOrEqual=" + DEFAULT_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating is greater than or equal to UPDATED_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.greaterThanOrEqual=" + UPDATED_OVER_ALL_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating is less than or equal to DEFAULT_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.lessThanOrEqual=" + DEFAULT_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating is less than or equal to SMALLER_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.lessThanOrEqual=" + SMALLER_OVER_ALL_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating is less than DEFAULT_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.lessThan=" + DEFAULT_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating is less than UPDATED_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.lessThan=" + UPDATED_OVER_ALL_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllSellerRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllSellerRating is greater than DEFAULT_OVER_ALL_SELLER_RATING
        defaultReviewsShouldNotBeFound("overAllSellerRating.greaterThan=" + DEFAULT_OVER_ALL_SELLER_RATING);

        // Get all the reviewsList where overAllSellerRating is greater than SMALLER_OVER_ALL_SELLER_RATING
        defaultReviewsShouldBeFound("overAllSellerRating.greaterThan=" + SMALLER_OVER_ALL_SELLER_RATING);
    }


    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating equals to DEFAULT_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.equals=" + DEFAULT_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating equals to UPDATED_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.equals=" + UPDATED_OVER_ALL_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating not equals to DEFAULT_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.notEquals=" + DEFAULT_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating not equals to UPDATED_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.notEquals=" + UPDATED_OVER_ALL_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating in DEFAULT_OVER_ALL_DELIVERY_RATING or UPDATED_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.in=" + DEFAULT_OVER_ALL_DELIVERY_RATING + "," + UPDATED_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating equals to UPDATED_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.in=" + UPDATED_OVER_ALL_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating is not null
        defaultReviewsShouldBeFound("overAllDeliveryRating.specified=true");

        // Get all the reviewsList where overAllDeliveryRating is null
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating is greater than or equal to DEFAULT_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.greaterThanOrEqual=" + DEFAULT_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating is greater than or equal to UPDATED_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.greaterThanOrEqual=" + UPDATED_OVER_ALL_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating is less than or equal to DEFAULT_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.lessThanOrEqual=" + DEFAULT_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating is less than or equal to SMALLER_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.lessThanOrEqual=" + SMALLER_OVER_ALL_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating is less than DEFAULT_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.lessThan=" + DEFAULT_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating is less than UPDATED_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.lessThan=" + UPDATED_OVER_ALL_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllReviewsByOverAllDeliveryRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where overAllDeliveryRating is greater than DEFAULT_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldNotBeFound("overAllDeliveryRating.greaterThan=" + DEFAULT_OVER_ALL_DELIVERY_RATING);

        // Get all the reviewsList where overAllDeliveryRating is greater than SMALLER_OVER_ALL_DELIVERY_RATING
        defaultReviewsShouldBeFound("overAllDeliveryRating.greaterThan=" + SMALLER_OVER_ALL_DELIVERY_RATING);
    }


    @Test
    @Transactional
    public void getAllReviewsByReviewAsAnonymousIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewAsAnonymous equals to DEFAULT_REVIEW_AS_ANONYMOUS
        defaultReviewsShouldBeFound("reviewAsAnonymous.equals=" + DEFAULT_REVIEW_AS_ANONYMOUS);

        // Get all the reviewsList where reviewAsAnonymous equals to UPDATED_REVIEW_AS_ANONYMOUS
        defaultReviewsShouldNotBeFound("reviewAsAnonymous.equals=" + UPDATED_REVIEW_AS_ANONYMOUS);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewAsAnonymousIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewAsAnonymous not equals to DEFAULT_REVIEW_AS_ANONYMOUS
        defaultReviewsShouldNotBeFound("reviewAsAnonymous.notEquals=" + DEFAULT_REVIEW_AS_ANONYMOUS);

        // Get all the reviewsList where reviewAsAnonymous not equals to UPDATED_REVIEW_AS_ANONYMOUS
        defaultReviewsShouldBeFound("reviewAsAnonymous.notEquals=" + UPDATED_REVIEW_AS_ANONYMOUS);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewAsAnonymousIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewAsAnonymous in DEFAULT_REVIEW_AS_ANONYMOUS or UPDATED_REVIEW_AS_ANONYMOUS
        defaultReviewsShouldBeFound("reviewAsAnonymous.in=" + DEFAULT_REVIEW_AS_ANONYMOUS + "," + UPDATED_REVIEW_AS_ANONYMOUS);

        // Get all the reviewsList where reviewAsAnonymous equals to UPDATED_REVIEW_AS_ANONYMOUS
        defaultReviewsShouldNotBeFound("reviewAsAnonymous.in=" + UPDATED_REVIEW_AS_ANONYMOUS);
    }

    @Test
    @Transactional
    public void getAllReviewsByReviewAsAnonymousIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where reviewAsAnonymous is not null
        defaultReviewsShouldBeFound("reviewAsAnonymous.specified=true");

        // Get all the reviewsList where reviewAsAnonymous is null
        defaultReviewsShouldNotBeFound("reviewAsAnonymous.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByCompletedReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where completedReview equals to DEFAULT_COMPLETED_REVIEW
        defaultReviewsShouldBeFound("completedReview.equals=" + DEFAULT_COMPLETED_REVIEW);

        // Get all the reviewsList where completedReview equals to UPDATED_COMPLETED_REVIEW
        defaultReviewsShouldNotBeFound("completedReview.equals=" + UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void getAllReviewsByCompletedReviewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where completedReview not equals to DEFAULT_COMPLETED_REVIEW
        defaultReviewsShouldNotBeFound("completedReview.notEquals=" + DEFAULT_COMPLETED_REVIEW);

        // Get all the reviewsList where completedReview not equals to UPDATED_COMPLETED_REVIEW
        defaultReviewsShouldBeFound("completedReview.notEquals=" + UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void getAllReviewsByCompletedReviewIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where completedReview in DEFAULT_COMPLETED_REVIEW or UPDATED_COMPLETED_REVIEW
        defaultReviewsShouldBeFound("completedReview.in=" + DEFAULT_COMPLETED_REVIEW + "," + UPDATED_COMPLETED_REVIEW);

        // Get all the reviewsList where completedReview equals to UPDATED_COMPLETED_REVIEW
        defaultReviewsShouldNotBeFound("completedReview.in=" + UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void getAllReviewsByCompletedReviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where completedReview is not null
        defaultReviewsShouldBeFound("completedReview.specified=true");

        // Get all the reviewsList where completedReview is null
        defaultReviewsShouldNotBeFound("completedReview.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultReviewsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultReviewsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultReviewsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultReviewsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultReviewsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the reviewsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultReviewsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedBy is not null
        defaultReviewsShouldBeFound("lastEditedBy.specified=true");

        // Get all the reviewsList where lastEditedBy is null
        defaultReviewsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllReviewsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultReviewsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultReviewsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultReviewsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the reviewsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultReviewsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen is not null
        defaultReviewsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the reviewsList where lastEditedWhen is null
        defaultReviewsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen is greater than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.greaterThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen is greater than or equal to UPDATED_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.greaterThanOrEqual=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen is less than or equal to DEFAULT_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.lessThanOrEqual=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen is less than or equal to SMALLER_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.lessThanOrEqual=" + SMALLER_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen is less than DEFAULT_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.lessThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen is less than UPDATED_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.lessThan=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllReviewsByLastEditedWhenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        // Get all the reviewsList where lastEditedWhen is greater than DEFAULT_LAST_EDITED_WHEN
        defaultReviewsShouldNotBeFound("lastEditedWhen.greaterThan=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the reviewsList where lastEditedWhen is greater than SMALLER_LAST_EDITED_WHEN
        defaultReviewsShouldBeFound("lastEditedWhen.greaterThan=" + SMALLER_LAST_EDITED_WHEN);
    }


    @Test
    @Transactional
    public void getAllReviewsByReviewLineListIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);
        ReviewLines reviewLineList = ReviewLinesResourceIT.createEntity(em);
        em.persist(reviewLineList);
        em.flush();
        reviews.addReviewLineList(reviewLineList);
        reviewsRepository.saveAndFlush(reviews);
        Long reviewLineListId = reviewLineList.getId();

        // Get all the reviewsList where reviewLineList equals to reviewLineListId
        defaultReviewsShouldBeFound("reviewLineListId.equals=" + reviewLineListId);

        // Get all the reviewsList where reviewLineList equals to reviewLineListId + 1
        defaultReviewsShouldNotBeFound("reviewLineListId.equals=" + (reviewLineListId + 1));
    }


    @Test
    @Transactional
    public void getAllReviewsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);
        Orders order = OrdersResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        reviews.setOrder(order);
        order.setOrderOnReview(reviews);
        reviewsRepository.saveAndFlush(reviews);
        Long orderId = order.getId();

        // Get all the reviewsList where order equals to orderId
        defaultReviewsShouldBeFound("orderId.equals=" + orderId);

        // Get all the reviewsList where order equals to orderId + 1
        defaultReviewsShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReviewsShouldBeFound(String filter) throws Exception {
        restReviewsMockMvc.perform(get("/api/reviews?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reviews.getId().intValue())))
            .andExpect(jsonPath("$.[*].reviewerName").value(hasItem(DEFAULT_REVIEWER_NAME)))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].reviewDate").value(hasItem(DEFAULT_REVIEW_DATE.toString())))
            .andExpect(jsonPath("$.[*].overAllSellerRating").value(hasItem(DEFAULT_OVER_ALL_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].overAllSellerReview").value(hasItem(DEFAULT_OVER_ALL_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].overAllDeliveryRating").value(hasItem(DEFAULT_OVER_ALL_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].overAllDeliveryReview").value(hasItem(DEFAULT_OVER_ALL_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].reviewAsAnonymous").value(hasItem(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].completedReview").value(hasItem(DEFAULT_COMPLETED_REVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restReviewsMockMvc.perform(get("/api/reviews/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReviewsShouldNotBeFound(String filter) throws Exception {
        restReviewsMockMvc.perform(get("/api/reviews?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReviewsMockMvc.perform(get("/api/reviews/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReviews() throws Exception {
        // Get the reviews
        restReviewsMockMvc.perform(get("/api/reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Update the reviews
        Reviews updatedReviews = reviewsRepository.findById(reviews.getId()).get();
        // Disconnect from session so that the updates on updatedReviews are not directly saved in db
        em.detach(updatedReviews);
        updatedReviews
            .reviewerName(UPDATED_REVIEWER_NAME)
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .reviewDate(UPDATED_REVIEW_DATE)
            .overAllSellerRating(UPDATED_OVER_ALL_SELLER_RATING)
            .overAllSellerReview(UPDATED_OVER_ALL_SELLER_REVIEW)
            .overAllDeliveryRating(UPDATED_OVER_ALL_DELIVERY_RATING)
            .overAllDeliveryReview(UPDATED_OVER_ALL_DELIVERY_REVIEW)
            .reviewAsAnonymous(UPDATED_REVIEW_AS_ANONYMOUS)
            .completedReview(UPDATED_COMPLETED_REVIEW)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(updatedReviews);

        restReviewsMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isOk());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
        Reviews testReviews = reviewsList.get(reviewsList.size() - 1);
        assertThat(testReviews.getReviewerName()).isEqualTo(UPDATED_REVIEWER_NAME);
        assertThat(testReviews.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testReviews.getReviewDate()).isEqualTo(UPDATED_REVIEW_DATE);
        assertThat(testReviews.getOverAllSellerRating()).isEqualTo(UPDATED_OVER_ALL_SELLER_RATING);
        assertThat(testReviews.getOverAllSellerReview()).isEqualTo(UPDATED_OVER_ALL_SELLER_REVIEW);
        assertThat(testReviews.getOverAllDeliveryRating()).isEqualTo(UPDATED_OVER_ALL_DELIVERY_RATING);
        assertThat(testReviews.getOverAllDeliveryReview()).isEqualTo(UPDATED_OVER_ALL_DELIVERY_REVIEW);
        assertThat(testReviews.isReviewAsAnonymous()).isEqualTo(UPDATED_REVIEW_AS_ANONYMOUS);
        assertThat(testReviews.isCompletedReview()).isEqualTo(UPDATED_COMPLETED_REVIEW);
        assertThat(testReviews.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testReviews.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingReviews() throws Exception {
        int databaseSizeBeforeUpdate = reviewsRepository.findAll().size();

        // Create the Reviews
        ReviewsDTO reviewsDTO = reviewsMapper.toDto(reviews);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReviewsMockMvc.perform(put("/api/reviews")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reviewsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reviews in the database
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReviews() throws Exception {
        // Initialize the database
        reviewsRepository.saveAndFlush(reviews);

        int databaseSizeBeforeDelete = reviewsRepository.findAll().size();

        // Delete the reviews
        restReviewsMockMvc.perform(delete("/api/reviews/{id}", reviews.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reviews> reviewsList = reviewsRepository.findAll();
        assertThat(reviewsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reviews.class);
        Reviews reviews1 = new Reviews();
        reviews1.setId(1L);
        Reviews reviews2 = new Reviews();
        reviews2.setId(reviews1.getId());
        assertThat(reviews1).isEqualTo(reviews2);
        reviews2.setId(2L);
        assertThat(reviews1).isNotEqualTo(reviews2);
        reviews1.setId(null);
        assertThat(reviews1).isNotEqualTo(reviews2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReviewsDTO.class);
        ReviewsDTO reviewsDTO1 = new ReviewsDTO();
        reviewsDTO1.setId(1L);
        ReviewsDTO reviewsDTO2 = new ReviewsDTO();
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
        reviewsDTO2.setId(reviewsDTO1.getId());
        assertThat(reviewsDTO1).isEqualTo(reviewsDTO2);
        reviewsDTO2.setId(2L);
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
        reviewsDTO1.setId(null);
        assertThat(reviewsDTO1).isNotEqualTo(reviewsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reviewsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reviewsMapper.fromId(null)).isNull();
    }
}
