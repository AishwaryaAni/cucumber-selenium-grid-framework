@regression @auth
Feature: Login
  As a registered user
  I want to sign in with my credentials
  So that I can reach the secure area

  Background:
    Given I am on the login page

  @smoke
  Scenario: Valid credentials open the secure area
    When I log in as "tomsmith" with password "SuperSecretPassword!"
    Then I should land on the secure area
    And I should see the message "You logged into a secure area!"

  Scenario Outline: Invalid credentials are rejected - <case>
    When I log in as "<username>" with password "<password>"
    Then I should see the message "<message>"
    And I should still be on the login page

    Examples:
      | case           | username | password             | message                   |
      | wrong password | tomsmith | not-the-password     | Your password is invalid! |
      | unknown user   | nobody   | SuperSecretPassword! | Your username is invalid! |
