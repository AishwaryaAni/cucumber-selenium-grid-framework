package com.qa.bdd.steps;

import com.qa.bdd.context.TestContext;
import com.qa.bdd.pages.ContextMenuPage;
import com.qa.bdd.pages.JavaScriptAlertsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Alert;
import org.testng.Assert;

/** Native browser dialogs: wait for the alert, inspect it, then accept / dismiss / type. */
public class AlertSteps {

    private final TestContext context;
    private JavaScriptAlertsPage alertsPage;
    private ContextMenuPage contextMenuPage;
    private Alert alert;
    private String alertText;

    public AlertSteps(TestContext context) {
        this.context = context;
    }

    @Given("I am on the JavaScript alerts page")
    public void iAmOnTheJavaScriptAlertsPage() {
        alertsPage = new JavaScriptAlertsPage(context.driver()).open();
    }

    @When("I trigger the JS {word} and accept it")
    public void iTriggerAndAccept(String type) {
        alert = alertsPage.trigger(type);
        alertText = alert.getText();
        alert.accept();
    }

    @When("I trigger the JS {word} and dismiss it")
    public void iTriggerAndDismiss(String type) {
        alert = alertsPage.trigger(type);
        alertText = alert.getText();
        alert.dismiss();
    }

    @When("I trigger the JS Prompt and enter {string}")
    public void iTriggerThePromptAndEnter(String input) {
        alert = alertsPage.trigger("Prompt");
        alertText = alert.getText();
        alert.sendKeys(input);
        alert.accept();
    }

    @Then("the result should read {string}")
    public void theResultShouldRead(String expected) {
        Assert.assertEquals(alertsPage.result(), expected, "Result text");
    }

    @Given("I am on the context menu page")
    public void iAmOnTheContextMenuPage() {
        contextMenuPage = new ContextMenuPage(context.driver()).open();
    }

    @When("I right-click the hot spot")
    public void iRightClickTheHotSpot() {
        alert = contextMenuPage.rightClickHotSpot();
        alertText = alert.getText();
        alert.accept();
    }

    @Then("the alert should have said {string}")
    public void theAlertShouldHaveSaid(String expected) {
        Assert.assertEquals(alertText, expected, "Alert text");
    }
}
