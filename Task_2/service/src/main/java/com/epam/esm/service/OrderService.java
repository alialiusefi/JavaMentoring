package com.epam.esm.service;

import com.epam.esm.dto.OrderDTO;

import java.util.List;

public interface OrderService extends BaseService<OrderDTO> {

    List<OrderDTO> getOrdersByUserID(Long userID, int pageNumber, int pageSize);
}
