package com.jobportal.account.domain;

import com.jobportal.common.domain.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Entity
@Table(name = "user_accounts")
@NoArgsConstructor
@Getter
@Setter
public class UserAccount extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false,name = "password_hash")
    private String passwordHash;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(name = "email_verified")
    private boolean emailVerified;


    @Column(name = "last_login_at")
    private Instant lastLoginAt;

    public void setEmail(String email){

        this.email =email == null ? null : email.toLowerCase();
    }
}
