package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * /dynamic_loading/{1|2}
 *  Example 1: element is in the DOM but hidden until loading finishes.
 *  Example 2: element is not in the DOM at all until loading finishes.
 * One explicit wait (visibilityOfElementLocated) handles both — no sleeps.
 */
public class DynamicLoadingPage extends BasePage {

    private final By startButton = By.cssSelector("#start button");
    private final By finishText = By.cssSelector("#finish h4");

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
    }

    public DynamicLoadingPage open(int example) {
        open("/dynamic_loading/" + example);
        visible(startButton);
        return this;
    }

    public void start() {
        click(startButton);
    }

    public String loadedText() {
        return text(finishText);
    }
}
