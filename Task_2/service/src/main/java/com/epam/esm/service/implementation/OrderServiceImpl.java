package com.epam.esm.service.implementation;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.specification.FindAllOrders;
import com.epam.esm.repository.specification.FindAllOrdersByUserID;
import com.epam.esm.repository.specification.FindOrderByID;
import com.epam.esm.repository.specification.FindUserByUserID;
import com.epam.esm.repository.specification.FindUserOrderByOrderID;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.math.BigDecimal;
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
    public List<OrderDTO> getAll(int pageNumber, int size) {
        try {
            List<Order> orderList = orderRepository.queryList(new FindAllOrders(), pageNumber, size);
            return orderConverter.toDTOList(orderList);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find Orders");
        }
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
    public OrderDTO add(Long userID, OrderDTO dto) {
        Order order = orderConverter.toEntity(dto);//
        UserEntity userEntity = userRepository.queryEntity(new FindUserByUserID(userID)).
                orElseThrow(() -> new ResourceNotFoundException("User with this id was not found"));//
        order.setTimestamp(LocalDateTime.now());
        Order orderAdded = orderRepository.add(order);
        userEntity.getOrderList().add(orderAdded);
        userRepository.update(userEntity);
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
        if (getByID(dto.getId()) == null) {
            throw new ResourceNotFoundException("Order with ID: "
                    + dto.getId() + " was not found!");
        }
        Order order = orderConverter.toEntity(dto);
        return orderConverter.toDTO(orderRepository.update(order));
    }

    @Transactional
    @Override
    public OrderDTO patch(Map<Object, Object> fields, Long id) {
        Order order = orderRepository.queryEntity(
                new FindOrderByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Order with this id doesn't exist!"));
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Order.class, (String) k);
            field.setAccessible(true);
            if (!handleBigDecimal(field, order, v)) {
                ReflectionUtils.setField(field, order, v);
            }
            field.setAccessible(false);
        });
        return update(orderConverter.toDTO(order));
    }

    private boolean handleBigDecimal(Field field, Order order, Object value) {
        if (field.getType().equals(BigDecimal.class)) {
            if (value instanceof Double) {
                BigDecimal bigDecimal = BigDecimal.valueOf((Double) value).setScale(
                        OrderDTO.SCALE,
                        OrderDTO.ROUNDING_MODE
                );
                ReflectionUtils.setField(field, order, bigDecimal);
                return true;
            }
        }
        return false;
    }
}
