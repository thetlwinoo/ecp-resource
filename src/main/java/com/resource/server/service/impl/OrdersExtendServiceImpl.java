package com.resource.server.service.impl;

import com.resource.server.domain.*;
import com.resource.server.repository.AddressesRepository;
import com.resource.server.repository.CustomersExtendRepository;
import com.resource.server.repository.OrdersExtendRepository;
import com.resource.server.repository.PeopleExtendRepository;
import com.resource.server.service.OrdersExtendService;
import com.resource.server.service.dto.OrdersDTO;
import com.resource.server.service.mapper.OrdersMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class OrdersExtendServiceImpl implements OrdersExtendService {

    private final Logger log = LoggerFactory.getLogger(OrdersExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendedRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final AddressesRepository addressesRepository;
    private final OrdersMapper ordersMapper;

    public OrdersExtendServiceImpl(PeopleExtendRepository peopleExtendedRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, AddressesRepository addressesRepository, OrdersMapper ordersMapper) {
        this.peopleExtendedRepository = peopleExtendedRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.addressesRepository = addressesRepository;
        this.ordersMapper = ordersMapper;
    }

    @Override
    public Integer getAllOrdersCount(Principal principal) {
        People people = getUserFromPrinciple(principal);
        Customers customer = customersExtendRepository.findCustomersByPersonId(people.getId());
        return ordersExtendRepository.countAllByCustomerId(customer.getId());
    }

    @Override
    public OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO) {
        People people = getUserFromPrinciple(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        Customers customer = customersExtendRepository.findCustomersByPersonId(people.getId());

        Set<ShoppingCartItems> cartItems = cart.getCartItemLists();

        Orders saveOrder = new Orders();

        Calendar calendar = Calendar.getInstance();
        saveOrder.setOrderDate(LocalDate.now());
        saveOrder.setDueDate(LocalDate.now());
        saveOrder.setShipDate(LocalDate.now());
        Addresses billToAddress = addressesRepository.getOne(ordersDTO.getBillToAddressId());
        saveOrder.setBillToAddress(billToAddress);
        Addresses shipToAddress = addressesRepository.getOne(ordersDTO.getShipToAddressId());
        saveOrder.setShipToAddress(shipToAddress);

        for (ShoppingCartItems i : cartItems) {
            //increase sell count on the product
            i.getStockItem().setSellCount(i.getStockItem().getSellCount() + i.getQuantity());

            OrderLines orderLines = new OrderLines();
            orderLines.setQuantity(i.getQuantity());
            orderLines.setOrder(saveOrder);
            orderLines.setStockItem(i.getStockItem());
            orderLines.setUnitPrice(i.getStockItem().getUnitPrice());
            saveOrder.getOrderLineLists().add(orderLines);
        }

        saveOrder.setPaymentStatus(0);
        saveOrder.setOrderFlag(0);
        saveOrder.setFrieight(cart.getTotalCargoPrice());
        saveOrder.setCustomer(customer);
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
        saveOrder.setTotalDue(cart.getTotalPrice());
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
//        Long nextSerial = ordersExtendRepository.getNextSeriesId();

        saveOrder = ordersExtendRepository.save(saveOrder);
        saveOrder.setOrderNumber("SO" + saveOrder.getId());
        ordersExtendRepository.save(saveOrder);
        return ordersMapper.toDto(saveOrder);
    }

    private People getUserFromPrinciple(Principal principal) {
        if (principal == null || principal.getName() == null) {
            throw new IllegalArgumentException("Invalid access");
        }

        Optional<People> people = peopleExtendedRepository.findPeopleByLogonName(principal.getName());
        if (!people.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }
        return people.get();
    }
}
