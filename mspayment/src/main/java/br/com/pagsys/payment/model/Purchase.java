package br.com.pagsys.payment.model;

import br.com.pagsys.payment.converter.StringListConverter;
import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.dto.UserDto;
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
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = StringListConverter.class)
    private List<String> products;
    private Long amount;
    private BigDecimal totalPrice;
    private String user;

    public Purchase(List<String> products, Long amount, BigDecimal totalPrice, String user) {
        this.products = products;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public static Purchase build(PurchaseDto dto, UserDto userByToken) {
        return new Purchase(
                dto.getProducts(),
                dto.getAmount(),
                dto.getTotalPrice(),
                userByToken.getSub()
        );
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", products=" + products +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                ", user='" + user + '\'' +
                '}';
    }
}
