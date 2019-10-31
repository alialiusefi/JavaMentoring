package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

@Table
@Entity(name = "users")
public class User extends AbstractEntity {

    @Id
    @SequenceGenerator(name = "usersSequence", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersSequence")
    private Long id;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @ManyToMany
    @JoinTable(name = "authorities", joinColumns =
            {@JoinColumn(name = "username")},
            inverseJoinColumns = {@JoinColumn(name = "authority")})
    private List<Authority> authorityList;

    @ManyToMany
    @JoinTable(name = "order_user", joinColumns =
            {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private List<Order> orderList;



    private User(UserBuilder userBuilder) {
        this.username = userBuilder.username;
        this.password = userBuilder.password;
        this.enabled = userBuilder.enabled;
        this.authorityList = userBuilder.authorityList;
        this.orderList = userBuilder.orderList;
    }

    public User() {
        super();
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

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isEnabled() == user.isEnabled() &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getAuthorityList(), user.getAuthorityList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), isEnabled(), getAuthorityList());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", enabled=").append(enabled);
        sb.append(", authorityList=").append(authorityList);
        sb.append('}');
        return sb.toString();
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

        public User getResult() {
            return new User(this);
        }


    }

}
