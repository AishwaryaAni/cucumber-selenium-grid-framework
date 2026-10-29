package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

/** /key_presses — reports the last key pressed in the input. */
public class KeyPressesPage extends BasePage {

    private final By target = By.id("target");
    private final By result = By.id("result");

    public KeyPressesPage(WebDriver driver) {
        super(driver);
    }

    public KeyPressesPage open() {
        open("/key_presses");
        visible(target);
        return this;
    }

    /** @param keyName a Selenium {@link Keys} name such as ENTER, ESCAPE, SPACE */
    public void press(String keyName) {
        visible(target).sendKeys(Keys.valueOf(keyName.toUpperCase()));
    }

    public String result() {
        return text(result);
    }
}
