package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** /login — form submission + flash message. */
public class LoginPage extends BasePage {

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        open("/login");
        visible(loginButton);
        return this;
    }

    public void loginAs(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
    }

    /** Flash text includes a "×" close glyph — strip it so assertions compare the message only. */
    public String flashMessage() {
        return text(flash).replace("×", "").trim();
    }

    public void waitForSecureArea() {
        wait.until(ExpectedConditions.urlContains("/secure"));
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().endsWith("/login") && !driver.findElements(loginButton).isEmpty();
    }
}
