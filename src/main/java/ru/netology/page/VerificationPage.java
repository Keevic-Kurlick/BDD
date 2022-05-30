package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import lombok.Value;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Value
public class VerificationPage {
    SelenideElement codeField = $("[data-test-id=code] input");
    SelenideElement verifyButton = $("[data-test-id=action-verify]");

    public VerificationPage(){
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(DataHelper.VerificationCode code) {
        codeField.setValue(code.getCode());
        verifyButton.click();
        return new DashboardPage();
    }
}