package me.escoffier.quarkus.reactive;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Transaction {

    double amount;

    String account;

    long date;

    String kind;

    public double getAmount() {
        return amount;
    }

    public Transaction setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Transaction setAccount(String account) {
        this.account = account;
        return this;
    }

    public long getDate() {
        return date;
    }

    public Transaction setDate(long date) {
        this.date = date;
        return this;
    }

    public String getKind() {
        return kind;
    }

    public Transaction setKind(String kind) {
        this.kind = kind;
        return this;
    }
}
