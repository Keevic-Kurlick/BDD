package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class TransferBalanceTest {

    private static DataHelper.AuthInfo user;
    private final DataHelper.Card firstCard = DataHelper.Card.getFirstCardFor(user);
    private final DataHelper.Card secondCard = DataHelper.Card.getSecondCardFor(user);
    private final int sumToTransfer = 350;
    private int initialBalanceFirstCard;
    private int initialBalanceSecondCard;
    private int initialSum;
    private static DashboardPage dashboard;

    @BeforeAll
    static void setUp() {
        open("http://localhost:9999");
        user = DataHelper.AuthInfo.getAuthInfo();
        LoginPage validLogin = new LoginPage();
        VerificationPage verification = validLogin.validLogin(user);
        dashboard = verification.validVerify(user);
    }

    @BeforeEach
    void setUp1() {
        initialBalanceFirstCard = dashboard.getCardBalance(firstCard.getCardId());
        initialBalanceSecondCard = dashboard.getCardBalance(secondCard.getCardId());
        initialSum = initialBalanceFirstCard + initialBalanceSecondCard;
    }

    @AfterEach
    void checkIfInitialSumStaysImmutable() {
        Assertions.assertEquals(initialSum,
                dashboard.getCardBalance(firstCard.getCardId()) +
                        dashboard.getCardBalance(secondCard.getCardId()));
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var transfer = dashboard.transferBalance(firstCard.getCardId());
        transfer.transferBalance(String.valueOf(sumToTransfer), secondCard.getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard + sumToTransfer,
                dashboard.getCardBalance(firstCard.getCardId()));
        Assertions.assertEquals(initialBalanceSecondCard - sumToTransfer,
                dashboard.getCardBalance(secondCard.getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var transfer = dashboard.transferBalance(secondCard.getCardId());
        transfer.transferBalance(String.valueOf(sumToTransfer), firstCard.getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard - sumToTransfer,
                dashboard.getCardBalance(firstCard.getCardId()));
        Assertions.assertEquals(initialBalanceSecondCard + sumToTransfer,
                dashboard.getCardBalance(secondCard.getCardId()));
    }

    @Test
    void shouldTransferCardBalance() {
        var transfer = dashboard.transferBalance(secondCard.getCardId());
        String amount = String.valueOf(initialBalanceFirstCard);
        transfer.transferBalance(amount, firstCard.getCardNumber());

        Assertions.assertEquals(0, dashboard.getCardBalance(firstCard.getCardId()));
        Assertions.assertEquals(dashboard.getCardBalance(secondCard.getCardId()),
                initialBalanceFirstCard + initialBalanceSecondCard);
    }

    @Test
    void shouldNotTransferMoreThanCardBalance() {
        var transfer = dashboard.transferBalance(secondCard.getCardId());
        String amount = String.valueOf(initialBalanceFirstCard + 1);
        transfer.transferBalance(amount, firstCard.getCardNumber());

        Assertions.assertTrue(dashboard.getCardBalance(firstCard.getCardId()) >= 0);
    }
}
