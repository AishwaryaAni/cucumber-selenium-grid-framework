package com.qa.bdd.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/** /javascript_alerts — native browser dialogs: alert, confirm, prompt. */
public class JavaScriptAlertsPage extends BasePage {

    private final By result = By.id("result");

    public JavaScriptAlertsPage(WebDriver driver) {
        super(driver);
    }

    public JavaScriptAlertsPage open() {
        open("/javascript_alerts");
        visible(button("Alert"));
        return this;
    }

    /** Opens the dialog and hands it back — the caller decides accept / dismiss / type. */
    public Alert trigger(String type) {
        click(button(type));
        return waitForAlert();
    }

    public String result() {
        return text(result);
    }

    private By button(String type) {
        return By.xpath("//button[normalize-space()='Click for JS " + type + "']");
    }
}
