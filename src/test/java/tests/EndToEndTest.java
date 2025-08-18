package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class EndToEndTest extends BaseTest{

    @Test(description = "Оформление и покупка товара с проверками",
            testName = "Тест покупки")
    @Description("Проверка всего процесса покупки: от добавления товара до подтверждения заказа")
    public void testCompletePurchaseWithChaining() {

        final String PRODUCT_NAME = "Sauce Labs Backpack";
        final String FIRST_NAME = "Fedor";
        final String LAST_NAME = "Shishkin";
        final String ZIP_CODE = "330229";

        String confirmationText = loginStandardUser()
                .isPageOpened()
                .validateProductData(PRODUCT_NAME)
                .addToCart(PRODUCT_NAME)
                .goToCart()
                .isPageOpened()
                .validateCartItem(PRODUCT_NAME)
                .checkout()
                .fillInformation(FIRST_NAME, LAST_NAME, ZIP_CODE)
                .isPageOpened()
                .validateCheckoutData(PRODUCT_NAME)
                .finishCheckout()
                .getConfirmationText();

        assertTrue(confirmationText.contains("Thank you"),
                "Подтверждение заказа должно содержать 'Thank you'. Фактически: " + confirmationText);
    }
}