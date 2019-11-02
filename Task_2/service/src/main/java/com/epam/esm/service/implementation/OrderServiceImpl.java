package com.epam.esm.service.implementation;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllOrdersByUserID;
import com.epam.esm.repository.specification.FindOrderByID;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.repository.specification.FindUserOrderByOrderID;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public OrderDTO getByID(Long id) {
        Order orderFound = orderRepository.queryEntity(new FindOrderByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Order with this id doesn't exist!"));
        return orderConverter.toDTO(orderFound);
    }

    @Override
    public List<com.epam.esm.dto.OrderDTO> getAll(int pageNumber, int size) {
        /*try {
            List<Order> orderList = orderRepository.queryList(new FindAllOrders(), pageNumber, size);
            return orderConverter.toDTOList(orderList);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find Orders");
        }*/
        return null;
    }

    public List<OrderDTO> getOrdersByUserID(Long userID, int pageNumber, int pageSize) {
        try {
            List<Order> orders = orderRepository.queryList(new FindAllOrdersByUserID(userID), pageNumber, pageSize);
            List<OrderDTO> orderDTOS = orderConverter.toDTOList(orders);
            return orderDTOS;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find orders");
        }
    }

    @Override
    public OrderDTO getUserOrder(Long userID, Long orderID) {
        User userfound = userRepository.queryEntity(new FindUserByUserID(userID))
                .orElseThrow(() -> new ResourceNotFoundException("User with this " +
                        "id was not found"));
        Order order = orderRepository.queryEntity(new FindUserOrderByOrderID(userID, orderID)).
                orElseThrow(() -> new ResourceNotFoundException("Order with this id was not found"));
        OrderDTO orderDTO = orderConverter.toDTO(order);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO add(Long userID, OrderDTO dto) {
        Order order = orderConverter.toEntity(dto);//
        User user = userRepository.queryEntity(new FindUserByUserID(userID)).
                orElseThrow(() -> new ResourceNotFoundException("User with this id was not found"));//
        order.setTimestamp(LocalDateTime.now());
        Order orderAdded = orderRepository.add(order);
        user.getOrderList().add(orderAdded);
        userRepository.update(user);
        return orderConverter.toDTO(orderAdded);
    }

    @Override
    public boolean delete(OrderDTO dto) {
        return false;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public com.epam.esm.dto.OrderDTO update(com.epam.esm.dto.OrderDTO dto) {
        return null;
    }

    @Override
    public com.epam.esm.dto.OrderDTO patch(Map<Object, Object> fields, Long id) {
        com.epam.esm.dto.OrderDTO oldOrder = getByID(id);
        /*if (oldOrder == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + id + " was not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(com.epam.esm.dto.OrderDTO.class, (String) k);
            ReflectionUtils.setField(field, oldOrder, v);
        });
        return update(oldOrder);*/
        return null;
    }
}
