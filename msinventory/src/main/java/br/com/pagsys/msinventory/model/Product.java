package br.com.pagsys.msinventory.model;

import br.com.pagsys.msinventory.dto.ProductDto;
import br.com.pagsys.msinventory.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@Data
@Document(collection = "product")
public class Product {
    @MongoId(value = FieldType.OBJECT_ID)
    @Id
    String id;
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
