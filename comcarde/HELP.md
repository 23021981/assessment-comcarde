# To check the stock levels for the products sold by a retailer, 
#and apply a number of ‘rules’ before returning advice for which products should be ordered

## Pre-Requisite Software:
* Apache Maven
* JDK 8
* Lombok plugin for compilation into IntelliJ IDEA.
* Postman.

## Implementation details
* I have implemented stock check rule engine as a REST API with Spring Boot 
and Maven framework.
* Written the rule, i have used expression language (MVEL) and some domain specific keywords.
* I have store the rules into H2Database.
* I have implemented an abstract Inference Engine, which use to implement the different 
domain-specific Inference Engine.
* The testing do with postman for API test.
   
## In Memory database used H2Database.
* Used to store the rules into Rule table through rest API call and store recommended  
information after stock check into Audit table.
* Tables are RULES and STOCK_CHECK_AUDIT.
* URL to H2 UI console. http://localhost:8070/h2
         
## List of End-Point
* http://localhost:8070/retailer/addRule (POST)
* http://localhost:8070/retailer/checkRule (POST)
* http://localhost:8070/retailer/getAllRules (GET)
* http://localhost:8070/retailer/getAllRules/ORDER (GET)

## Reporting with code coverage
* Run below command to generate reporting:
Eg: /Users/atulkumar/workspace/assessment> mvn cobertura:cobertura
* You can find report inside target folder under jaococo-ut folder (index.html).

## Stock Rule logic:
### Minimum stock level 
* We have created the logic and store as Input and Output into RULES's CONDITION and ACTION table.
### Products to be marked as blocked
* We have store the logic as as Input and Output into RULES's CONDITION and ACTION table.
### Add a rule to order an additional volume of a certain product. 
* Provided Rest end point for additional rule. Sample rquest JOSN is in resources/RuleInput.txt 
#### RULES table description
Namespace: Use to identify or club the same types of rules of a domain into one.
Id: Each rule could be identified with some unique Id.
Condition or pattern: Rule’s condition in the MVEL + DSL form.
Action: Rule’s action in the MVEL + DSL form.
Priority: It will use to decide which rule should execute first on input data.
Description: Show the description of the rule.

### Audit of the stock-check and recommended purchase history.
* Created STOCK_CHECK_AUDIT.
* RECOMMENDED column belongs to if any advice after rule check. It is boolean.
* STOCK_CHECK column deals with if any rule check happened or not. It is boolean.

### Important commands
* Run the testcase: mvn clean test
Copy the JAR in local machine and execute the below command from command prompt or terminal.
* Start Spring boot application: java -jar retailer-1.0.0.jar

### Future Enhancement
* Include Swagger for API documentation.
* Use Java 9 features to concise the line of codes.
* More test coverage and data validation.
* We can write cucumber Automation test case (BDD).