package ru.netology.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {

    @RequiredArgsConstructor
    @Value
    public static class AuthInfo {
        String login;
        String password;
        @NonFinal
        List<Card> cards = new ArrayList<>();

        public static AuthInfo getAuthInfo() {
            return new AuthInfo("vasya", "qwerty123");
        }

        public void setCard(Card card) {
            cards.add(card);
        }

        public Card getCard(int number) {
            return cards.get(number);
        }

        public String firstCardId() {
            return "92df3f1c-a033-48e6-8390-206f6b1f56c0";
        }

        public String secondCardId() {
            return "0f3f5c2a-249e-4c3d-8287-09f7a039391d";
        }
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Card {
        String cardNumber;
        public final static String FIRST_CARD_NUMBER = "5559000000000001";
        public final static String SECOND_CARD_NUMBER = "5559000000000002";
        int initBalance;
    }

    @Value
    public static class VerificationCode {
        String code;

        public static String getVerificationCodeFor(AuthInfo authInfo) {
            return "12345";
        }
    }
}
