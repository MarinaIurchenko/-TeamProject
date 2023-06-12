package ru.netology.javaqadiplom;

public class SavingAccount extends Account {

    protected int minBalance;
    protected int maxBalance;
    protected boolean payCheck;
    protected boolean addCheck;

    public SavingAccount(int initialBalance, int minBalance, int maxBalance, int rate) {
        if (initialBalance < 0|| initialBalance < minBalance || initialBalance > maxBalance) {
            throw new IllegalArgumentException("Начальный баланс не может быть отрицательным, а у вас: " + initialBalance);
        }
        if (rate < 0) {
            throw new IllegalArgumentException("Накопительная ставка не может быть отрицательной, а у вас: " + rate);
        }
        if (minBalance < 0){
            throw new IllegalArgumentException("Минимальный баланс не может быть отрицательной, а у вас: " + minBalance);
        }
        if (maxBalance < 0){
            throw new IllegalArgumentException("Максимальный баланс не может быть отрицательной, а у вас: " + maxBalance);
        }
        if (maxBalance < minBalance) {
            throw new IllegalArgumentException("Минимальный баланс должен быть не больше максимального, а у вас: " + minBalance + " > " + maxBalance);
        }
        this.balance = initialBalance;
        this.minBalance = minBalance;
        this.maxBalance = maxBalance;
        this.rate = rate;

    }

    @Override
    public boolean pay(int amount) {
        if (amount <= 0) {
            return payCheck = false;
        }  if (balance - amount >= minBalance) {
            balance -= amount;
            return payCheck = true;
        } else {
            return payCheck = false;
        }
    }

    @Override
    public boolean add(int amount) {
        if (amount <= 0) {
            return addCheck = false;
        }  if (balance + amount <= maxBalance) {
            balance += amount;
            return addCheck = true;
        } else {
            return addCheck = false;
        }
    }
    @Override
    public int yearChange() {
        if (payCheck) {
            return 0;
        } else if (addCheck) {
            return 0;
        } else
            return balance / 100 * rate;
    }

    public int getMinBalance() {
        return minBalance;
    }

    public int getMaxBalance() {
        return maxBalance;
    }
}
