package com.epam.esm.controller;

import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{userID}")
    public UserDTO getUser(@PathVariable Long userID) {
        return userService.getByID(userID);
    }

    @GetMapping()
    public List<UserDTO> getAllUsers(@RequestParam Integer page, @RequestParam Integer size) {
        return userService.getAll(page, size);
    }

    @PutMapping(value = "/{userID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO updateUser(@PathVariable Long userID,
                              @Valid UserDTO userDTO) {
        userDTO.setId(userID);
        return userService.update(userDTO);
    }

    @PatchMapping(value = "/{userID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public UserDTO patchUser(@PathVariable Long userID,
                             @RequestBody Map<Object, Object> fields) {
        return userService.patch(fields, userID);
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
