package br.com.pagsys.msinventory.dto;

import br.com.pagsys.msinventory.enums.PurchaseVerificationResult;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PurchaseVerificationResultDto {
    private PurchaseVerificationResult result;
    private BigDecimal totalPrice;
    private Long purchaseId;

    public PurchaseVerificationResultDto( BigDecimal totalPrice, Long id) {
        this.purchaseId = id;
        this.totalPrice = totalPrice;
    }
}
