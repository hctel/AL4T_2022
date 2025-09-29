package be.ecam.basics.exercises;

import java.util.Objects;

public class Account {
    private long balance;

    public Account() {
        this(0);
    }

    public Account(double initial) {
        this.balance = Math.round(initial*100);
    }

    public double getBalance() {
        return balance/100.0;
    }

    public void deposit(double amount) {
        if (amount < 0) throw new IllegalArgumentException("amount");
        balance += Math.round(amount*100);
    }

    public void withdraw(double amount) {
        if (amount < 0) throw new IllegalArgumentException("amount");
        if (Math.round(amount*100) > balance) throw new IllegalStateException("insufficient");
        balance -= Math.round(amount*100);
    }

    public void transferTo(Account other, double amount) {
        Objects.requireNonNull(other, "other");
        withdraw(amount);
        other.deposit(amount);
    }
}
