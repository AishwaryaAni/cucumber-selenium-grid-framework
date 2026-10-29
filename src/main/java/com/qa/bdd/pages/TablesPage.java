package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/** /tables — client-side sortable data table. */
public class TablesPage extends BasePage {

    private final By headers = By.cssSelector("#table1 thead th");

    public TablesPage(WebDriver driver) {
        super(driver);
    }

    public TablesPage open() {
        open("/tables");
        visible(headers);
        return this;
    }

    public void clickHeader(String columnName) {
        click(By.xpath("//table[@id='table1']//th[normalize-space()='" + columnName + "']"));
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
