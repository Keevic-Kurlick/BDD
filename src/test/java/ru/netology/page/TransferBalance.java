package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TransferBalance {
    SelenideElement header = $("h1");
    SelenideElement transferButton = $("button[data-test-id = action-transfer]");
    SelenideElement amountInput = $("[data-test-id = amount] input");
    SelenideElement fromInput = $("[data-test-id = from] input");

    public TransferBalance() {
        header.shouldHave(text("Пополнение карты"));
    }

    public void transferBalance(String amount, String fromCardNumber) {
        amountInput.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        amountInput.setValue(amount);
        fromInput.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
        fromInput.sendKeys(fromCardNumber);
        transferButton.click();
    }
}
