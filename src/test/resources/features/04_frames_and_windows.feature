@regression @context-switching
Feature: Frames and windows
  Content inside iframes and new windows is only reachable
  after switching the driver's context to it.

  Scenario Outline: Read content inside nested frames - <frame>
    Given I am on the nested frames page
    Then the frame "<frame>" should contain "<text>"

    Examples:
      | frame                    | text   |
      | frame-top > frame-left   | LEFT   |
      | frame-top > frame-middle | MIDDLE |
      | frame-top > frame-right  | RIGHT  |
      | frame-bottom             | BOTTOM |

  @smoke
  Scenario: Work in a new window and come back
    Given I am on the multiple windows page
    When I open the link in a new window
    Then the new window title should be "New Window"
    And its heading should be "New Window"
    And closing it returns me to the original window
