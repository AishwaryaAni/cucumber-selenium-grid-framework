package com.qa.bdd.driver;

import com.qa.bdd.config.ConfigReader;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

/**
 * Builds a WebDriver for the configured browser, either on this machine (execution=local)
 * or on a Selenium Grid (execution=remote). Same tests, same code — only config changes.
 */
public final class DriverFactory {

    private DriverFactory() { }

    public static WebDriver create() {
        String browser = ConfigReader.get("browser").toLowerCase();
        boolean headless = ConfigReader.getBoolean("headless");
        boolean remote = "remote".equalsIgnoreCase(ConfigReader.get("execution"));

        MutableCapabilities options = switch (browser) {
            case "chrome" -> chromeOptions(headless);
            case "firefox" -> firefoxOptions(headless);
            case "edge" -> edgeOptions(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };

        WebDriver driver = remote ? remoteDriver(options) : localDriver(browser, options);

        driver.manage().timeouts().implicitlyWait(Duration.ZERO); // explicit waits only
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1920, 1080));
        return driver;
    }

    private static WebDriver localDriver(String browser, MutableCapabilities options) {
        return switch (browser) {
            case "chrome" -> new ChromeDriver((ChromeOptions) options);
            case "firefox" -> new FirefoxDriver((FirefoxOptions) options);
            default -> new EdgeDriver((EdgeOptions) options);
        };
    }

    private static WebDriver remoteDriver(MutableCapabilities options) {
        try {
            RemoteWebDriver driver = new RemoteWebDriver(new URI(ConfigReader.get("grid.url")).toURL(), options);
            // Streams local files to the grid node, so file-upload tests work remotely too
            driver.setFileDetector(new LocalFileDetector());
            return driver;
        } catch (URISyntaxException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid grid.url: " + ConfigReader.get("grid.url"), e);
        }
    }

    private static ChromeOptions chromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-notifications", "--no-sandbox", "--disable-dev-shm-usage");
        return options;
    }

    private static FirefoxOptions firefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        if (headless) {
            options.addArguments("-headless");
        }
        return options;
    }

    private static EdgeOptions edgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        if (headless) {
            options.addArguments("--headless=new");
        }
        return options;
    }
}
