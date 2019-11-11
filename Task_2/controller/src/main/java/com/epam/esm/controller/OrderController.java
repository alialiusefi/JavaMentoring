package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public OrderDTO getOrder(@PathVariable Long orderID) {
        return orderService.getByID(orderID);
    }

    @GetMapping()
    public List<OrderDTO> getAllOrders(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "5") Integer size) {
        return (List<OrderDTO>) orderService.getAll(page, size);
    }

    @PatchMapping("/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDTO patchOrder(@RequestBody Map<Object, Object> fields,
                               @PathVariable Long orderID) {
        return orderService.patch(fields, orderID);
    }

    @PutMapping("/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDTO updateOrder(@RequestBody OrderDTO orderDTO, Long orderID) {
        orderDTO.setId(orderID);
        return orderService.update(orderDTO);
    }

    @DeleteMapping("/{orderID}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderID) {
        orderService.delete(orderID);
    }

}
