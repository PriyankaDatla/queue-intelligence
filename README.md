# Queue Intelligence System

## Overview

Queue Intelligence System is a Spring Boot-based queue management application that enables users to join service queues digitally while allowing administrators to manage queues, counters, and token flow efficiently.

The system provides secure JWT-based authentication and role-based access control to ensure that administrative operations are restricted to authorized users.

## Features

### Authentication & Security

* User Registration
* User Login
* JWT Authentication
* BCrypt Password Encryption
* Role-Based Access Control (ADMIN / CUSTOMER)

### Queue Management

* Create Queue
* View Available Queues
* Join Queue
* Track Queue Position
* View Estimated Wait Time
* Serve Next Token

### Counter Management

* Open Counter
* Close Counter
* Counter Assignment

### Documentation

* Swagger/OpenAPI Integration

## Tech Stack

* Java
* Spring Boot
* Spring Security
* JWT
* MySQL
* Spring Data JPA
* Maven
* Swagger/OpenAPI

## API Documentation

Swagger UI:

http://localhost:8081/swagger-ui/index.html

## Database

MySQL Database with the following entities:

* User
* Queue
* Token
* Counter

## Running the Project

1. Clone the repository
2. Configure MySQL database
3. Update application.properties
4. Run:

mvn install

mvn spring-boot:run

5. Open Swagger UI

http://localhost:8081/swagger-ui/index.html

## Future Enhancements

* React Frontend
* Queue Analytics Dashboard
* Email Notifications
* Real-Time Updates
* Deployment to Cloud
