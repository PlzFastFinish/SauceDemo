package pages;

import lombok.extern.log4j.Log4j2;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


@Log4j2
public class ProductsPage extends BasePage{
    private static final By TITLE = By.className("title");
    private static final By FIRST_ITEM_NAME = By.cssSelector(".inventory_item_name");
    private static final By FIRST_ITEM_PRICE = By.cssSelector(".inventory_item_price");
    private static final By SHOPPING_CART_LINK = By.className("shopping_cart_link");
    private static final String ADD_TO_CART_PATTERN =
            "//div[text()='%s']/ancestor::div[@class='inventory_item']//button";

    public ProductsPage(WebDriver driver) {
        super(driver);
        log.info("Инициализация страницы продуктов");
    }

    @Override
    @Step("Открыть страницу с продуктами")
    public ProductsPage open() {
        driver.get(BASE_URL + "inventory.html");
        return this;
    }

    @Override
    @Step("Проверить, что страница продуктов открыта")
    public ProductsPage isPageOpened() {
        waitForElementToBeVisible(TITLE);
        return this;
    }

    public boolean isOnProductsPage() {
        log.info("Альтернативная проверка открытия страницы");
        try {
            waitForElementToBeVisible(TITLE);
            return driver.findElement(TITLE).isDisplayed();
        } catch (Exception e) {
            log.info("Страница продуктов не открыта");
            return false;
        }
    }

    @Step("Получить название первого товара в списке")
    public String getFirstProductName() {
        log.info("Получение названия первого товара");
        return driver.findElement(FIRST_ITEM_NAME).getText();
    }

    @Step("Получить цену первого товара в списке")
    public String getFirstProductPrice() {
        log.info("Получение цены первого товара");
        return driver.findElement(FIRST_ITEM_PRICE).getText();
    }

    @Step("Добавить товар {productName} в корзину")
    public ProductsPage addToCart(String productName) {
        log.info("Добавление товара '{}' в корзину", productName);
        String xpath = String.format(ADD_TO_CART_PATTERN, productName);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    @Step("Перейти в корзину")
    public CartPage goToCart() {
        log.info("Переход в корзину");
        driver.findElement(SHOPPING_CART_LINK).click();
        return new CartPage(driver);
    }

    @Step("Проверить данные товара: {expectedName}")
    public ProductsPage validateProductData(String expectedName) {
        log.info("Проверка данных товара '{}'", expectedName);
        String actualName = getFirstProductName();
        String price = getFirstProductPrice().replace("$", "");

        assertEquals(actualName, expectedName,
                "Название товара не совпадает. Ожидалось: " + expectedName + ", Фактически: " + actualName);
        assertTrue(Double.parseDouble(price) > 0,
                "Цена товара должна быть положительной. Фактическая цена: " + price);

        log.info("Проверка товара '{}' завершена успешно", expectedName);
        return this;
    }
}