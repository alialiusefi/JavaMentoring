package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;

import java.util.List;

public interface OrderService extends BaseService<OrderDTO> {

    @Override
    default OrderDTO add(OrderDTO orderDTO) {
        throw new UnsupportedOperationException("Wrong method called!");
    }

    OrderDTO add(Long userID, OrderDTO orderDTO);

    List<OrderDTO> getOrdersByUserID(Long userID, int pageNumber, int pageSize);

    OrderDTO getUserOrder(Long userID, Long orderID);
}
