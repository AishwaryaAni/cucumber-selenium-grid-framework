package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * /drag_and_drop — native HTML5 drag-and-drop.
 * WebDriver's Actions.dragAndDrop() moves the mouse but does not fire HTML5 DragEvents,
 * so the columns never swap. The reliable cross-browser fix is to dispatch the drag
 * events in the page with a shared DataTransfer object.
 */
public class DragAndDropPage extends BasePage {

    private static final String HTML5_DRAG_AND_DROP =
            "const [src, tgt] = arguments;"
            + "const dt = new DataTransfer();"
            + "const fire = (el, type) => el.dispatchEvent("
            + "  new DragEvent(type, {bubbles: true, cancelable: true, dataTransfer: dt}));"
            + "fire(src, 'dragstart'); fire(tgt, 'dragenter'); fire(tgt, 'dragover');"
            + "fire(tgt, 'drop'); fire(src, 'dragend');";

    private final By columnA = By.id("column-a");
    private final By columnB = By.id("column-b");

    public DragAndDropPage(WebDriver driver) {
        super(driver);
    }

    public DragAndDropPage open() {
        open("/drag_and_drop");
        visible(columnA);
        return this;
    }

    public void dragAOntoB() {
        WebElement a = visible(columnA);
        WebElement b = visible(columnB);
        ((JavascriptExecutor) driver).executeScript(HTML5_DRAG_AND_DROP, a, b);
    }

    public String headerOfFirstColumn() {
        return text(By.cssSelector("#column-a header"));
    }

    public String headerOfSecondColumn() {
        return text(By.cssSelector("#column-b header"));
    }
}
