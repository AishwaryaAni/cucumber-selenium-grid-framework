package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/** /tables — client-side sortable data table. */
public class TablesPage extends BasePage {

    private final By headers = By.cssSelector("#table1 thead th");

    public TablesPage(WebDriver driver) {
        super(driver);
    }

    /** The sorter script marks every header with class "header" once it is initialised. */
    private final By initialisedHeaders = By.cssSelector("#table1 thead th.header");

    public TablesPage open() {
        open("/tables");
        visible(headers);
        // Clicking before the sort script attaches is silently ignored (seen on Firefox)
        wait.until(ExpectedConditions.presenceOfElementLocated(initialisedHeaders));
        return this;
    }

    /** Clicks a header and waits until its sort-state class changes, i.e. the sort really happened. */
    public void clickHeader(String columnName) {
        By header = By.xpath("//table[@id='table1']//th[normalize-space()='" + columnName + "']");
        String before = String.valueOf(visible(header).getAttribute("class"));
        click(header);
        wait.until(d -> !before.equals(String.valueOf(d.findElement(header).getAttribute("class"))));
    }

    /** All cell values of a column, top to bottom, as the user currently sees them. */
    public List<String> columnValues(String columnName) {
        List<String> names = driver.findElements(headers).stream().map(WebElement::getText).map(String::trim).toList();
        int index = names.indexOf(columnName);
        if (index < 0) {
            throw new IllegalArgumentException("No column '" + columnName + "' in " + names);
        }
        return driver.findElements(By.cssSelector("#table1 tbody tr td:nth-child(" + (index + 1) + ")"))
                .stream().map(WebElement::getText).map(String::trim).toList();
    }
}
