Feature: login user
  Scenario Outline: Log in test
    Given open browser at page google.ru
    When push buttom login enter "<user name>"
    And enter "<user password>"
    Then enter google "<pass status>"

    Examples:
      | user name   | user password | pass status |
      | ananabramov | p5vsp8gvA     | ok          |
      | cubman      | rdffgdf       | wrong       |
