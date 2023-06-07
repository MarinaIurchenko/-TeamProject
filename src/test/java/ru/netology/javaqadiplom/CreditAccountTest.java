package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAccountTest {

    private CreditAccount creditAccount;

    @Test
    public void shouldAddToPositiveBalance() {//// тест на корректность присвоения значений в объекте
        CreditAccount account = new CreditAccount(
                0,
                5_000,
                15
        );

        account.add(3_000);

        assertEquals(3_000, account.getBalance());
    }
    @BeforeEach
    public void setUp() {
        this.creditAccount = new CreditAccount(1000, 500, 5);
    }

    @Test
    public void testPaySuccessful() {
        boolean isPaid = creditAccount.pay(500);
        Assertions.assertTrue(isPaid);
        Assertions.assertEquals(500, creditAccount.getBalance());
    }

    @Test
    public void testPayUnsuccessful() {
        boolean isPaid = creditAccount.pay(1500);
        Assertions.assertFalse(isPaid);
        Assertions.assertEquals(1000, creditAccount.getBalance());
    }

    @Test
    public void testAddSuccessful() {
        boolean isAdded = creditAccount.add(500);
        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(1500, creditAccount.getBalance());
    }

    @Test
    public void testAddUnsuccessful() {
        boolean isAdded = creditAccount.add(-500);
        Assertions.assertFalse(isAdded);
        Assertions.assertEquals(1000, creditAccount.getBalance());

        // Проверка на заблокированный аккаунт:
        creditAccount.pay(1500); // Баланс становится отрицательным
        isAdded = creditAccount.add(500);
        Assertions.assertFalse(isAdded);
        Assertions.assertEquals(-500, creditAccount.getBalance());
    }

    // Граничные случаи:

    @Test
    public void testPayLimit() {
        boolean isPaid = creditAccount.pay(1000);
        Assertions.assertTrue(isPaid);
        Assertions.assertEquals(-500, creditAccount.getBalance());
    }

    @Test
    public void testPayAll() {
        boolean isPaid = creditAccount.pay(1500);
        Assertions.assertTrue(isPaid);
        Assertions.assertEquals(-500, creditAccount.getBalance());
    }

    @Test
    public void testAddZero() {
        boolean isAdded = creditAccount.add(0);
        Assertions.assertFalse(isAdded);
        Assertions.assertEquals(1000, creditAccount.getBalance());
    }


    @Test
    public void testPayWithPositiveAmount() {//Тест на проверку корректности начального значения баланса

        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 15;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(500, account.getBalance());
    }

    @Test
    public void testPayWithNegativeAmount() {// Тест на добавление отрицательной суммы на счет
        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 15;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void testPayWithAmountGreaterThanBalanceAndCreditLimit() {//Тест на добавление суммы, превышающей кредитный лимит
        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 15;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(7000);
        Assertions.assertFalse(result);
        Assertions.assertEquals(-6000, account.getBalance());
    }

    @Test
    public void testAddWithPositiveAmount() {//Тест на добавление положительной суммы на счет
        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 15;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.add(500);
        Assertions.assertTrue(result);
        Assertions.assertEquals(1500, account.getBalance());
    }

    @Test
    public void testAddWithNegativeAmount() {
        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 15;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.add(-500);
        Assertions.assertFalse(result);
        Assertions.assertEquals(1000, account.getBalance());
    }

    @Test
    public void testYearChange() {//возвращает корректное значение изменения баланса счета от начала года
        int initialBalance = 1000;
        int creditLimit = 5000;
        int rate = 15;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        int interest = account.yearChange();
        Assertions.assertEquals(100, interest);
    }

    @Test
    public void testNegativeRate() {
        int initialBalance = 1000;
        int creditLimit = 5000;
        int rate = -5;
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CreditAccount(initialBalance, creditLimit, rate)
        );
    }
    @Test
    public void testNegativeInitialBalance() {//отрицательный начальный баланс
        int initialBalance = -1000;
        int creditLimit = 5000;
        int rate = 15;
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CreditAccount(initialBalance, creditLimit, rate)
        );
    }

    @Test
    public void testNegativeCreditLimit() {//отрицательный кредитный лимит
        int initialBalance = 1000;
        int creditLimit = -5000;
        int rate = 10;
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new CreditAccount(initialBalance, creditLimit, rate)
        );
    }

    @Test
    public void testPayWithAmountEqualToBalance() {//оплата сумм, равных текущему балансу или отрицательному кредитному лимиту
        int initialBalance = 1000;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(1000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void testPayWithAmountEqualToNegativeCreditLimit() {//добавление сумм, равных отрицательному кредитному лимиту
        int initialBalance = 1000;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(-5000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-5000, account.getBalance());
    }

    @Test
    public void testAddWithAmountEqualToNegativeCreditLimit() {//начисление процентов при нулевом балансе
        int initialBalance = -5000;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.add(-5000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void testYearChangeWithZeroBalance() {
        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        int interest = account.yearChange();
        Assertions.assertEquals(0, interest);
    }

}



