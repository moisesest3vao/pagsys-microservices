package br.com.pagsys.payment.dto;

import br.com.pagsys.payment.enums.PurchaseVerificationResult;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PurchaseVerificationResultDto {
    private PurchaseVerificationResult result;
    private BigDecimal totalPrice;
    private Long purchaseId;



}
