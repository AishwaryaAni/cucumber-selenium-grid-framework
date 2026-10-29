package com.qa.bdd.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/** /context_menu — right-click on a hot spot raises a JS alert. */
public class ContextMenuPage extends BasePage {

    private final By hotSpot = By.id("hot-spot");

    public ContextMenuPage(WebDriver driver) {
        super(driver);
    }

    public ContextMenuPage open() {
        open("/context_menu");
        visible(hotSpot);
        return this;
    }

    public Alert rightClickHotSpot() {
        new Actions(driver).contextClick(visible(hotSpot)).perform();
        return waitForAlert();
    }
}
