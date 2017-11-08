Feature: login
@gmailtest
Scenario Outline:: gmailTest
Given gmail user launches <google> site
When User enters different values for username
|username|docsample113@gmail.com|
When User enters different values for password
|password|PasswordIndia|

Given gmail user launches <google> site
When User enters different values for username
|username|meera|
When User enters different values for password
|password|PasswordIndia|
Given gmail user launches <google> site
When User enters different values for username
|username|docsample113@gmail.com|
When User enters different values for password
|password|xyz|
Given gmail user launches <google> site
When User enters different values for username
|username|docsample|
When User enters different values for password
|password|xyz|
Given gmail user launches <google> site
When User enters different values for username
|username| |
When User enters different values for password
|password| |



Examples:
      |AutomationID|google|
      |TestUser8Integrator|www.gmail.com|
      
      #Given User Logged in to AWS application with <username> and <password>
#And Logout from AWS application
#Examples:
      #| username|password |
      #| RPHADKE|AutoTesterRP1!|
      
 #@test1
#Scenario Outline:: AWSTest1
#Given User Logged in to AWS application with <username> and <password>
#And Logout from AWS application
#Examples:
      #| username|password |
      #| RPHADKE|AutoTester1!|