package com.andrade.inventary_management_system_backend.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import com.andrade.inventary_management_system_backend.enums.TransactionStatus;
import com.andrade.inventary_management_system_backend.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "total_product")
    private Integer totalProduct;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    private String description;
    private String note;

    @Column(name = "created-at")
    private final Instant createdAt = Instant.now();

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_supllier")
    private Supplier supplier;

    @Override
    public String toString() {
        return "Transaction [id=" + id
                + ", totalProduct=" + totalProduct
                + ", totalPrice=" + totalPrice
                + ", transactionType=" + transactionType
                + ", transactionStatus=" + transactionStatus
                + ", description=" + description
                + ", note=" + note
                + ", createdAt=" + createdAt
                + ", updateAt=" + updateAt
                + "]";
    }

}
