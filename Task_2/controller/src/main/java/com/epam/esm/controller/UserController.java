package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/v2/users")
@RestController
@Validated
public class UserController {

    private UserService userService;
    private OrderService orderService;
    private TagService tagService;

    @Autowired
    public UserController(UserService userService, OrderService orderService,
                          TagService tagService) {
        this.userService = userService;
        this.orderService = orderService;
        this.tagService = tagService;
    }

    @GetMapping("/{userID}")
    public UserDTO getUser(@PathVariable Long userID) {
        return userService.getByID(userID);
    }

    @GetMapping()
    public List<UserDTO> getAllUsers(@RequestParam Integer page, @RequestParam Integer size) {
        return (List<UserDTO>) userService.getAll(page, size);
    }

    @GetMapping("/{userID}/tags")
    public TagDTO getPopularTag(@PathVariable Long userID, @RequestParam boolean popular) {
        return tagService.getMostUsedTagOfMostExpensiveUserOrders(userID);
    }


    @PutMapping(value = "/{userID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO updateUser(@PathVariable Long userID,
                              @Valid UserDTO userDTO) {
        userDTO.setId(userID);
        return userService.update(userDTO);
    }


    @DeleteMapping("/{userID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long userID) {
        userService.delete(userID);
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
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public OrderDTO saveUserOrder(@RequestBody @Valid OrderDTO orderDTO,
                                  @PathVariable Long userID) {
        return orderService.add(userID, orderDTO);
    }

}
