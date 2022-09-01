package br.com.pagsys.payment.model;

import br.com.pagsys.payment.converter.StringListConverter;
import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.dto.UserDto;
import br.com.pagsys.payment.enums.PurchaseStatus;
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
    private BigDecimal totalPrice;
    private String user;
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    public Purchase(List<String> products, BigDecimal totalPrice, String user) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.user = user;
        this.status = PurchaseStatus.IN_PROCESSING;
    }

    public static Purchase build(PurchaseDto dto, UserDto userByToken) {
        return new Purchase(
                dto.getProducts(),
                dto.getTotalPrice(),
                userByToken.getSub()
        );
    }


}
