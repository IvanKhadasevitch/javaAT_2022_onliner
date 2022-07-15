package by.khadasevich.onliner.catalog;

import by.khadasevich.onliner.TestBase;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.regex.Pattern;

public class ComputerAccessoriesTest extends TestBase {
    private final String ONLINER_URL = "https://www.onliner.by/";
    private final String CATALOG_REF = "Каталог";
    private final String COMPUTERS_SECTION_XPATH =
           "//span[contains(text(),'Компьютеры') and contains(text(),'сети')]";
    private final String COMPUTERS_ACCESSORIES_XPATH =
            "//div[contains(text(),'Комплектующие')]";
    private final String COMPUTERS_ACCESSORIES_NAME_XPATH =
            "//div[contains(@class,'aside-item_active')]"
                   + "/div/div/a/span/span[contains(@class,'dropdown-title')]";
    private final String COMPUTERS_ACCESSORIES_DESCRIPTION_XPATH =
            "//div[contains(@class,'aside-item_active')]"
             + "/div/div/a/span/span[contains(@class,'dropdown-description')]";
    private final String VALUABLE_DESCRIPTION_PATTERN =
            "^[0-9,]+\\sтовар(а)*(ов)*[\\r\\n]+от\\s[0-9,]+\\sр\\.$";
    @Test
    @DisplayName("Check computer's accessories have name, quantity, price")
    public void testAccessoriesHaveNameQuantityPrise() {
        // Navigate to onliner.by.
        driver.navigate().to(ONLINER_URL);
        // click catalog
        waitElementToBeClickable(By.linkText(CATALOG_REF)).click();
//        System.out.println("catalog URL: " + driver.getCurrentUrl());
        // click computer section
        waitElementToBeClickable(By.xpath(COMPUTERS_SECTION_XPATH)).click();
        // click accessories of computer section
        waitElementToBeClickable(By.xpath(COMPUTERS_ACCESSORIES_XPATH)).click();
        // take webElements contains "accessories" names
        List<WebElement> accessoriesNameElements =
                waitForExpectedElements(By
                        .xpath(COMPUTERS_ACCESSORIES_NAME_XPATH));
        // take valuable accessories Names
        List<String> valuableAccessoriesNames =
                accessoriesNameElements.stream()
                    .map(WebElement::getText)
                    .filter(name -> name != null && !name.isEmpty())
                    .toList();
//        System.out.println("size: " + valuableAccessoriesNames.size());
//        valuableAccessoriesNames.forEach(System.out::println);
        // take webElements contains "accessories" quantity and price
        List<WebElement> accessoriesDescriptionElements =
                waitForExpectedElements(By
                        .xpath(COMPUTERS_ACCESSORIES_DESCRIPTION_XPATH));
        // take valuable accessories description
        List<String> valuableAccessoriesDescriptions =
                accessoriesDescriptionElements.stream()
                    .map(WebElement::getText)
                    .filter(description ->
                            description != null && !description.isEmpty())
                    .filter(this::isValuableAccessoriesDescription)
                    .toList();
//        System.out.println("size: " + valuableAccessoriesDescriptions.size());
//        valuableAccessoriesDescriptions.forEach(System.out::println);
        // check that all accessories have names and
        // descriptions (price and quantity)
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(accessoriesNameElements)
                .as("Not all accessories have name or description")
                .hasSameSizeAs(accessoriesDescriptionElements)
                .hasSameSizeAs(valuableAccessoriesNames)
                .hasSameSizeAs(valuableAccessoriesDescriptions);
        soft.assertAll();

    }
    private boolean isValuableAccessoriesDescription (String description) {
        return Pattern.matches(VALUABLE_DESCRIPTION_PATTERN, description);
    }
}
