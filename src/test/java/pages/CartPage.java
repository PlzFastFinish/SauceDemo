package pages;

import lombok.extern.log4j.Log4j2;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


@Log4j2
public class CartPage extends BasePage {
    private static final By CART_ITEM = By.className("cart_item");
    private static final By REMOVE_BUTTON = By.cssSelector("button[class*='btn_secondary']");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By ITEM_NAME = By.className("inventory_item_name");
    private static final By ITEM_PRICE = By.cssSelector(".inventory_item_price");
    private static final By CART_TITLE = By.className("title");

    public CartPage(WebDriver driver) {
        super(driver);
        log.info("Инициализация страницы корзины");
    }

    @Override
    @Step("Открыть страницу корзины")
    public CartPage open() {
        log.info("Открытие страницы корзины по URL: {}", BASE_URL + "cart.html");
        driver.get(BASE_URL + "cart.html");
        return this;
    }

    @Override
    @Step("Проверить, что страница корзины открыта")
    public CartPage isPageOpened() {
        log.info("Проверка видимости заголовка корзины");
        waitForElementToBeVisible(CART_TITLE);
        return this;
    }

    @Step("Проверить наличие товаров в корзине")
    public boolean isItemPresent() {
        log.info("Проверка наличия товаров в корзине");
        return !driver.findElements(CART_ITEM).isEmpty();
    }

    @Step("Удалить товар из корзины")
    public CartPage removeItem() {
        log.info("Удаление товара из корзины");
        waitForElementToBeClickable(REMOVE_BUTTON);
        driver.findElement(REMOVE_BUTTON).click();
        return this;
    }

    @Step("Нажать кнопку 'Continue Shopping'")
    public ProductsPage continueShopping() {
        log.info("Нажатие кнопки 'Continue Shopping'");
        waitForElementToBeClickable(CONTINUE_SHOPPING_BUTTON);
        driver.findElement(CONTINUE_SHOPPING_BUTTON).click();
        return new ProductsPage(driver);
    }

    @Step("Нажать кнопку 'Checkout'")
    public CheckoutYourInformationPage checkout() {
        log.info("Нажатие кнопки 'Checkout'");
        waitForElementToBeClickable(CHECKOUT_BUTTON);
        driver.findElement(CHECKOUT_BUTTON).click();
        return new CheckoutYourInformationPage(driver);
    }

    @Step("Получить название товара в корзине")
    public String getItemName() {
        log.info("Получение названия товара в корзине");
        return driver.findElement(ITEM_NAME).getText();
    }

    @Step("Получить цену товара в корзине")
    public String getItemPrice() {
        log.info("Получение цены товара в корзине");
        return driver.findElement(ITEM_PRICE).getText();
    }

    @Step("Проверить товар в корзине: {expectedName}")
    public CartPage validateCartItem(String expectedName) {
        log.info("Проверка товара в корзине. Ожидаемое название: {}", expectedName);
        String actualName = getItemName();
        assertEquals(actualName, expectedName,
                "Название товара в корзине не совпадает. Ожидалось: " + expectedName + ", Фактически: " + actualName);
        assertTrue(isItemPresent(), "Товар должен отображаться в корзине");
        return this;
    }
}