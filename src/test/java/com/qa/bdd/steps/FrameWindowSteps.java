package com.qa.bdd.steps;

import com.qa.bdd.context.TestContext;
import com.qa.bdd.pages.FramesPage;
import com.qa.bdd.pages.WindowsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class FrameWindowSteps {

    private final TestContext context;
    private FramesPage framesPage;
    private WindowsPage windowsPage;

    public FrameWindowSteps(TestContext context) {
        this.context = context;
    }

    @Given("I am on the nested frames page")
    public void iAmOnTheNestedFramesPage() {
        framesPage = new FramesPage(context.driver()).open();
    }

    @Then("the frame {string} should contain {string}")
    public void theFrameShouldContain(String framePath, String expected) {
        Assert.assertEquals(framesPage.textInFramePath(framePath), expected, "Text in " + framePath);
    }

    @Given("I am on the multiple windows page")
    public void iAmOnTheMultipleWindowsPage() {
        windowsPage = new WindowsPage(context.driver()).open();
    }

    @When("I open the link in a new window")
    public void iOpenTheLinkInANewWindow() {
        windowsPage.openNewWindowAndSwitch();
    }

    @Then("the new window title should be {string}")
    public void theNewWindowTitleShouldBe(String expected) {
        Assert.assertEquals(windowsPage.title(), expected, "Window title");
    }

    @Then("its heading should be {string}")
    public void itsHeadingShouldBe(String expected) {
        Assert.assertEquals(windowsPage.headingText(), expected, "Window heading");
    }

    @Then("closing it returns me to the original window")
    public void closingItReturnsMeToTheOriginalWindow() {
        windowsPage.closeAndReturn();
        Assert.assertEquals(windowsPage.windowCount(), 1, "Only the original window remains");
        Assert.assertTrue(context.driver().getCurrentUrl().endsWith("/windows"), "Back on the original page");
    }
}
