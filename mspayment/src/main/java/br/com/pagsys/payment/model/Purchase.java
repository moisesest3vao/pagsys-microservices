package br.com.pagsys.payment.model;

import br.com.pagsys.payment.converter.StringListConverter;
import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.enums.PurchaseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String user;
    private String userEmail;
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;

    public Purchase(List<String> products, String user, String email) {
        this.products = products;
        this.user = user;
        this.status = PurchaseStatus.IN_PROCESSING;
        this.userEmail = email;
    }

    public static Purchase build(PurchaseDto dto, String userId, String userEmail) {
        return new Purchase(
                dto.getProducts(),
                userId,
                userEmail
        );
    }


}
