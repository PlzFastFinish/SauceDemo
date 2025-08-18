package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class CheckoutCompletePage extends BasePage {
    private static final By COMPLETE_HEADER = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
        log.info("Инициализация страницы завершения заказа");
    }

    @Override
    public CheckoutCompletePage open() {
        driver.get(BASE_URL + "checkout-complete.html");
        return this;
    }

    @Override
    public CheckoutCompletePage isPageOpened() {
        log.info("Проверка открытия страницы завершения заказа");
        waitForElementToBeVisible(COMPLETE_HEADER);
        return this;
    }

    public String getConfirmationText() {
        log.info("Получение текста подтверждения заказа");
        return driver.findElement(COMPLETE_HEADER).getText();
    }
}