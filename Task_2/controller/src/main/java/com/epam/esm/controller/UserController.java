package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v2/users")
@RestController
@Validated
public class UserController {

    private UserService userService;
    private OrderService orderService;

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/{userID}/orders")
    public List<OrderDTO> getOrdersByUserID(@PathVariable Long userID,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        return orderService.getOrdersByUserID(userID, page, size);
    }

    @GetMapping("/{userID}/orders/{orderID}")
    public OrderDTO getUserOrder(@PathVariable @Valid Long userID,
                                 @PathVariable @Valid Long orderID) {
        return orderService.getUserOrder(userID, orderID);
    }

    @PostMapping(value = "/{userID}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO saveUserOrder(@RequestBody @Valid OrderDTO orderDTO,
                                  @PathVariable Long userID) {
        return orderService.add(userID, orderDTO);
    }

}
