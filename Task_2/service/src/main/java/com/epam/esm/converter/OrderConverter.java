package com.epam.esm.converter;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.Order;

import java.util.List;

public class OrderConverter extends BaseConverter implements Converter<OrderDTO, Order> {
    @Override
    public Order toEntity(OrderDTO dto) {
        return null;
    }

    @Override
    public OrderDTO toDTO(Order entity) {
        return null;
    }

    @Override
    public List<Order> toEntityList(List<OrderDTO> dto) {
        return null;
    }

    @Override
    public List<OrderDTO> toDTOList(List<Order> entities) {
        return null;
    }
}
