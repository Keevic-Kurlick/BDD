package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.open;

public class transferBalanceTest {

    private static final DataHelper.AuthInfo user = DataHelper.AuthInfo.getAuthInfo();
    private static final int sumToTransfer = 350;
    private int initialBalanceFirstCard;
    private int initialBalanceSecondCard;
    private int initialSum;
    private static DashboardPage dashboard;

    @BeforeAll
    static void setUp() {
        open("http://localhost:9999");
        LoginPage validLogin = new LoginPage();
        VerificationPage verification = validLogin.validLogin(user);
        dashboard = verification.validVerify(DataHelper.VerificationCode.getVerificationCodeFor(user));
    }

    @BeforeEach
    void setUp1() {
        initialBalanceFirstCard = dashboard.getCardBalance(user.getFirstCard().getCardId());
        initialBalanceSecondCard = dashboard.getCardBalance(user.getSecondCard().getCardId());
        initialSum = initialBalanceFirstCard + initialBalanceSecondCard;
    }

    @AfterEach
    void checkIfInitialSumStaysImmutable() {
        Assertions.assertEquals(initialSum, dashboard.getCardBalance(user.getFirstCard().getCardId()) +
                dashboard.getCardBalance(user.getSecondCard().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        var transfer = dashboard.transferBalance(user.getFirstCard().getCardId());
        transfer.transferBalance("350", user.getSecondCard().getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard + 350,
                dashboard.getCardBalance(user.getFirstCard().getCardId()));
        Assertions.assertEquals(initialBalanceSecondCard - 350,
                dashboard.getCardBalance(user.getSecondCard().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        var transfer = dashboard.transferBalance(user.getSecondCard().getCardId());
        transfer.transferBalance("350", user.getFirstCard().getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard - 350,
                dashboard.getCardBalance(user.getFirstCard().getCardId()));
        Assertions.assertEquals(initialBalanceSecondCard + 350,
                dashboard.getCardBalance(user.getSecondCard().getCardId()));
    }
}
