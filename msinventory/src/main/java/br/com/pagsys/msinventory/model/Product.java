package br.com.pagsys.msinventory.model;

import br.com.pagsys.msinventory.dto.ProductDto;
import br.com.pagsys.msinventory.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@NoArgsConstructor
@Data
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Category category;
    private BigDecimal price;
    private Long amount;

    public Product(ProductDto dto) {
        this.name = dto.getName();
        this.category = dto.getCategory();
        this.price = dto.getPrice();
        this.amount = dto.getAmount();
    }

    public void update(ProductDto dto) {
        this.name = dto.getName();
        this.category = dto.getCategory();
        this.price = dto.getPrice();
        this.amount = dto.getAmount();
    }
}
