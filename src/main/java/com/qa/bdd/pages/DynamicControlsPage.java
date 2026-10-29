package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** /dynamic_controls — elements removed / enabled asynchronously (AJAX). */
public class DynamicControlsPage extends BasePage {

    private final By checkboxButton = By.cssSelector("#checkbox-example button");
    private final By checkbox = By.cssSelector("#checkbox-example input[type='checkbox']");
    private final By inputButton = By.cssSelector("#input-example button");
    private final By textInput = By.cssSelector("#input-example input[type='text']");
    private final By message = By.id("message");

    public DynamicControlsPage(WebDriver driver) {
        super(driver);
    }

    public DynamicControlsPage open() {
        open("/dynamic_controls");
        visible(checkboxButton);
        return this;
    }

    public void clickControl(String label) {
        By button = "Remove".equalsIgnoreCase(label) || "Add".equalsIgnoreCase(label) ? checkboxButton : inputButton;
        click(button);
    }

    public String statusMessage() {
        return text(message);
    }

    /** Waits for the element to leave the DOM, not just to disappear visually. */
    public boolean checkboxIsGone() {
        return wait.until(ExpectedConditions.numberOfElementsToBe(checkbox, 0)).isEmpty();
    }

    public boolean inputIsEnabled() {
        return wait.until(ExpectedConditions.elementToBeClickable(textInput)).isEnabled();
    }
}
