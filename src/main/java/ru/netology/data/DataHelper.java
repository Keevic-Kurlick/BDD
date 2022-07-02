package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.Locale;

public class DataHelper {

    @RequiredArgsConstructor
    @Value
    public static class AuthInfo {
        String login;
        String password;
        @NonFinal
        ArrayList<Card> cards;

        public static AuthInfo getAuthInfo() {
            return new AuthInfo("vasya", "qwerty123");
        }

        public static AuthInfo getOtherAuthInfo() {
            return new AuthInfo("petya", "123qwerty");
        }

        public Card getFirstCard() { return new Card("92df3f1c-a033-48e6-8390-206f6b1f56c0",
                "5559000000000001", 10_000);
        }
        public Card getSecondCard() { return new Card("0f3f5c2a-249e-4c3d-8287-09f7a039391d",
                "5559000000000002", 10_000); }

        public static AuthInfo generateAuthInfo(String locale) {
            Faker faker = new Faker((new Locale(locale)));
            return new AuthInfo(faker.name().fullName(), faker.internet().password());
        }
    }

    @Value
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Card {
        String cardId;
        String cardNumber;
        @NonFinal
        int initBalance;
    }

    @Value
    public static class VerificationCode {
        String code;

        public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
            return new VerificationCode("12345");
        }
    }
}
