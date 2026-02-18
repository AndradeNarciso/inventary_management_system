package com.andrade.inventary_management_system_backend.domain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.andrade.inventary_management_system_backend.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_at")
    private final Instant createdAt = Instant.now();

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Override
    public String toString() {
        return "User [id=" + id
                + ", name=" + name
                + ", email=" + email
                + ", password=" + password
                + ", role=" + role
                + ", phoneNumber=" + phoneNumber
                + ", createdAt=" + createdAt + "]";
    }

}
