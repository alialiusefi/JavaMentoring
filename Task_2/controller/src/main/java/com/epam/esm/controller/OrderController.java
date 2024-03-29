package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/v2/orders")
@RestController
@Validated
public class OrderController {

    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDTO getOrder(@PathVariable Long orderID) {
        return orderService.getByID(orderID);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderDTO> getAllOrders(@RequestParam(defaultValue = "${app.pagedefault.defaultPageNumber}") Integer page,
                                       @RequestParam(defaultValue = "${app.pagedefault.defaultPageSize}") Integer size) {
        return orderService.getAll(page, size);
    }

    @PutMapping("/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, Long orderID) {
        orderDTO.setId(orderID);
        return orderService.update(orderDTO);
    }

    @DeleteMapping("/{orderID}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderID) {
        orderService.delete(orderID);
    }

}
