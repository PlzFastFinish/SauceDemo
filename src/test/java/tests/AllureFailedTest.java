package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Тесты корзины покупок")
@Feature("Основные функции корзины")
public class AllureFailedTest extends BaseTest {

    @Test(description = "Проверка соответствия названия товара в корзине (намеренное падение)",enabled = false)
    @Story("Проверка добавления товара в корзину")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Shishkin F.A")
    @Link("https://www.saucedemo.com/")
    @TmsLink("ITM-4")
    @Issue("ITM-4-1")
    public void demoFailedTest() {
        loginStandardUser();
        productsPage.addToCart("Sauce Labs Backpack");
        String itemName = cartPage
                .open()
                .getItemName();
        Assert.assertEquals(itemName,
                "BLA BLA BLA",
                "Намеренная ошибка");
    }
}