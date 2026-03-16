# CompanyManagementSystem

This is a comprehensive Company Management System built using a microservices architecture with Spring Boot backend services and Angular frontend applications. The system provides functionalities for employee management, leave management, project management, and authentication.

## Architecture Overview

The system follows a microservices architecture with the following components:

### Backend Services (Spring Boot)

- **service-registryEU**: Eureka Service Registry for service discovery and registration.
- **api-gateway**: Spring Cloud Gateway for routing requests to appropriate microservices.
- **EmployeeManagementSystem**: Handles employee data management including CRUD operations.
- **LeaveManagementSystem**: Manages employee leave requests, balances, and approvals.
- **projectmanagement-service**: Provides project management capabilities with task creation and management.
- **spring-boot-spring-security-jwt-authentication-master**: Authentication service using Spring Security and JWT tokens.

### Frontend Applications (Angular)

- **cmsfrontend1.1**: Updated version of the Angular frontend with additional features like CORS support.

### Configuration Files

- **endpoints.txt**: Documentation of API endpoints and sample requests for testing the services.

## Technologies Used

- **Backend**: Spring Boot 3.x, Spring Cloud, JPA, Eureka, JWT Security
- **Frontend**: Angular 18, Bootstrap, Server-Side Rendering (SSR)
- **Build Tools**: Maven
- **Java Version**: 17

## Getting Started

1. Start the Eureka Service Registry
2. Start the API Gateway
3. Start individual microservices (Employee, Leave, Project Management, Authentication)
4. Start the Angular frontend applications

Each service can be run using `mvnw spring-boot:run` from their respective directories.