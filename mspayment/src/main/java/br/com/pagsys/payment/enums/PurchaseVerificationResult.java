package br.com.pagsys.payment.enums;

public enum PurchaseVerificationResult {
    DENIED("DENIED"), APPROVED("APPROVED");
    public final String value;
    PurchaseVerificationResult(String value) {
        this.value = value;
    }
}
