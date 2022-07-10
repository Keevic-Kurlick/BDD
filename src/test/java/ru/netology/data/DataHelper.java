package ru.netology.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;

        public static AuthInfo getAuthInfo() {
            return new AuthInfo("vasya", "qwerty123");
        }

        public static AuthInfo getOtherAuthInfo(AuthInfo original) {
            return new AuthInfo("petya", "123qwerty");
        }
    }

    @Value
    public static class Card {
        String cardId;
        String cardNumber;

        public static Card getFirstCardFor(AuthInfo authInfo) {
            return new Card("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559000000000001");
        }

        public static Card getSecondCardFor(AuthInfo authInfo) {
            return new Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559000000000002");
        }
    }

    @Value
    public static class VerificationCode {
        String code;

        public static String getVerificationCodeFor(AuthInfo authInfo) {
            return "12345";
        }
    }
}
