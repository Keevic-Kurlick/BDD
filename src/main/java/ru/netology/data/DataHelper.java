package ru.netology.data;

import com.github.javafaker.Faker;
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

        public void addCard(Card card) {
            cards.add(card);
        }

        public static AuthInfo getAuthInfo() {
            return new AuthInfo("vasya", "qwerty123");
        }

        public static AuthInfo getOtherAuthInfo() {
            return new AuthInfo("petya", "123qwerty");
        }

        public static AuthInfo generateAuthInfo(String locale) {
            Faker faker = new Faker((new Locale(locale)));
            return new AuthInfo(faker.name().fullName(), faker.internet().password());
        }
    }

    @Value
    @RequiredArgsConstructor
    public class Card {
        String number;
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
