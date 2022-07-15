package by.khadasevich.onliner.catalog;

import by.khadasevich.onliner.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComputerSectionTest extends TestBase {
    private final String ONLINER_URL = "https://www.onliner.by/";
    private final String CATALOG_REF = "Каталог";
    private final String COMPUTERS_SECTION_XPATH =
            "//span[contains(text(),'Компьютеры') and contains(text(),'сети')]";
    private final String COMPUTERS_SECTION_ITEMS_CLASS =
            "catalog-navigation-list__aside-title";
    List<String> sectionItemNamesForCheck =
            Arrays.asList("Ноутбуки, компьютеры, мониторы", "Комплектующие",
                    "Хранение данных", "Сетевое оборудование");

    @Test
    @DisplayName("Check computers section has items")
    public void testComputerSectionItems() {
        // Navigate to onliner.by.
        driver.navigate().to(ONLINER_URL);
        // click catalog
        waitElementToBeClickable(By.linkText(CATALOG_REF)).click();
//        System.out.println("catalog URL: " + driver.getCurrentUrl());
        // click computer section
        waitElementToBeClickable(By.xpath(COMPUTERS_SECTION_XPATH)).click();
        // get computer section items
        List<WebElement> computerSectionItems =
                waitForExpectedElements(By
                        .className(COMPUTERS_SECTION_ITEMS_CLASS));
        // take items names
        List<String> computerSectionItemsText =
                computerSectionItems
                        .stream()
                        .filter(webElement -> webElement.getText() != null
                                && !webElement.getText().isEmpty())
                        .map(WebElement::getText).toList();
//        computerSectionItemsText.forEach(System.out::println);
        // check needed items names in computerSectionItemsText
        assertThat(computerSectionItemsText)
                .as("Not all needed points computer section has")
                .containsAll(sectionItemNamesForCheck);
    }
}
