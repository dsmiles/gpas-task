# GPAS API Sample Tests

This repository contains sample integration tests using REST-assured against the GPAS API (Global Pathogen Analysis Service).

API documentation: [GPAS API Documentation](https://dev.portal.gpas.world/redoc)

I've simply picked a few endpoints and written a few tests to demonstrate making requests and checking responses.

## Tested Endpoints

- Auth endpoint
- Admin endpoint (limited testing due to lack of credentials)
- Batch endpoint (limited testing due to lack of credentials)
- CLI Version Endpoint

These tests are implemented using JUnit 5, RestAssured, and Hamcrest for assertion.

### Implementation Notes

I've kept the examples simple for clarity but would typically abstract request and response payloads using POJOs. For larger projects, I would separate request and response handling into their own classes to remove code duplication.

**Note:**

Since I don't have a username and password, I couldn't execute the Admin or Batch tests. However, I was able to execute some of the Auth tests and the CLI Version test.

### Python Code

I realise that GPAS have a preference for Python, but I am confident that I could pick it up quickly if required.

As an exercise, I've converted the Java JUnit tests to Python using Pytest. You can find the Python tests in the `python` directory.


## Components Used

This example was written using the following:

- Java 17
- JUnit 5
- REST-assured [here](https://rest-assured.io)
- Hamcrest Matchers
- Maven
- Git

## Usage

To run the test class against the authentication endpoint, follow these steps:

1. Clone the repository:
```
git clone https://github.com/dsmiles/gpas-task.git
cd gpas-task
```

2. Run Maven test command:
```
mvn test
```
