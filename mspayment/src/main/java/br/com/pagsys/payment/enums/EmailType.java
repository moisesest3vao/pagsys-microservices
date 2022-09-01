package br.com.pagsys.payment.enums;

public enum EmailType {
    SUCCESS_PURCHASE("SUCCESS_PURCHASE"),
    PROCESSING_PURCHASE("PROCESSING_PURCHASE"),
    FAILED_PURCHASE("FAILED_PURCHASE");


    public final String value;

    EmailType(String value) {
        this.value = value;
    }
}
