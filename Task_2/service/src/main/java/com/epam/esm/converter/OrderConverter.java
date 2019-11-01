package com.epam.esm.converter;

import com.epam.esm.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter extends BaseConverter implements Converter<com.epam.esm.dto.OrderDTO, Order> {

    @Autowired
    protected OrderConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public Order toEntity(com.epam.esm.dto.OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);

    }

    @Override
    public com.epam.esm.dto.OrderDTO toDTO(Order entity) {
        return modelMapper.map(entity, com.epam.esm.dto.OrderDTO.class);
    }


    @Override
    public List<Order> toEntityList(List<com.epam.esm.dto.OrderDTO> dto) {
        return dto.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<com.epam.esm.dto.OrderDTO> toDTOList(List<Order> entities) {
        return entities.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
