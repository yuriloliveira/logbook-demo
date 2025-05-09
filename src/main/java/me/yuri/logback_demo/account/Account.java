package me.yuri.logback_demo.account;

public record Account(String iban) {
    public static final String JOKER_IBAN = "CH0000000000000000000";
}
