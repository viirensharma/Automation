Feature: Feature for testing Contact Us page

  Scenario: User successfully submits the form with valid email
    Given User opens browser and enters URL
    When User enters first name as "Josh"
    Then User enters the last name as "Warner"
    Then User enters a valid email "josh1234@gmail.com"
    Then User enters "Test Comment" in comments
    Then user clicks  the Submit button
    Then User is redirected to Thank You Page

  ## Multiple Invalid Data which includes email and no last name etc ##
  Scenario Outline: User submits contact form with invalid details
    Given a user is on the Contact Us page
    When the user enters "<FirstName>", "<LastName>", "<Email>", and "<Comment>"
    And User clicks on Submit
    Then User sees the page that data is invalid

    Examples: 
      | FirstName | LastName | Email              | Comment                    |
      |           | Warner   | josh1234@gmail.com | Test Comment               |
      | !@#$$     | *^$^     | 198.168.12         | The entire data is invalid |
      | Josh      |          | josh1234.122.com   | Test Comment               |
