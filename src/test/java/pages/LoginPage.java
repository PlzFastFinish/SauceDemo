package pages;

import lombok.extern.log4j.Log4j2;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class LoginPage extends BasePage {
    private static final By LOGIN_FIELD = By.id("user-name");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test=error]");

    public LoginPage(WebDriver driver) {
        super(driver);
        log.info("Инициализация страницы авторизации");
    }

    @Override
    @Step("Открытие страницы логина")
    public LoginPage open() {
        log.info("Открытие страницы авторизации по URL: {}", BASE_URL);
        driver.get(BASE_URL);
        return this;
    }

    @Override
    @Step("Проверка открытия страницы логина")
    public LoginPage isPageOpened() {
        log.info("Проверка видимости полей авторизации");
        waitForElementToBeVisible(LOGIN_FIELD)
                .waitForElementToBeVisible(PASSWORD_FIELD);
        return this;
    }

    @Step("Вход в систему с именем пользователя: {user} и паролем: {password}")
    public ProductsPage login(String user, String password) {
        log.info("Попытка авторизации пользователя: {},{}", user,password);
        isPageOpened();
        driver.findElement(LOGIN_FIELD).sendKeys(user);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return new ProductsPage(driver);
    }

    @Step("Попытка входа с невалидными данными: login = '{user}', password = '{password}'")
    public LoginPage attemptLogin(String user, String password) {
        log.info("Попытка входа с невалидными данными. Логин: {},{}", user,password);
        driver.findElement(LOGIN_FIELD).sendKeys(user);
        driver.findElement(PASSWORD_FIELD).sendKeys(password);
        driver.findElement(LOGIN_BUTTON).click();
        return this;
    }

    @Step("Получение текста ошибки при неудачном входе")
    public String getErrorMessage() {
        log.info("Получение текста ошибки авторизации");
        return driver.findElement(ERROR_MESSAGE).getText();
    }
}