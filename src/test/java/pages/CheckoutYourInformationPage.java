package pages;

import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class CheckoutYourInformationPage extends BasePage {

    private static final By FIRST_NAME_FIELD = By.id("first-name");
    private static final By LAST_NAME_FIELD = By.id("last-name");
    private static final By POSTAL_CODE_FIELD = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");

    public CheckoutYourInformationPage(WebDriver driver) {
        super(driver);
        log.info("Инициализация CheckoutPage");
    }

    @Override
    @Step("Открыть страницу оформления заказа")
    public CheckoutYourInformationPage open() {
        log.info("Открытие страницы оформления заказа по URL: {}", BASE_URL + "checkout-step-one.html");
        driver.get(BASE_URL + "checkout-step-one.html");
        return this;
    }

    @Override
    @Step("Проверить, что страница оформления заказа открыта")
    public CheckoutYourInformationPage isPageOpened() {
        log.info("Проверка открытия страницы оформления заказа");
        waitForElementToBeVisible(FIRST_NAME_FIELD);
        return this;
    }

    @Step("Заполнить данные для оформления заказа")
    public CheckoutOverviewPage fillInformation(String firstName, String lastName, String postalCode) {
        log.info("Заполнение данных для оформления: Имя={}, Фамилия={}, Индекс={}",
                firstName, lastName, postalCode);
        log.info("Ввод имени в поле: {}", FIRST_NAME_FIELD);
        driver.findElement(FIRST_NAME_FIELD).sendKeys(firstName);
        log.info("Ввод фамилии в поле: {}", LAST_NAME_FIELD);
        driver.findElement(LAST_NAME_FIELD).sendKeys(lastName);
        log.info("Ввод почтового индекса в поле: {}", POSTAL_CODE_FIELD);
        driver.findElement(POSTAL_CODE_FIELD).sendKeys(postalCode);
        log.info("Нажатие кнопки продолжения: {}", CONTINUE_BUTTON);
        driver.findElement(CONTINUE_BUTTON).click();
        log.info("Переход на страницу подтверждения заказа");
        return new CheckoutOverviewPage(driver);
    }
}