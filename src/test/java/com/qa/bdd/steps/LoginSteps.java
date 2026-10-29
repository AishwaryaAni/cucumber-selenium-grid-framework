package com.qa.bdd.steps;

import com.qa.bdd.context.TestContext;
import com.qa.bdd.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private final TestContext context;
    private LoginPage loginPage;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    /** Created on first use — after the @Before hook has started the browser. */
    private LoginPage page() {
        if (loginPage == null) {
            loginPage = new LoginPage(context.driver());
        }
        return loginPage;
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        page().open();
    }

    @When("I log in as {string} with password {string}")
    public void iLogInAs(String username, String password) {
        page().loginAs(username, password);
    }

    @Then("I should land on the secure area")
    public void iShouldLandOnTheSecureArea() {
        page().waitForSecureArea();
    }

    @Then("I should see the message {string}")
    public void iShouldSeeTheMessage(String expected) {
        Assert.assertEquals(page().flashMessage(), expected, "Flash message");
    }

    @Then("I should still be on the login page")
    public void iShouldStillBeOnTheLoginPage() {
        Assert.assertTrue(page().isOnLoginPage(), "User must not be logged in");
    }
}
