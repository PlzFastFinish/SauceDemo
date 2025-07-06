package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;

public class SorterTest extends BaseTest {

    @Test
    public void sortPriceLowToHigh() {
        loginPage.open();
        loginPage.login(LOGIN_STANDARD_USER, CORRECT_PASSWORD);
        Collection<Double> sortedItemsPrices = productsPage.getSortedItemsPricesLowToHigh();
        productsPage.sortItemsByPriceFromLowToHigh();
        Assert.assertEquals(productsPage.getItemsPrices(),
                sortedItemsPrices,
                "Цены отсортированы неверно");
    }
}