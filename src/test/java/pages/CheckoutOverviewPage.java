package pages;

import lombok.extern.log4j.Log4j2;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Log4j2
public class CheckoutOverviewPage extends BasePage {
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By TOTAL_LABEL = By.className("summary_total_label");
    private static final By FINISH_BUTTON = By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        super(driver);
        log.info("Инициализация страницы подтверждения заказа");
    }

    @Override
    public CheckoutOverviewPage open() {
        log.info("Открываю страницу подтверждения заказа: {}", BASE_URL + "checkout-step-two.html");
        driver.get(BASE_URL + "checkout-step-two.html");
        return this;
    }

    @Override
    public CheckoutOverviewPage isPageOpened() {
        log.info("Ожидание видимости элемента: {}", FINISH_BUTTON);
        waitForElementToBeVisible(FINISH_BUTTON);
        return this;
    }

    @Step("Получить название товара на странице подтверждения")
    public String getItemName() {
        log.info("Получение названия товара из элемента: {}", ITEM_NAME);
        waitForElementToBeVisible(ITEM_NAME);
        return driver.findElement(ITEM_NAME).getText();
    }

    @Step("Получить итоговую сумму заказа")
    public String getTotalPrice() {
        log.info("Получение суммы заказа из элемента: {}", TOTAL_LABEL);
        waitForElementToBeVisible(TOTAL_LABEL);
        return driver.findElement(TOTAL_LABEL).getText();
    }

    public CheckoutCompletePage finishCheckout() {
        log.info("Нажатие кнопки завершения заказа: {}", FINISH_BUTTON);
        driver.findElement(FINISH_BUTTON).click();
        return new CheckoutCompletePage(driver);
    }

    @Step("Проверить данные на странице подтверждения: {expectedName}")
    public CheckoutOverviewPage validateCheckoutData(String expectedName) {
        log.info("Начало проверки заказа. Ожидаемый товар: {}", expectedName);
        String totalPrice = getTotalPrice().replaceAll("[^0-9.]", "");
        String actualName = getItemName();

        assertEquals(actualName, expectedName,
                "Название товара на странице подтверждения не совпадает. Ожидалось: " + expectedName + ", Фактически: " + actualName);

        assertTrue(Double.parseDouble(totalPrice) > 0,
                "Итоговая сумма должна быть положительной. Фактическая сумма: " + totalPrice);
        log.info("Проверка завершена. Товар: {}, Сумма: {}", actualName, totalPrice);
        return this;
    }
}