package com.qa.bdd.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.nio.file.Path;

/**
 * /upload — sends a file path to the input. On a remote Grid the browser runs in another
 * container; DriverFactory's LocalFileDetector streams the file there transparently.
 */
public class FileUploadPage extends BasePage {

    private final By fileInput = By.id("file-upload");
    private final By submit = By.id("file-submit");
    private final By heading = By.tagName("h3");
    private final By uploadedFiles = By.id("uploaded-files");

    public FileUploadPage(WebDriver driver) {
        super(driver);
    }

    public FileUploadPage open() {
        open("/upload");
        visible(submit);
        return this;
    }

    public void upload(Path file) {
        driver.findElement(fileInput).sendKeys(file.toAbsolutePath().toString());
        click(submit);
    }

    public String heading() {
        return text(heading);
    }

    public String uploadedFileName() {
        return text(uploadedFiles);
    }
}
