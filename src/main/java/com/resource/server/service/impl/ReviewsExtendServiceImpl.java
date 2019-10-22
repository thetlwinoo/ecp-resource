package com.resource.server.service.impl;

import com.resource.server.domain.Customers;
import com.resource.server.domain.Orders;
import com.resource.server.domain.People;
import com.resource.server.domain.Reviews;
import com.resource.server.repository.*;
import com.resource.server.service.ReviewsExtendService;
import com.resource.server.service.dto.ReviewLinesDTO;
import com.resource.server.service.dto.ReviewsDTO;
import com.resource.server.service.mapper.ReviewLinesMapper;
import com.resource.server.service.mapper.ReviewsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewsExtendServiceImpl implements ReviewsExtendService {

    private final Logger log = LoggerFactory.getLogger(ReviewsExtendServiceImpl.class);
    private final ReviewsExtendRepository reviewsExtendRepository;
    private final PeopleExtendRepository peopleExtendRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final ReviewsMapper reviewsMapper;
    private final ReviewLinesMapper reviewLinesMapper;
    private final ReviewLinesExtendRepository reviewLinesExtendRepository;

    public ReviewsExtendServiceImpl(ReviewsExtendRepository reviewsExtendRepository, PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, ReviewsMapper reviewsMapper, ReviewLinesMapper reviewLinesMapper, ReviewLinesExtendRepository reviewLinesExtendRepository) {
        this.reviewsExtendRepository = reviewsExtendRepository;
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.reviewsMapper = reviewsMapper;
        this.reviewLinesMapper = reviewLinesMapper;
        this.reviewLinesExtendRepository = reviewLinesExtendRepository;
    }

    @Override
    public ReviewsDTO save(Principal principal, ReviewsDTO reviewsDTO, Long orderId) {
        People people = getUserFromPrinciple(principal);
        reviewsDTO.setReviewerName(people.getFullName());
        reviewsDTO.setEmailAddress(people.getEmailAddress());
        reviewsDTO.setReviewDate(LocalDate.now());
        Reviews reviews = reviewsMapper.toEntity(reviewsDTO);
        reviews = reviewsExtendRepository.save(reviews);

        if (reviewsDTO.getId() == null) {
            Optional<Orders> orders = ordersExtendRepository.findById(orderId);

            if (orders.isPresent()) {
                orders.get().setOrderOnReview(reviews);
            }
        }

        return reviewsMapper.toDto(reviews);
    }

    public ReviewsDTO completedReview(Long orderId) {
        Optional<Orders> orders = ordersExtendRepository.findById(orderId);
        Reviews reviews = new Reviews();
        if (orders.isPresent()) {
            reviews = orders.get().getOrderOnReview();
            reviews.setCompletedReview(true);
            reviewsExtendRepository.save(reviews);
        }

        return reviewsMapper.toDto(reviews);
    }

    @Override
    public List<Orders> findAllOrderedProducts(Principal principal) {
        try {
            People people = getUserFromPrinciple(principal);
            Customers customer = customersExtendRepository.findCustomersByPersonId(people.getId());
            List<Orders> ordersList = ordersExtendRepository.findAllByCustomerIdOrderByLastEditedWhenDesc(customer.getId());

            List<Orders> paidOrders = new ArrayList<Orders>();
            for (Orders order : ordersList) {
                if (order.getPaymentStatus() > 0) {
                    paidOrders.add(order);
                }
            }
            return paidOrders;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public List<ReviewLinesDTO> findReviewLinesByStockItemId(Long stockItemId) {
        return reviewLinesExtendRepository.findAllByStockItemId(stockItemId).stream()
            .map(reviewLinesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        Optional<People> people = peopleExtendRepository.findPeopleByLogonName(principal.getName());
        if (!people.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return people.get();
    }
}
