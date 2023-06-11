package ru.netology.javaqadiplom;

public class CreditAccount extends Account {
    protected int creditLimit;

    public CreditAccount(int initialBalance, int creditLimit, int rate) {
        if (rate <= 0) {
            throw new IllegalArgumentException("Накопительная ставка не может быть отрицательной, а у вас: " + rate
            );
        } else if (creditLimit <= 0) {
            throw new IllegalArgumentException("Кредитный лимит не может быть отрицательным, а у вас: " + creditLimit
            );
        } else if (initialBalance < 0) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным, а у вас: " + initialBalance
            );
        }
        this.balance = initialBalance;
        this.creditLimit = creditLimit;
        this.rate = rate;
    }

    @Override
    public boolean pay(int amount) {
        if (amount <= 0) {
            return false;
        }
        if (balance - amount >= -creditLimit) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean add(int amount) {
        if (amount <= 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    @Override
    public int yearChange() {
        return balance / 100 * rate;
    }

    public int getCreditLimit() {
        return creditLimit;
    }


}
