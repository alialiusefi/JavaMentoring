package com.epam.esm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "users")
public class UserEntity extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "usersSequence", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSequence")
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;


    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Authority> authorityList;

    @OneToMany
    @JoinTable(name = "order_user", inverseJoinColumns =
            {@JoinColumn(name = "order_id")},
            joinColumns = {@JoinColumn(name = "user_id")})
    private List<Order> orders;

    private UserEntity(UserBuilder userBuilder) {
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.enabled = userBuilder.enabled;
        this.authorityList = userBuilder.authorityList;
        this.orders = userBuilder.orderList;
    }

    public UserEntity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Authority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<Authority> authorityList) {
        this.authorityList = authorityList;
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
        UserEntity userEntity = (UserEntity) o;
        return isEnabled() == userEntity.isEnabled() &&
                Objects.equals(getUsername(), userEntity.getUsername()) &&
                Objects.equals(getPassword(), userEntity.getPassword()) &&
                Objects.equals(getAuthorityList(), userEntity.getAuthorityList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), isEnabled(), getAuthorityList());
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", authorityList=" + authorityList +
                ", orderList=" + orders +
                '}';
    }

    public static class UserBuilder {

        private final String username;
        private final String password;
        private final boolean enabled;
        private List<Authority> authorityList;
        private List<Order> orderList;

        public UserBuilder(String username, String password, boolean enabled) {
            this.username = username;
            this.password = password;
            this.enabled = enabled;
        }

        public void setOrderList(List<Order> orderList) {
            this.orderList = orderList;
        }

        public UserBuilder setAuthorityList1(List<Authority> authorityList) {
            this.authorityList = authorityList;
            return this;
        }

        public UserEntity getResult() {
            return new UserEntity(this);
        }


    }

}
