package com.epam.esm.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity(name = "authorities")
@Table
public class Authority extends AbstractEntity implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "authoritySequence", sequenceName = "authorities_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authoritySequence")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "userstatus", nullable = false)
    private UserStatus userStatus;

    private Authority(AuthorityBuilder builder) {
        this.userEntity = builder.userEntity1;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String getAuthority() {
        return this.userStatus.toString();
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
                Objects.equals(userEntity, authority.userEntity) &&
                userStatus == authority.userStatus;
    }

    public static class AuthorityBuilder {

        private final UserEntity userEntity1;
        private final UserStatus userStatus;

        public AuthorityBuilder(UserEntity userEntity1, UserStatus status) {
            this.userEntity1 = userEntity1;
            this.userStatus = status;
        }

        public Authority getResult() {
            return new Authority(this);
        }
    }
}
