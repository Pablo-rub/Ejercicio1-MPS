package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    BankAccount bankAccount;

    @BeforeEach
    void setup(){bankAccount = new BankAccount(0);}

    @Test
    @DisplayName("Al sacar dinero se realiza bien la operación")
    void moneyWithdrew() {
        int balance = bankAccount.getBalance();
        int amount = balance/4;
        bankAccount.withdraw(amount);
        int obtainedValue = bankAccount.getBalance();
        int expectedValue = balance - amount;

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Si la cantidad a sacar es mayor que la disponible, no se realiza la operación")
    void moneyNotWithdrew() {
        int amount = bankAccount.getBalance() * 2;
        boolean obtainedValue = !bankAccount.withdraw(amount);
        assertFalse(obtainedValue);
    }

    @Test
    @DisplayName("Al depositar dinero negativo debe devolver un error")
    void negativeMoneyError() {
        int amount = -1;
        assertThrows(IllegalArgumentException.class, () -> {
            bankAccount.deposit(amount);
        });
    }

    @Test
    @DisplayName("Al depositar dinero se realiza bien la operación")
    void moneyDeposited() {
        int balance = bankAccount.getBalance();
        int amount = balance/4;
        bankAccount.deposit(amount);
        int obtainedValue = bankAccount.getBalance();
        int expectedValue = balance + amount;

        assertEquals(expectedValue, obtainedValue);
    }

    @Test
    @DisplayName("Se calcula de forma exitosa el pago mensual")
    void wellCalculatedPayment() {
        double amount = 10000, interest = 0.001;
        int npayments = 12;
        double obtainedValue = bankAccount.payment(amount, interest, npayments);
        double expectedValue = 838.7599255697181;

        assertEquals(expectedValue, obtainedValue);
    }

    @ParameterizedTest
    @CsvSource({"1, 1, 1, 0, 1", "10000, 0.001, 12, 2, 8341.651388934994"})
    @DisplayName("Se calcula de forma exitosa la deuda mensual si son 0 meses")
    void zeroMonthsLoan(double amount, double interest, int npayments, int months, double expectedValue) {
        double obtainedValue = bankAccount.pending(amount, interest, npayments, months);
        assertEquals(expectedValue, obtainedValue);
    }
}
