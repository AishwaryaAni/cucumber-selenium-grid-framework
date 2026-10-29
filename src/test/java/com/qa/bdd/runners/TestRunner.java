package com.qa.bdd.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Runs every feature through TestNG.
 * Filter by tag at run time: mvn test -Dcucumber.filter.tags="@smoke"
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.qa.bdd.steps", "com.qa.bdd.hooks"},
        plugin = {
                "pretty",
                "summary",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {

    /** parallel = true: scenarios run concurrently (thread count set in testng.xml). */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
