package com.epam.esm.service.implementation;

import com.epam.esm.converter.OrderConverter;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.specification.FindOrderByID;
import com.epam.esm.repository.specification.FindOrderByUserID;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public com.epam.esm.dto.OrderDTO getByID(long id) {
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

    public List<com.epam.esm.dto.OrderDTO> getOrdersByUserID(Long userID, int pageNumber, int pageSize) {
        try {
            return orderConverter.toDTOList(
                    orderRepository.queryList(new FindOrderByUserID(userID), pageNumber, pageSize));
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Didn't find orders");
        }
    }

    @Override
    public com.epam.esm.dto.OrderDTO add(com.epam.esm.dto.OrderDTO dto) {
        return null;
    }

    @Override
    public boolean delete(com.epam.esm.dto.OrderDTO dto) {
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
    public com.epam.esm.dto.OrderDTO patch(Map<Object, Object> fields, long id) {
        com.epam.esm.dto.OrderDTO oldOrder = getByID(id);
        if (oldOrder == null) {
            throw new ResourceNotFoundException("Gift Certificate with ID: "
                    + id + " was not found!");
        }
        fields.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(com.epam.esm.dto.OrderDTO.class, (String) k);
            ReflectionUtils.setField(field, oldOrder, v);
        });
        return update(oldOrder);
    }
}
