@regression @alerts
Feature: Native browser dialogs
  Alerts, confirms and prompts block the page until handled,
  so the automation must switch to them explicitly.

  Background:
    Given I am on the JavaScript alerts page

  @smoke
  Scenario: Accept a simple alert
    When I trigger the JS Alert and accept it
    Then the result should read "You successfully clicked an alert"

  Scenario: Accept a confirmation
    When I trigger the JS Confirm and accept it
    Then the result should read "You clicked: Ok"

  Scenario: Cancel a confirmation
    When I trigger the JS Confirm and dismiss it
    Then the result should read "You clicked: Cancel"

  Scenario: Answer a prompt
    When I trigger the JS Prompt and enter "Hello from the Grid"
    Then the result should read "You entered: Hello from the Grid"
