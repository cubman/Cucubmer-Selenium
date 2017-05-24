Feature: Post sort
  Scenario: post sorting
    Given loggined user and at page inbox: ananabramov,p5vsp8gvA,https://mail.google.com/mail/u/0/?ik=9a3ca749ae&view=om&permmsgid=msg-f:
    When sorted by date desc
    Then check is sorted