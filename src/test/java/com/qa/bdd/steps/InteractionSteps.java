package com.qa.bdd.steps;

import com.qa.bdd.context.TestContext;
import com.qa.bdd.pages.DragAndDropPage;
import com.qa.bdd.pages.FileUploadPage;
import com.qa.bdd.pages.HoversPage;
import com.qa.bdd.pages.KeyPressesPage;
import com.qa.bdd.pages.TablesPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Mouse, keyboard, drag-and-drop, data tables and file upload. */
public class InteractionSteps {

    private final TestContext context;
    private HoversPage hoversPage;
    private KeyPressesPage keyPressesPage;
    private DragAndDropPage dragAndDropPage;
    private TablesPage tablesPage;
    private FileUploadPage uploadPage;
    private String hoverCaption;
    private Path uploadedFile;

    public InteractionSteps(TestContext context) {
        this.context = context;
    }

    // ---------- Hovers ----------
    @Given("I am on the hovers page")
    public void iAmOnTheHoversPage() {
        hoversPage = new HoversPage(context.driver()).open();
    }

    @When("I hover over user {int}")
    public void iHoverOverUser(int position) {
        hoverCaption = hoversPage.hoverAndReadCaption(position);
    }

    @Then("the caption {string} should appear")
    public void theCaptionShouldAppear(String expected) {
        Assert.assertEquals(hoverCaption, expected, "Hover caption");
    }

    // ---------- Keyboard ----------
    @Given("I am on the key presses page")
    public void iAmOnTheKeyPressesPage() {
        keyPressesPage = new KeyPressesPage(context.driver()).open();
    }

    @When("I press the {word} key")
    public void iPressTheKey(String key) {
        keyPressesPage.press(key);
    }

    @Then("the page should report {string}")
    public void thePageShouldReport(String expected) {
        Assert.assertEquals(keyPressesPage.result(), expected, "Key press result");
    }

    // ---------- Drag and drop ----------
    @Given("I am on the drag and drop page")
    public void iAmOnTheDragAndDropPage() {
        dragAndDropPage = new DragAndDropPage(context.driver()).open();
    }

    @When("I drag column A onto column B")
    public void iDragColumnAOntoColumnB() {
        dragAndDropPage.dragAOntoB();
    }

    @Then("the columns should read {string} then {string}")
    public void theColumnsShouldRead(String first, String second) {
        Assert.assertEquals(dragAndDropPage.headerOfFirstColumn(), first, "First column header");
        Assert.assertEquals(dragAndDropPage.headerOfSecondColumn(), second, "Second column header");
    }

    // ---------- Sortable table ----------
    @Given("I am on the data tables page")
    public void iAmOnTheDataTablesPage() {
        tablesPage = new TablesPage(context.driver()).open();
    }

    @When("I sort the table by {string} {int} time(s)")
    public void iSortTheTableBy(String column, int clicks) {
        for (int i = 0; i < clicks; i++) {
            tablesPage.clickHeader(column);
        }
    }

    @Then("the {string} column should be in {word} order")
    public void theColumnShouldBeInOrder(String column, String direction) {
        List<String> actual = tablesPage.columnValues(column);
        List<String> expected = new ArrayList<>(actual);
        Comparator<String> byText = String.CASE_INSENSITIVE_ORDER;
        expected.sort("descending".equalsIgnoreCase(direction) ? byText.reversed() : byText);
        Assert.assertEquals(actual, expected, column + " sorted " + direction);
    }

    // ---------- File upload ----------
    @Given("I am on the file upload page")
    public void iAmOnTheFileUploadPage() {
        uploadPage = new FileUploadPage(context.driver()).open();
    }

    @When("I upload a generated text file")
    public void iUploadAGeneratedTextFile() {
        try {
            uploadedFile = Files.createTempFile("grid-upload-", ".txt");
            Files.writeString(uploadedFile, "Uploaded by the Cucumber + Selenium Grid framework");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        uploadPage.upload(uploadedFile);
    }

    @Then("the upload should be confirmed with the file name")
    public void theUploadShouldBeConfirmedWithTheFileName() {
        Assert.assertEquals(uploadPage.heading(), "File Uploaded!", "Upload heading");
        Assert.assertEquals(uploadPage.uploadedFileName(), uploadedFile.getFileName().toString(), "Uploaded file name");
    }
}
