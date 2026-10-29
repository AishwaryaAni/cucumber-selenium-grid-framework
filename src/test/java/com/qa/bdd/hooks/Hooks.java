package com.qa.bdd.hooks;

import com.qa.bdd.config.ConfigReader;
import com.qa.bdd.context.TestContext;
import com.qa.bdd.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/** Browser lifecycle per scenario + evidence on failure. */
public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) { // injected by PicoContainer
        this.context = context;
    }

    @Before(order = 0)
    public void startBrowser() {
        context.setDriver(DriverFactory.create());
        // Same scenario on Chrome and Firefox shows as two distinct results in Allure
        Allure.parameter("browser", ConfigReader.get("browser"));
        Allure.parameter("execution", ConfigReader.get("execution"));
    }

    @After(order = 0)
    public void stopBrowser(Scenario scenario) {
        if (!context.hasDriver()) {
            return;
        }
        try {
            if (scenario.isFailed()) {
                byte[] png = ((TakesScreenshot) context.driver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", "Screenshot at failure");
            }
        } finally {
            context.driver().quit();
        }
    }
}
