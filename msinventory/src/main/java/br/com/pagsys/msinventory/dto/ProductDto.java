package br.com.pagsys.msinventory.dto;

import br.com.pagsys.msinventory.enums.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Category category;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Long amount;


}
