package by.khadasevich.onliner.catalog;

import by.khadasevich.onliner.TestBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.assertj.core.api.SoftAssertions;

public class CatalogSectionsTest extends TestBase {
    private final String ONLINER_URL = "https://www.onliner.by/";
    private final String CATALOG_REF = "Каталог";
    private final String ELECTRONICS_SECTION_XPATH =
            "//span[contains(text(),'Электроника')]";
    private final String COMPUTERS_SECTION_XPATH =
            "//span[contains(text(),'Компьютеры') and contains(text(),'сети')]";
    private final String APPLIANCES_SECTION_XPATH =
            "//span[contains(text(),'Бытовая') and contains(text(),'техника')]";
    private final String BUILDING_SECTION_XPATH =
            "//span[contains(text(),'Стройка') and contains(text(),'ремонт')]";
    private final String HOUSE_SECTION_XPATH =
            "//span[contains(text(),'Дом') and contains(text(),'сад')]";
    private final String AUTO_SECTION_XPATH =
            "//span[contains(text(),'Авто') and contains(text(),'мото')]";
    private final String SPORT_SECTION_XPATH =
            "//span[contains(text(),'Красота') and contains(text(),'спорт')]";
    private final String CHILDREN_SECTION_XPATH =
            "//span[contains(text(),'Детям') and contains(text(),'мамам')]";
    private final String OFFICE_SECTION_XPATH =
            "//span[contains(text(),'Работа') and contains(text(),'офис')]";
    private final String MEAL_SECTION_XPATH =
            "//span[contains(text(),'Еда')]";
    @Test
    @DisplayName("Check catalog has sections")
    public void testCatalogSectionsPresent() {
        // Navigate to onliner.by.
        driver.navigate().to(ONLINER_URL);
        // click catalog
        waitElementToBeClickable(By.linkText(CATALOG_REF)).click();
//        System.out.println("catalog URL: " + driver.getCurrentUrl());
        // check catalog sections
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(
                        isExistElementByXPath(ELECTRONICS_SECTION_XPATH))
                .as("Catalog section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(COMPUTERS_SECTION_XPATH))
                .as("Computers section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(APPLIANCES_SECTION_XPATH))
                .as("Appliances section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(BUILDING_SECTION_XPATH))
                .as("Building section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(HOUSE_SECTION_XPATH))
                .as("House section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(AUTO_SECTION_XPATH))
                .as("Auto section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(SPORT_SECTION_XPATH))
                .as("Sport section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(CHILDREN_SECTION_XPATH))
                .as("Children and mam section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(OFFICE_SECTION_XPATH))
                .as("Office section doesn't exist").isTrue();
        soft.assertThat(
                        isExistElementByXPath(MEAL_SECTION_XPATH))
                .as("Meal section doesn't exist").isTrue();
        soft.assertAll();
    }
}
