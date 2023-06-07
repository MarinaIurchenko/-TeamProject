package ru.netology.javaqadiplom;

public class Bank {

    public boolean transfer(Account from, Account to, int amount) {
        if (amount <= 0) {
            return false;
        }
        if (from.pay(amount)) {
            to.add(amount);
        }
        return true;
    }
}
