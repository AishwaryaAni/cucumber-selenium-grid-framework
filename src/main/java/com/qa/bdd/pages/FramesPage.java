package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** /nested_frames — a frameset inside a frameset. */
public class FramesPage extends BasePage {

    public FramesPage(WebDriver driver) {
        super(driver);
    }

    public FramesPage open() {
        open("/nested_frames");
        return this;
    }

    /**
     * Reads body text from a frame path such as "frame-top > frame-middle".
     * Always starts from the top document and returns there, so later steps are unaffected.
     */
    public String textInFramePath(String path) {
        driver.switchTo().defaultContent();
        try {
            for (String frame : path.split(">")) {
                wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame.trim()));
            }
            return text(By.tagName("body"));
        } finally {
            driver.switchTo().defaultContent();
        }
    }
}
