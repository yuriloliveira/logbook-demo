# Logbook demo

This project is a demo for [Logbook](https://github.com/zalando/logbook).

## Pre-requisites
- Java 17+ (Java 11 for Logbook 2.16)
- Maven
- Spring Boot 3+ (Spring Boot 2 for Logbook 2.16)

*Logbook 3.x.x only works with Spring Boot 3* 

## Running the app locally
To run the app locally, execute
```bash
./mvnw spring-boot:run
```

## API
For the details of the HTTP API, please check the [OpenAPI schema](openapi-schema.yml).

## Advantages
- Easy to setup
- Standard logging structure
- Many formats available (incl. Splunk and json)
- Built-in data-masking features
- Maintained by a big company

## Trade-offs
- Might lead to higher log ingestion
- Might lose logs if exceptions/timeouts are not handled properly