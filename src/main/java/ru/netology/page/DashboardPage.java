package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Value
public class DashboardPage {

    @NonFinal
    ElementsCollection cards = $$(".list__item div");
    SelenideElement header = $("h1");
    ElementsCollection buttons = $$("div");
    SelenideElement reloadButton = $("[data-test-id= action-reload]");

    String balanceStart = "баланс: ";
    String balanceFinish = " р.";

    public DashboardPage() {
        header.shouldHave(text("Ваши карты"));
        reloadButton.click();
    }

    public int getCardBalance(String id) {
        val text = cards.findBy(Condition.attributeMatching("data-test-id", id)).text();
        return extractBalance(text);
    }

    public String getCardNumber(String id) {
        val text = cards.findBy(Condition.attributeMatching("data-test-id", id)).text();
        return extractCard(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    private String extractCard(String text) {
        String[] verse = text.split(balanceStart);
        return verse[0];
    }

    public TransferBalance transferBalance(String id) {
        buttons.findBy(attributeMatching("data-test-id", id)).find("button").click();
        return new TransferBalance();
    }
}
