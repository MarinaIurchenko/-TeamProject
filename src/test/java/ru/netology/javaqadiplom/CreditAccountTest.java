package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAccountTest {

    @Test
    public void shouldAddToPositiveBalance() {
        CreditAccount account = new CreditAccount(
                0,
                5_000,
                15
        );

        account.add(3_000);

        assertEquals(3_000, account.getBalance());
    }
    @Test
    public void testConstructorRight(){//Тест на корректность присвоения значений при создании объекта
        CreditAccount account = new CreditAccount(0,5000,15);
        int[] expected = {0,5000,15};
        int[] actual = {account.getBalance(), account.getCreditLimit(), account.getRate()};
        Assertions.assertArrayEquals(expected, actual);
    }
    @Test
    public void testValuesNegativeCreditLimit(){// Тест на корректные значения,при отрицательном кредитном лимите

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CreditAccount account = new CreditAccount(0,-5000,15);});
    }
    @Test
    public void testValuesNegativeBalance(){// Тест на корректные значения,при отрицательном балансе

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CreditAccount account = new CreditAccount(-1000,5000,15);});
    }
    @Test
    public void testValuesNegativeInterestRate(){// Тест на корректные значения,при отрицательной процентной ставке

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            CreditAccount account = new CreditAccount(1000,5000,-15);});
    }

    @Test
    public void testPayLimit() {// Тест на оплату в размере предельного значения баланса
        CreditAccount account = new CreditAccount(1000, 5000, 15);
        boolean isPaid = account.pay(1000);
        Assertions.assertTrue(isPaid);
        Assertions.assertEquals(0, account.getBalance());
    }
    @Test
    public void testPayAll() {// Тест на оплату в размере превышающий баланс
        CreditAccount account = new CreditAccount(1000, 5000, 15);
        boolean isPaid = account.pay(1500);
        Assertions.assertTrue(isPaid);
        Assertions.assertEquals(-500, account.getBalance());
    }

    @Test
    public void testPayNegative() {//Тест для метода `pay` при попытке оплатить отрицательную сумму
        CreditAccount account = new CreditAccount(1000, 5000, 15);
        boolean isPaid = account.pay(-500);
        Assertions.assertFalse(isPaid);
        Assertions.assertEquals(1000, account.getBalance());
    }
    @Test
    public void testPayOverDebt() {//Тест для метода `pay` при попытке оплатить сумму, превышающую общую задолженность по кредиту
        CreditAccount account = new CreditAccount(-3000, 5000, 15);
        boolean isPaid = account.pay(4000);
        Assertions.assertFalse(isPaid);
        Assertions.assertEquals(-3000, account.getBalance());
    }
    @Test
    public void testAddOverLimit() {//Тест для метода `add` при добавлении суммы, превышающей кредитный лимит
        CreditAccount account = new CreditAccount(-3000, 5000, 15);
        boolean isAdded = account.add(6000);
        Assertions.assertFalse(isAdded);
        Assertions.assertEquals(-3000, account.getBalance());
    }

    @Test
    public void testAddZero() {// тест на поведение метода add,при 0 значении и что баланс счета после попытки выполнения операции add со значением 0 остался неизменным и равен 1000 единицам
        CreditAccount account = new CreditAccount(1000, 5000, 15);
        boolean isAdded = account.add(0);
        Assertions.assertFalse(isAdded);
        Assertions.assertEquals(1000, account.getBalance());
    }
    @Test
    public void testAddNegative() {//Тест для метода `add` при попытке добавить отрицательную сумму
        CreditAccount account = new CreditAccount(1000, 5000, 15);
        boolean isAdded = account.add(-500);
        Assertions.assertFalse(isAdded);
        Assertions.assertEquals(1000, account.getBalance());
    }
    @Test
    public void testYearChangeZeroBalanceAndLimit() {//Тест для метода `yearChange` при балансе на счете и кредитном лимите, равных нулю
        CreditAccount account = new CreditAccount(0, 0, 15);
        int interest = account.yearChange();
        Assertions.assertEquals(0, interest);
    }
    @Test
    public void testYearChangeNegativeBalance() {//Тест для метода `yearChange` при балансе на счете, равном отрицательному кредитному лимиту
        CreditAccount account = new CreditAccount(-5000, 5000, 10);
        int interest = account.yearChange();
        int expectedInterest = (int) Math.round(-5000 * 0.1);
        Assertions.assertEquals(expectedInterest, interest);
    }
    @Test
    public void testYearChangePositiveBalance() {// Тест для метода `yearChange` при балансе на счете, равном положительному кредитному лимиту
        CreditAccount account = new CreditAccount(3000, 2000, 8);
        int interest = account.yearChange();
        int expectedInterest = (int) Math.round(3000 * 0.08);
        Assertions.assertEquals(expectedInterest, interest);
    }

    @Test
    public void testPayWithAmountEqualToBalance() {//Тест проверяет, что при выплате суммы, которая равна текущему балансу метод возвращает что выплата была успешно завершена
        int initialBalance = 1000;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(1000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void testPayWithAmountEqualToNegativeCreditLimit() {//Тест проверяет, что при выплате суммы, которая эквивалентна отрицательному предельному кредитному лимиту , метод должен вернуть true и баланс должен стать равным этой сумме
        int initialBalance = 1000;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.pay(5000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(-4000, account.getBalance());
    }

    @Test
    public void testAddWithAmountEqualToNegativeCreditLimit() {//Тест проверяет, что при попытке внести сумму, равную отрицательному лимиту,транзакция должна быть выполнена успешно, а баланс счета должен стать равным 0
        int initialBalance = -5000;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        boolean result = account.add(-5000);
        Assertions.assertTrue(result);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void testYearChangeWithZeroBalance() {// Тест проверяет, что при отсутствии баланса на счете кредитной карты, изменение баланса в течение года должно оставаться нулевым
        int initialBalance = 0;
        int creditLimit = 5000;
        int rate = 10;
        CreditAccount account = new CreditAccount(initialBalance, creditLimit, rate);
        int interest = account.yearChange();
        Assertions.assertEquals(0, interest);
    }

}



