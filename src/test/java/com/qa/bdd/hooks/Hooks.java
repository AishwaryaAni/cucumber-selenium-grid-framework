package com.qa.bdd.hooks;

import com.qa.bdd.context.TestContext;
import com.qa.bdd.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
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
        // Browser tagging for the merged Allure report happens in CI (see grid-tests.yml)
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
