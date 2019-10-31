package com.epam.esm.service.implementation;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.specification.FindOrderByID;
import com.epam.esm.repository.specification.FindTagByID;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public OrderDTO getByID(long id) {
        Order orderFound = orderRepository.queryEntity(new FindOrderByID(id)).orElseThrow(() ->
                new ResourceNotFoundException("Order with this id doesn't exist!"));
        return tagConverter.toDTO(orderFound);
    }

    @Override
    public List<OrderDTO> getAll(int pageNumber, int size) {
        return null;
    }

    @Override
    public OrderDTO add(OrderDTO dto) {
        return null;
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
    public OrderDTO update(OrderDTO dto) {
        return null;
    }
}
