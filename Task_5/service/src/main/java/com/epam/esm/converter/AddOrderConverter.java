package com.epam.esm.converter;

import com.epam.esm.dto.AddOrderDTO;
import com.epam.esm.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddOrderConverter extends BaseConverter implements Converter<AddOrderDTO, Order> {

    @Autowired
    protected AddOrderConverter(ModelMapper modelMapper) {
        super(modelMapper);
    }

    @Override
    public Order toEntity(AddOrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);

    }

    @Override
    public AddOrderDTO toDTO(Order entity) {
        return modelMapper.map(entity, AddOrderDTO.class);
    }


    @Override
    public List<Order> toEntityList(List<AddOrderDTO> dto) {
        return dto.stream().map(this::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddOrderDTO> toDTOList(List<Order> entities) {
        return entities.stream().map(this::toDTO)
                .collect(Collectors.toList());
    }
}
