package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class Authority extends AbstractEntity {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "userstatus", nullable = false)
    private UserStatus userStatus;

    private Authority(AuthorityBuilder builder) {
        this.username = builder.username;
        this.userStatus = builder.userStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authority authority = (Authority) o;
        return Objects.equals(username, authority.username) &&
                userStatus == authority.userStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userStatus);
    }

    @Override
    public String toString() {
        return "Authority{" +
                "username='" + username + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }

    public static class AuthorityBuilder {

        private final String username;
        private final UserStatus userStatus;

        public AuthorityBuilder(String username, UserStatus status) {
            this.username = username;
            this.userStatus = status;
        }

        public Authority getResult() {
            return new Authority(this);
        }
    }
}
