Feature: Post sort
  Scenario: post sorting
    Given loggined user and at page inbox
    When sorted by date desc
    Then check is sorted