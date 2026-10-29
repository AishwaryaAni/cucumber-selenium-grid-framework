package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** /windows — link that opens a new browser window/tab. */
public class WindowsPage extends BasePage {

    private final By newWindowLink = By.linkText("Click Here");
    private final By heading = By.tagName("h3");
    private String originalHandle;

    public WindowsPage(WebDriver driver) {
        super(driver);
    }

    public WindowsPage open() {
        open("/windows");
        visible(newWindowLink);
        return this;
    }

    /** Remembers the current window, opens the new one and switches into it. */
    public void openNewWindowAndSwitch() {
        originalHandle = driver.getWindowHandle();
        int before = driver.getWindowHandles().size();
        click(newWindowLink);
        wait.until(ExpectedConditions.numberOfWindowsToBe(before + 1));
        String newHandle = driver.getWindowHandles().stream()
                .filter(h -> !h.equals(originalHandle))
                .findFirst()
                .orElseThrow();
        driver.switchTo().window(newHandle);
        wait.until(ExpectedConditions.titleIs("New Window"));
    }

    public String title() {
        return driver.getTitle();
    }

    public String headingText() {
        return text(heading);
    }

    public void closeAndReturn() {
        driver.close();
        driver.switchTo().window(originalHandle);
    }

    public int windowCount() {
        return driver.getWindowHandles().size();
    }
}
