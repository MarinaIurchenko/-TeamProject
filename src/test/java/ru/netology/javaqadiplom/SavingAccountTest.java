package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SavingAccountTest {

    @Test
    public void shouldTestSavingAccount() { // тест на корректность присвоения значений в объекте
        SavingAccount account = new SavingAccount(
                10_000,
                5_000,
                20_000,
                10
        );
        int[] expected = {10_000, 5_000, 20_000, 10};
        int[] actual = {account.getBalance(), account.getMinBalance(), account.getMaxBalance(), account.getRate()};

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotTestIfMinBalanceNegative() { // тест на исключение вида IllegalArgumentException при отрицательном мин.балансе

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SavingAccount account = new SavingAccount(
                    4_000,
                    -1,
                    10_000,
                    7);
        });
    }

    @Test
    public void shouldNotTestIfUnderMinBalance() { // тест на исключение вида IllegalArgumentException при первоначальном балансе ниже мин.значения

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SavingAccount account = new SavingAccount(
                    500,
                    1_000,
                    5_000,
                    5
            );
        });
    }
    @Test
    public void shouldNotTestIfOverMaxBalance() { // тест на исключение вида IllegalArgumentException при первоначальном балансе выше макс.значения

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SavingAccount account = new SavingAccount(
                    5_500,
                    1_000,
                    5_000,
                    5
            );
        });
    }

    @Test
    public void shouldNotTestIfMinBalanceOverMaxBalance() { // тест на исключение вида IllegalArgumentException при мин.балансе больше макс.баланса

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SavingAccount account = new SavingAccount(
                    2_000,
                    3_000,
                    2_500,
                    5
            );
        });
    }

    @Test
    public void shouldAddLessThanMaxBalance() { // тест на пополнение счета, при котором итоговый баланс не превышает макс.баланс
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        account.add(3_000);

        Assertions.assertEquals(2_000 + 3_000, account.getBalance());
    }

    @Test
    public void shouldNotAddMoreThanMaxBalance() { // тест на пополнение счета, при котором итоговый баланс превышает макс.баланс
        SavingAccount account = new SavingAccount(
                5_000,
                1_000,
                10_000,
                5
        );

        account.add(7_000);

        Assertions.assertEquals(5_000, account.getBalance());
    }

    @Test
    public void shouldCalcWhenAddEqualsMaxBalance() { // тест на пополнение счета, при котором итоговый банас равен макс.балансу
        SavingAccount account = new SavingAccount(
                3_000,
                1_000,
                7_000,
                4
        );

        account.add(4_000);

        Assertions.assertEquals(3_000 + 4_000, account.getBalance());
    }

    @Test
    public void shouldPayMoreThanMinBalance() { // тест на оплату со счета, при котором итоговый баланс выше мин.балансу
        SavingAccount account = new SavingAccount(
                2_000,
                500,
                5_000,
                3
        );

        account.pay(1_000);

        Assertions.assertEquals(2_000 - 1_000, account.getBalance());
    }

    @Test
    public void shouldNotPayLessThanMinBalance() { // тест на оплату со счета, при котором итоговый баланс ниже мин.баланса
        SavingAccount account = new SavingAccount(
                3_000,
                1_000,
                10_000,
                3
        );

        account.pay(2_500);

        Assertions.assertEquals(3_000, account.getBalance());
    }

    @Test
    public void shouldCalcWhenPayEqualsMinBalance() { // тест на оплату, при котором итоговый баланс равен мин.балансу
        SavingAccount account = new SavingAccount(
                5_000,
                1_000,
                7_000,
                5
        );

        account.pay(4000);

        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void shouldCalcRate() { // тест на расчет процентов на остаток по счету
        SavingAccount account = new SavingAccount(
                1_000,
                500,
                5_000,
                10
        );

        Assertions.assertEquals(100, account.yearChange());
    }

    @Test
    public void shouldThrowWhenRateNegative() { // тест на исключение вида IllegalArgumentException при отрицательном значении годовой ставки

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SavingAccount account = new SavingAccount(
                    5_000,
                    1_000,
                    5_000,
                    -10
            );
        });
    }

    @Test
    public void shouldNotCalcRateIfAdd() { // тест на расчет процентов на остаток по счету в случае если было изменение счета (пополнение)
        SavingAccount account = new SavingAccount(
                5_000,
                1_000,
                7_000,
                7
        );

        account.add(1_000);

        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    public void shouldNotCalcRateIfPay() { // тест на расчет процентов на остаток по счету в случае если было изменение счета (оплата)
        SavingAccount account = new SavingAccount(
                3_000,
                500,
                5_000,
                12
        );

        account.pay(1_500);

        Assertions.assertEquals(0, account.yearChange());
    }
}
