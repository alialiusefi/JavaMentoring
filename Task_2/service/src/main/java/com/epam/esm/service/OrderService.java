package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<OrderDTO> {

    @Override
    default OrderDTO add(OrderDTO orderDTO) {
        throw new UnsupportedOperationException("Wrong method called!");
    }

    OrderDTO add(Long userID, OrderDTO orderDTO);

    List<OrderDTO> getOrdersByUserID(Long currentUserID, Long userIDOrders, int pageNumber, int pageSize);

    OrderDTO getUserOrder(Long userID, Long orderID);

    default OrderDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Unimplemented method!");
    }
}
