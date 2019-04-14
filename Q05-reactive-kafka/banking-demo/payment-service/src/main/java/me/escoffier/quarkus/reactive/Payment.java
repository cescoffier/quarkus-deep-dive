package me.escoffier.quarkus.reactive;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class Payment {

    @Positive
    double amount;

    @NotBlank
    String account;

    long date = System.currentTimeMillis();

    String kind = "payment";

    public double getAmount() {
        return amount;
    }

    public Payment setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Payment setAccount(String account) {
        this.account = account;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Payment setDate(long date) {
        this.date = date;
        return this;
    }

    public String getKind() {
        return kind;
    }

    public Payment setKind(String kind) {
        this.kind = kind;
        return this;
    }
}
