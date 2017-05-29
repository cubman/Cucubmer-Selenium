Feature: Post GMail sort
  Scenario: post GMail sorting
    Given loggined user and at page GMail: ananabramov,p5vsp8gvA
    When GMail sorted by date
    Then check is sorted GMail