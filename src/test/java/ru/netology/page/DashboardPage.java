package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    ElementsCollection cards = $$(".list__item div");
    SelenideElement header = $("h1");
    ElementsCollection buttons = $$("div");
    SelenideElement reloadButton = $("[data-test-id= action-reload]");

    String balanceStart = ", баланс: ";
    String balanceFinish = " р.";

    public DashboardPage() {
        header.shouldHave(text("Ваши карты"));
        reloadButton.click();
    }

    public int getCardBalance(String id) {
        val text = cards.findBy(Condition.attributeMatching("data-test-id", id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferBalance transferBalance(String cardIdToTransfer) {
        buttons.findBy(attributeMatching("data-test-id", cardIdToTransfer)).find("button").click();
        return new TransferBalance();
    }
}
