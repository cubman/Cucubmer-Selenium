Feature: SendMmessage
  Scenario Outline: Send
    Given open browser at page gmail
    When push buttom write and keyboard
    Then enter user "<user email>"
    And enter message subject "<symbols>"
    And enter message text "<message>"
    Then closed window



    Examples:
      | user email                 | symbols                      |  message    |
      | tolik.abramoff@yandex.ru   | K71 K72 K66 K70 K84 K78      | Hello world |