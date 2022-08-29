package br.com.pagsys.payment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PurchaseRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Product> product;
    private Long amount;
    private BigDecimal totalPrice;
    @ManyToOne
    private AuthUser user;
}
