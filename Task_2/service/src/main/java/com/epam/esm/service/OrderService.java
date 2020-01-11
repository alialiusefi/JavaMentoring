package com.epam.esm.service;

import com.epam.esm.dto.AddOrderDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.PageDTO;
import com.epam.esm.entity.CustomOAuthUser;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService<OrderDTO> {

    @Override
    default OrderDTO add(OrderDTO orderDTO) {
        throw new UnsupportedOperationException("Wrong method called!");
    }

    OrderDTO add(Long userID, AddOrderDTO orderDTO);

    List<OrderDTO> getOrdersByUserID(CustomOAuthUser currentUser, Long userIDOrders, int pageNumber, int pageSize);

    PageDTO getOrdersByUserIDPage(CustomOAuthUser currentUser, Long userIDOrders, int pageNumber, int pageSize);

    OrderDTO getUserOrder(Long userID, Long orderID);

    default OrderDTO patch(Map<Object, Object> fields, Long id) {
        throw new UnsupportedOperationException("Unimplemented method!");
    }
}
