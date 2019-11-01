package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                                            @RequestParam(defaultValue = "1") int pageNumber,
                                            @RequestParam(defaultValue = "5") int pageSize) {
        return orderService.getOrdersByUserID(userID, pageNumber, pageSize);
    }


}
