package br.com.pagsys.msusers.enums;

public enum EmailType {
    JOINER("JOINER"), LEAVER("LEAVER");

    private String value;
    EmailType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
