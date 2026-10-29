package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/** /hovers — captions only become visible on mouse-over (Actions API). */
public class HoversPage extends BasePage {

    private final By figures = By.cssSelector(".figure");

    public HoversPage(WebDriver driver) {
        super(driver);
    }

    public HoversPage open() {
        open("/hovers");
        visible(figures);
        return this;
    }

    /** @param position 1-based, as a tester would count on screen */
    public String hoverAndReadCaption(int position) {
        List<WebElement> all = driver.findElements(figures);
        WebElement figure = all.get(position - 1);
        new Actions(driver).moveToElement(figure).perform();
        WebElement caption = figure.findElement(By.cssSelector(".figcaption h5"));
        return wait.until(ExpectedConditions.visibilityOf(caption)).getText().trim();
    }
}
