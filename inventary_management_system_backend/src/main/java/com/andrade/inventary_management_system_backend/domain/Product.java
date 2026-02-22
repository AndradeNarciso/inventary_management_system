package com.andrade.inventary_management_system_backend.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true, nullable = false)
    private String sku; // Stock Keeping Unit

    @Positive
    private BigDecimal price;

    @Column(name = "quant_stock")
    @Min(value = 0)
    private Integer quantStock;

    private String description;

    @Column(name = "expire_date")
    private LocalDateTime expireDate;

    @Column(name = "created_at")
    private final Instant createdAt = Instant.now();

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @Override
    public String toString() {
        return "Product [id=" + id
                + ", name=" + name
                + ", sku=" + sku
                + ", price=" + price
                + ", quantStock=" + quantStock
                + ", description=" + description
                + ", expireDate=" + expireDate
                + ", createdAt=" + createdAt
                + ", imageUrl=" + imageUrl
                + "]";
    }

}
