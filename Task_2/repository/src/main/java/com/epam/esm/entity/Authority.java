package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "authorities")
@Table
public class Authority extends AbstractEntity implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "authoritySequence", sequenceName = "authoritires_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authoritySequence")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Integer userID;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "userstatus", nullable = false)
    private UserStatus userStatus;

    private Authority(AuthorityBuilder builder) {
        this.userID = builder.userID;
        this.userStatus = builder.userStatus;
    }

    public Authority() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.userStatus.toString();
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
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
        return Objects.equals(id, authority.id) &&
                Objects.equals(userID, authority.userID) &&
                userStatus == authority.userStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userID, userStatus);
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", userID=" + userID +
                ", userStatus=" + userStatus +
                '}';
    }

    public static class AuthorityBuilder {

        private final Integer userID;
        private final UserStatus userStatus;

        public AuthorityBuilder(Integer userID, UserStatus status) {
            this.userID = userID;
            this.userStatus = status;
        }

        public Authority getResult() {
            return new Authority(this);
        }
    }
}
