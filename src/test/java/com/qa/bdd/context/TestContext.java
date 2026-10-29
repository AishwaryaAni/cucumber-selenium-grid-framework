package com.qa.bdd.context;

import org.openqa.selenium.WebDriver;

/**
 * Scenario-scoped state, injected by PicoContainer into Hooks and every step class.
 * One instance per scenario = one browser per scenario = safe parallel execution
 * without static/ThreadLocal driver plumbing.
 */
public class TestContext {

    private WebDriver driver;

    public WebDriver driver() {
        if (driver == null) {
            throw new IllegalStateException("Driver not started — is the @Before hook running?");
        }
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public boolean hasDriver() {
        return driver != null;
    }
}
