package br.com.pagsys.payment.dto;

import br.com.pagsys.payment.model.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
public class PurchaseDto {

    public Long id;
    @NotNull
    @NotEmpty
    public List<String> products;
    @NotNull
    @NotEmpty
    public BigDecimal totalPrice;
    public String user;

    public PurchaseDto(Purchase model) {
        this.id = model.getId();
        this.totalPrice = model.getTotalPrice();
        this.products = model.getProducts();
        this.user = model.getUser();
    }
}
