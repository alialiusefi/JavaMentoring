package com.epam.esm.dto;

import com.epam.esm.entity.Order;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Validated
public class UserDTO extends DTO {

    @NotBlank
    @Size(min = 6, max = 50)
    private String username;
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    private boolean enabled;

    private List<Order> orders;

    //todo: should i add this list?
    /*private List<Authority> authorityList;*/

    public UserDTO(Long id, @NotBlank @Size(min = 6, max = 50) String username,
                   @NotBlank @Size(min = 6, max = 50) String password, boolean enabled, List<Order> orders) {
        super(id);
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.orders = orders;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return enabled == userDTO.enabled &&
                Objects.equals(username, userDTO.username) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(orders, userDTO.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, password, enabled, orders);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", orders=" + orders +
                '}';
    }
}
