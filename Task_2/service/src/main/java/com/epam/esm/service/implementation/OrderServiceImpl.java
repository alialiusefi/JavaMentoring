package com.epam.esm.service.implementation;

import com.epam.esm.converter.AddOrderConverter;
import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.AddOrderDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.entity.*;
import com.epam.esm.exception.OAuth2AuthenticationProcessingException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.*;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderConverter orderConverter;
    private AddOrderConverter addOrderConverter;
    private GiftCertificateRepository giftCertificateRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            OrderConverter orderConverter, AddOrderConverter addOrderConverter,
                            GiftCertificateRepository giftCertificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderConverter = orderConverter;
        this.addOrderConverter = addOrderConverter;
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @Override
    public OrderDTO getByID(Long id) {
        Order orderFound = orderRepository.queryEntity(new FindOrderByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Order with this id doesn't exist!"));
        return orderConverter.toDTO(orderFound);
    }

    @Override
    public List<OrderDTO> getAll(int pageNumber, int size) {
        try {
            List<Order> orderList = orderRepository.queryList(new FindAllOrders(), pageNumber, size);
            return orderConverter.toDTOList(orderList);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find Orders");
        }
    }

    public List<OrderDTO> getOrdersByUserID(CustomOAuthUser currentUserEntity, Long userIDOrders,
                                            int pageNumber, int pageSize) {
        UserEntity userEntity = this.userRepository.queryEntity(new FindUserByUserID(userIDOrders))
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find user " + userIDOrders));
        List<Authority> authorities = (List<Authority>) currentUserEntity.getAuthorities();
        boolean isAdmin = false;
        for (Authority i : authorities) {
            if (i.getUserStatus() == UserStatus.ADMIN) {
                isAdmin = true;
                break;
            }
        }
        if (currentUserEntity.getUserEntity().getId().equals(userIDOrders) || isAdmin) {
            try {
                List<Order> orders = orderRepository.queryList(new FindAllOrdersByUserID(userIDOrders),
                        pageNumber, pageSize);
                List<OrderDTO> orderDTOS = orderConverter.toDTOList(orders);
                return orderDTOS;
            } catch (EmptyResultDataAccessException e) {
                throw new ResourceNotFoundException("Didn't find orders");
            }
        }
        throw new OAuth2AuthenticationProcessingException("Access is denied!");
    }

    public PageDTO getOrdersByUserIDPage(CustomOAuthUser currentUserEntity, Long userIDOrders,
                                         int pageNumber, int pageSize) {
        List dtos = getOrdersByUserID(currentUserEntity, userIDOrders, pageNumber, pageSize);
        Long resultCount = orderRepository.queryCount(new CountFindAllUserOrders(currentUserEntity.getUserEntity().getId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cannot Find User's Orders Certificates"));
        return new PageDTO(dtos,resultCount);
  }


    @Override
    public OrderDTO getUserOrder(Long userID, Long orderID) {
        UserEntity userfound = userRepository.queryEntity(new FindUserByUserID(userID))
                .orElseThrow(() -> new ResourceNotFoundException("User with this " +
                        "id was not found"));
        Order order = orderRepository.queryEntity(new FindUserOrderByOrderID(userID, orderID)).
                orElseThrow(() -> new ResourceNotFoundException("Order with this id was not found"));
        OrderDTO orderDTO = orderConverter.toDTO(order);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO add(Long userID, AddOrderDTO dto) {
        Order order = addOrderConverter.toEntity(dto);//
        order.setGiftCertificates(null);
        UserEntity userEntity = userRepository.queryEntity(new FindUserByUserID(userID)).
                orElseThrow(() -> new ResourceNotFoundException("User with this id was not found"));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (Long giftCertificateID : dto.getGiftCertificates()) {
            GiftCertificate giftCertificateFound = giftCertificateRepository.queryEntity(
                    new FindGiftCertificateByID(giftCertificateID)).orElseThrow(() -> new ResourceNotFoundException
                    ("Couldn't find giftcertificate with id: " + giftCertificateID));
            giftCertificates.add(giftCertificateFound);
        }
        order.setTimestamp(LocalDateTime.now());
        order.setUserEntity(userEntity);
        double sum = 0.0;
        for (GiftCertificate i : giftCertificates) {
            sum += i.getPrice().doubleValue();
        }
        order.setOrderCost(BigDecimal.valueOf(sum));
        order.setGiftCertificates(giftCertificates);
        order.setUserEntity(userEntity);
        Order orderAdded = orderRepository.add(order);
        /*order.setGiftCertificates(giftCertificates);
        orderAdded = orderRepository.update(orderAdded);
        userEntity.getOrders().add(orderAdded);
        userEntity = userRepository.update(userEntity);
        */
        return orderConverter.toDTO(orderAdded);
    }

    @Transactional
    @Override
    public boolean delete(OrderDTO dto) {
        if (getByID(dto.getId()) == null) {
            throw new ResourceNotFoundException("Order with ID: "
                    + dto.getId() + " was not found!");
        }
        orderRepository.delete(orderConverter.toEntity(dto));
        return true;
    }

    @Transactional
    @Override
    public boolean delete(long id) {
        if (getByID(id) == null) {
            throw new ResourceNotFoundException("Order with ID: "
                    + id + " was not found!");
        }
        orderRepository.delete(new FindOrderByID(id));
        return true;
    }

    @Override
    @Transactional
    public OrderDTO update(OrderDTO dto) {
        OrderDTO oldOrder;
        if ((oldOrder = getByID(dto.getId())) == null) {
            throw new ResourceNotFoundException("Order with ID: "
                    + dto.getId() + " was not found!");
        }
        Order order = orderConverter.toEntity(dto);
        order.setOrderCost(oldOrder.getOrderCost());
        return orderConverter.toDTO(orderRepository.update(order));
    }

}
