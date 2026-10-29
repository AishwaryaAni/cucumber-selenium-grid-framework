@regression @interactions
Feature: Advanced mouse and keyboard interactions

  Scenario Outline: Hovering reveals a hidden caption - user <user>
    Given I am on the hovers page
    When I hover over user <user>
    Then the caption "name: user<user>" should appear

    Examples:
      | user |
      | 1    |
      | 2    |
      | 3    |

  Scenario Outline: Special keys are detected - <key>
    Given I am on the key presses page
    When I press the <key> key
    Then the page should report "You entered: <key>"

    # ENTER is deliberately not used: in this input it submits the form and reloads the page
    Examples:
      | key        |
      | BACK_SPACE |
      | ESCAPE     |
      | SPACE      |

  Scenario: Right-click opens a context menu alert
    Given I am on the context menu page
    When I right-click the hot spot
    Then the alert should have said "You selected a context menu"

  @smoke
  Scenario: HTML5 drag and drop swaps the columns
    Given I am on the drag and drop page
    When I drag column A onto column B
    Then the columns should read "B" then "A"
