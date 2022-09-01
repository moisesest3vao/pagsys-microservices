package br.com.pagsys.payment.enums;

public enum PurchaseStatus {
    FINISHED("FINISHED"),
    IN_PROCESSING("IN_PROCESSING");
    private final String value;

    PurchaseStatus(String value) {
        this.value = value;
    }
}
