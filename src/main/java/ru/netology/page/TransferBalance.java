package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import lombok.experimental.NonFinal;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
@Value
public class TransferBalance {
    SelenideElement header = $("h1");
    @NonFinal
    ElementsCollection buttons = $$("li button");
    SelenideElement transferButton = $("button[data-test-id = action-transfer]");
    SelenideElement amountInput = $("[data-test-id = amount] input");
    SelenideElement fromInput = $("[data-test-id = from] input");
    SelenideElement to = $("[data-test-id = to]");

    public TransferBalance() {
        header.shouldHave(text("Пополнение карты"));
    }

    public DashboardPage transferBalance(String amount, String cardNumber) {
        amountInput.setValue(amount);
        fromInput.sendKeys(cardNumber);
        transferButton.click();
        return new DashboardPage();
    }

}
