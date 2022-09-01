package br.com.pagsys.msinventory.model;

import br.com.pagsys.msinventory.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
    private Long amount;

}
