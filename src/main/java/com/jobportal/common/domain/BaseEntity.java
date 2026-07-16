package com.jobportal.common.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @CreatedBy
    private Long id;

    @CreatedBy
    private Instant createdAt;

    @LastModifiedBy
    private Instant updatedAt;

    @CreatedBy
    private Long version;
}
