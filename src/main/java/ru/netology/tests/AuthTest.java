package ru.netology.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    private final String FIRST_CARD = "5559000000000001";
    private final String FIRST_CARD_ID = "92df3f1c-a033-48e6-8390-206f6b1f56c0";
    private final String SECOND_CARD = "5559000000000002";
    private final String SECOND_CARD_ID = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";
    private int initSum = 10_000;

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        open("http://localhost:9999");
        DataHelper.AuthInfo user = DataHelper.AuthInfo.getAuthInfo();
        LoginPage validLogin = new LoginPage();
        var verification = validLogin.validLogin(user);
        var dashboard = verification.validVerify(DataHelper.VerificationCode.getVerificationCodeFor(user));
        var transfer = dashboard.transferBalance(FIRST_CARD_ID);
        initSum = dashboard.getCardBalance(FIRST_CARD_ID);
        transfer.transferBalance("350", SECOND_CARD);
        Assertions.assertEquals(dashboard.getCardBalance(FIRST_CARD_ID), initSum-350);
        Assertions.assertEquals(dashboard.getCardBalance(SECOND_CARD_ID), initSum+350);
    }
}
