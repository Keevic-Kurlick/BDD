package ru.netology.tests;

import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    private static final DataHelper.AuthInfo user = DataHelper.AuthInfo.getAuthInfo();
    private static final int sumToTransfer = 350;

    @BeforeAll
    static void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        //       open("http://localhost:9999");
        LoginPage validLogin = new LoginPage();
        var verification = validLogin.validLogin(user);
        var dashboard = verification.validVerify(DataHelper.VerificationCode.getVerificationCodeFor(user));
        var initialBalanceFirstCard = dashboard.getCardBalance(user.getFirstCard().getCardId());
        var initialBalanceSecondCard = dashboard.getCardBalance(user.getSecondCard().getCardId());
        var transfer = dashboard.transferBalance(user.getSecondCard().getCardId());
        transfer.transferBalance("350", user.getFirstCard().getCardNumber());

        Assertions.assertEquals(initialBalanceFirstCard - 350,
                dashboard.getCardBalance(user.getFirstCard().getCardId()));
        Assertions.assertEquals(initialBalanceSecondCard + 350,
                dashboard.getCardBalance(user.getSecondCard().getCardId()));
    }
}
