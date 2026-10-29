@regression @sync
Feature: Asynchronous content
  The UI must be verified only after it has finished loading —
  explicit waits replace "wait a few seconds and look again".

  @smoke
  Scenario Outline: Late-loading content appears - <case>
    Given I open dynamic loading example <example>
    When I start the loading
    Then the text "Hello World!" should eventually be displayed

    Examples:
      | case                         | example |
      | hidden element made visible  | 1       |
      | element rendered after load  | 2       |

  Scenario: A control is removed asynchronously
    Given I open the dynamic controls page
    When I click the "Remove" control
    Then the status should eventually read "It's gone!"
    And the checkbox should no longer exist

  Scenario: A disabled input is enabled asynchronously
    Given I open the dynamic controls page
    When I click the "Enable" control
    Then the status should eventually read "It's enabled!"
    And the text input should be enabled
