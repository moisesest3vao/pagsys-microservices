package br.com.pagsys.payment.enums;

public enum EmailType {
    SUCCESS_PURCHASE("SUCCESS_PURCHASE");

    public String value;

    EmailType(String value) {
        this.value = value;
    }
}
