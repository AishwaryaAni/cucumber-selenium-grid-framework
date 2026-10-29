@regression @data
Feature: Data tables and file upload

  Scenario Outline: Sorting a column - <column> <direction>
    Given I am on the data tables page
    When I sort the table by "<column>" <clicks> time(s)
    Then the "<column>" column should be in <direction> order

    Examples:
      | column     | clicks | direction  |
      | Last Name  | 1      | ascending  |
      | Last Name  | 2      | descending |
      | First Name | 1      | ascending  |

  @smoke
  Scenario: Upload a file, including from a remote Grid node
    Given I am on the file upload page
    When I upload a generated text file
    Then the upload should be confirmed with the file name
