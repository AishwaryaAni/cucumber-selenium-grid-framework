package com.qa.bdd.steps;

import com.qa.bdd.context.TestContext;
import com.qa.bdd.pages.DynamicControlsPage;
import com.qa.bdd.pages.DynamicLoadingPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/** Asynchronous UI: every "eventually" below is an explicit wait, never a sleep. */
public class DynamicContentSteps {

    private final TestContext context;
    private DynamicLoadingPage loadingPage;
    private DynamicControlsPage controlsPage;

    public DynamicContentSteps(TestContext context) {
        this.context = context;
    }

    @Given("I open dynamic loading example {int}")
    public void iOpenDynamicLoadingExample(int example) {
        loadingPage = new DynamicLoadingPage(context.driver()).open(example);
    }

    @When("I start the loading")
    public void iStartTheLoading() {
        loadingPage.start();
    }

    @Then("the text {string} should eventually be displayed")
    public void theTextShouldEventuallyBeDisplayed(String expected) {
        Assert.assertEquals(loadingPage.loadedText(), expected, "Loaded text");
    }

    @Given("I open the dynamic controls page")
    public void iOpenTheDynamicControlsPage() {
        controlsPage = new DynamicControlsPage(context.driver()).open();
    }

    @When("I click the {string} control")
    public void iClickTheControl(String label) {
        controlsPage.clickControl(label);
    }

    @Then("the status should eventually read {string}")
    public void theStatusShouldEventuallyRead(String expected) {
        Assert.assertEquals(controlsPage.statusMessage(), expected, "Status message");
    }

    @Then("the checkbox should no longer exist")
    public void theCheckboxShouldNoLongerExist() {
        Assert.assertTrue(controlsPage.checkboxIsGone(), "Checkbox removed from the DOM");
    }

    @Then("the text input should be enabled")
    public void theTextInputShouldBeEnabled() {
        Assert.assertTrue(controlsPage.inputIsEnabled(), "Input enabled");
    }
}
