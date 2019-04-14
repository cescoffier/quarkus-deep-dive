package me.escoffier.quarkus.reactive;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.ArrayList;
import java.util.List;

@RegisterForReflection
public class Fraud {


    private List<Transaction> transactions;
    private String account;
    private double amount;

    public Fraud() {
        // Use by mappers
    }

    public Fraud(List<Transaction> transactions) {
        this.transactions = new ArrayList<>(transactions);
        this.account = transactions.get(0).getAccount();
        this.amount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Fraud setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Fraud setAccount(String account) {
        this.account = account;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public Fraud setAmount(double amount) {
        this.amount = amount;
        return this;
    }
}
