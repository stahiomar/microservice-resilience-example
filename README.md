
# Microservices with Circuit Breaker, Eureka, API Gateway, and Cloud Config

This project demonstrates how to build a microservices-based architecture using Spring Boot with features such as service discovery (Eureka), API Gateway, Circuit Breaker for fault tolerance, and centralized configuration using Spring Cloud Config.

## Overview

The system consists of two microservices:
- **microservice-produits**: A microservice responsible for managing products.
- **microservice-commandes**: A microservice responsible for handling customer orders.

The system includes:
- **Eureka** for service discovery.
- **API Gateway** to route requests to the appropriate microservice.
- **Circuit Breaker** (using Resilience4j) to handle failures and simulate service delays.
- **Spring Cloud Config** to manage externalized configurations.

## Features

1. **Simulated Delay**: A delay of 5 seconds is introduced in the `microservice-produits` to simulate a slow service.
2. **Circuit Breaker**: Resilience4j is used to implement a Circuit Breaker pattern. If `microservice-produits` is delayed or unavailable, the `microservice-commandes` service will fall back to a predefined response.
3. **Eureka Discovery**: Both microservices are registered with Eureka for service discovery.
4. **API Gateway**: The API Gateway routes the requests to the appropriate microservices via service discovery.
5. **Spring Cloud Config**: Centralized configuration management for both services.

## Getting Started

### 1. **Run Eureka Server**

Eureka is responsible for service registration and discovery. Run the Eureka Server first.

- Navigate to the `service-register` project directory.
- Run the application using your IDE or with the following command:

```bash
mvn spring-boot:run
```

Eureka Server will be available at `http://localhost:8761`.

### 2. **Run Config Server**

The Config Server manages centralized configuration for both services.

- Navigate to the `service-config` project directory.
- Run the application using your IDE or with the following command:

```bash
mvn spring-boot:run
```

Make sure the configuration properties are available at `http://localhost:9001`.

### 3. **Run Microservices**

Start both the **microservice-produits** and **microservice-commandes** projects:

#### microservice-produits:
- Navigate to the `microservice-produits` project directory.
- Run the application using your IDE or with the following command:

```bash
mvn spring-boot:run
```

This microservice exposes an endpoint `GET /produits/delayed` that simulates a delay of 5 seconds.

#### microservice-commandes:
- Navigate to the `microservice-commandes` project directory.
- Run the application using your IDE or with the following command:

```bash
mvn spring-boot:run
```

This microservice calls `GET /produits` from **microservice-produits** using a Circuit Breaker. If the service is delayed or unavailable, a fallback response will be returned.

### 4. **Run API Gateway**

API Gateway acts as a reverse proxy to route requests to the appropriate microservice.

- Navigate to the `gateway` project directory.
- Run the application using your IDE or with the following command:

```bash
mvn spring-boot:run
```

The Gateway will route requests to either `microservice-commandes` or `microservice-produits`.

### 5. **Access the Application**

- **API Gateway**: The gateway can be accessed at `http://localhost:9005/`.
  - Access the commandes: `http://localhost:9005/microservice-commandes/commandes/produits`
  - Access the produits: `http://localhost:9005/microservice-products/produits`

### 6. **Circuit Breaker in Action**

- When calling `http://localhost:9005/microservice-commandes/commandes/produits`, if **microservice-produits** is delayed (due to the `Thread.sleep(5000)`), the Circuit Breaker will trigger, and the fallback response will be shown:

  ```json
  ["Service unavailable, try again later"]
  ```

  The fallback mechanism helps in ensuring that the system remains resilient even in case of temporary service disruptions.

## Key Technologies Used

- **Eureka**: For service registration and discovery.
- **Spring Cloud Gateway**: To route and filter requests to microservices.
- **Spring Cloud Config**: For centralized configuration management.
- **Resilience4j**: For fault tolerance, with Circuit Breaker pattern.
- **Spring Boot**: For building the microservices.
- **WebClient**: For making asynchronous HTTP calls between services.
- **Spring Boot Actuator**: For monitoring and health checks.
