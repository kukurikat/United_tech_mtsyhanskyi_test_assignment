Student Registration Form Automation Project

This repository contains automated tests for the Student Registration Form on the DemoQA website. The framework is designed using Java 21, Selenide, and TestNG, adhering to industry-standard architecture patterns.

Technical Stack

Programming Language: Java 21

Test Framework: TestNG

Automation Library: Selenide (Selenium WebDriver wrapper)

Design Pattern: Page Object Model (POM) with Fluent Interface (Method Chaining)

Reporting: Allure Reports

Build System: Maven

Project Structure

The project follows a standard Maven directory layout:

```text
├── .gitignore
├── pom.xml
├── README.md
├── src
│   └── test
│       ├── java
│       │   └── tests
│       │       ├── AnnotationTransformer.java
│       │       ├── BaseTest.java
│       │       ├── RegistrationTest.java
│       │       └── Retry.java
│       │   └── pages
│       │       └── RegistrationPage.java
│       └── resources
│           ├── testing_picture.jpg
│           └── testng.xml
```
Setup and Prerequisites

To run this project locally, ensure you have the following installed:

Java Development Kit (JDK): Version 21.

Apache Maven: Version 3.8 or higher.

Google Chrome: The browser must be installed locally.

Allure Commandline: Required to generate and serve reports. Install via system package manager (e.g., 
```brew install allure``` for macOS or ```scoop install allure``` for Windows).

Configuration
The main configurations of the test execution, including browser parameters, window size, base URL, timeouts, and report screenshots, are configured globally in BaseTest.java.

The test suite execution strategy, parallel execution properties, and listener registrations are managed via the src/test/resources/testng.xml file.

Test Execution Steps
All commands should be executed from the root directory of the project.

Run All Tests (Headless Mode)
By default, the framework is configured to run headless in continuous integration environments. Run the command below to execute the suite:


```mvn clean test```

Run Specific Test XML Suite

To trigger the suite directly referencing the TestNG configuration file:


```mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml```

Generating Reports

The project uses Allure for test reporting. During test execution, raw data is saved to the target/allure-results directory.

1. View Report Locally
   To automatically generate a temporary HTML report and open it in your default web browser, run:

```allure serve target/allure-results```
2. Generate Static Report
   To build a static HTML report inside the allure-report folder, run:

```allure generate target/allure-results --clean```

To open the generated static report, use:

```allure open```

AI Collaboration Statement

Artificial Intelligence (AI) was utilized in an assistant and reviewer capacity during the development of this project. AI collaboration specifically contributed to the following areas:

Page Object Model Refactoring: Assisting in removing assertions (shouldHave and Assert statements) from the Page Object classes to enforce clean architectural boundaries where page classes only expose state and behaviors, leaving assertions strictly inside the test files.

Asynchronous Transition Validation: Solving test flakiness caused by Bootstrap CSS transitions during validation by implementing smart Selenide condition waits for input elements' border-color properties before capturing values.

Data-Driven Assertions: Helping simplify structural assertions in the happy path test case using Java Maps to iterate through expected results dynamically rather than using sequential assert chains.

Retry Analyzer Architecture: Writing the global TestNG IRetryAnalyzer listener and IAnnotationTransformer configurations to handle unstable elements automatically.