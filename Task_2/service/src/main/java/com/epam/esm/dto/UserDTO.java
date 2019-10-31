package com.epam.esm.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    //todo: should i add this list?
    /*private List<Authority> authorityList;*/

    public UserDTO(Long id, @NotBlank @Size(min = 6, max = 50) String username,
                   @NotBlank @Size(min = 6, max = 50) String password, boolean enabled) {
        super(id);
        this.username = username;
        this.password = password;
        this.enabled = enabled;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return isEnabled() == userDTO.isEnabled() &&
                Objects.equals(getUsername(), userDTO.getUsername()) &&
                Objects.equals(getPassword(), userDTO.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUsername(), getPassword(), isEnabled());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDTO{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
