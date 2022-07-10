package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class TransferBalanceTest {

    private static DataHelper.AuthInfo user;
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

        DataHelper.Card cardFirst = new DataHelper.Card(
                DataHelper.Card.FIRST_CARD_NUMBER,
                dashboard.getCardBalance(user.firstCardId())
        );

        DataHelper.Card cardSecond =  new DataHelper.Card(
                DataHelper.Card.SECOND_CARD_NUMBER,
                dashboard.getCardBalance(user.secondCardId())
        );

        user.setCard(cardFirst);
        user.setCard(cardSecond);
    }

    @BeforeEach
    void setUp1() {
        initialBalanceFirstCard = dashboard.getCardBalance(user.firstCardId());
        initialBalanceSecondCard = dashboard.getCardBalance(user.secondCardId());
        initialSum = initialBalanceFirstCard + initialBalanceSecondCard;
    }

    @AfterEach
    void checkIfInitialSumStaysImmutable() {
        Assertions.assertEquals(initialSum, dashboard.getCardBalance(user.firstCardId()) +
                dashboard.getCardBalance(user.secondCardId()));
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var transfer = dashboard.transferBalance(user.firstCardId());
        transfer.transferBalance(String.valueOf(sumToTransfer), user.getCard(1).getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard + sumToTransfer,
                dashboard.getCardBalance(user.firstCardId()));
        Assertions.assertEquals(initialBalanceSecondCard - sumToTransfer,
                dashboard.getCardBalance(user.secondCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var transfer = dashboard.transferBalance(user.secondCardId());
        transfer.transferBalance(String.valueOf(sumToTransfer), user.getCard(0).getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard - sumToTransfer,
                dashboard.getCardBalance(user.firstCardId()));
        Assertions.assertEquals(initialBalanceSecondCard + sumToTransfer,
                dashboard.getCardBalance(user.secondCardId()));
    }

    @Test
    void shouldTransferCardBalance() {
        var transfer = dashboard.transferBalance(user.secondCardId());
        String amount = String.valueOf(initialBalanceFirstCard);
        transfer.transferBalance(amount, user.getCard(0).getCardNumber());

        Assertions.assertEquals(0, dashboard.getCardBalance(user.firstCardId()));
        Assertions.assertEquals(dashboard.getCardBalance(user.secondCardId()),
                initialBalanceFirstCard + initialBalanceSecondCard);
    }

    @Test
    void shouldNotTransferMoreThanCardBalance() {
        var transfer = dashboard.transferBalance(user.secondCardId());
        String amount = String.valueOf(initialBalanceFirstCard + 1);
        transfer.transferBalance(amount, user.getCard(0).getCardNumber());

        Assertions.assertTrue(dashboard.getCardBalance(user.firstCardId()) >= 0);
    }
}
