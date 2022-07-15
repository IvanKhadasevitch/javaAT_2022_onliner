package by.khadasevich.onliner;

import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

public class TestBase {
    private final String DRIVER_PATH =
            "D:\\java-libraries\\chromedriver_win-103\\chromedriver.exe";
    public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    public WebDriver driver;
    public Wait<WebDriver> wait;
    public TestBase() {
        // Optional, if not specified, WebDriver will search your path for
        // chromedriver.exe
        System.setProperty("webdriver.chrome.driver", DRIVER_PATH);
    }

    @BeforeEach
    public void start() {
        System.setOut(new PrintStream(System.out, true,
                StandardCharsets.UTF_8));
        System.out.println("TestBase.start");
        // init driver only once
        if (threadDriver.get() != null) {
            driver = threadDriver.get();
            System.out.println("driver: " + driver);
            wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(Duration.ofSeconds(12))
                    .pollingEvery(Duration.ofSeconds(2))
                    .ignoring(NoSuchElementException.class);
            return;
        }
        driver = new ChromeDriver();
        // set implicitly wait for driver 5 seconds; and other timeouts
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(12));
        System.out.println(((HasCapabilities) driver).getCapabilities());
        // save driver in thread local
        threadDriver.set(driver);

        // Waiting 12 seconds for an element to be present on the page,
        // checking for its presence once every 2 seconds.
        wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(12))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);

        // shut down hook to finish work once after all methods are done
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {driver.quit(); driver = null;
                    System.out.println("Driver shut down");})
        );
    }
    protected boolean isExistElementByXPath(String xPath) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
            return true;
        } catch (TimeoutException exp) {
            return false;
        }
    }
    protected List<WebElement> waitForExpectedElements(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        return driver.findElements(by);
    }
    protected WebElement waitElementToBeClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }
}
