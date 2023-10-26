# SCare 🚀

Welcome to the scare Application REST API! This project is built using Spring Boot and provides a set of endpoints for managing various aspects of a blog-related application. It includes functionality for managing users, customers, products, services, and more.

## Table of Contents

- [Getting Started](#getting-started) 🛠️
- [Technologies Used](#Technologies-Used) 💻 
- [Endpoints](#endpoints) 🌐
- [Error Handling](#error-handling) ❌
- [Authentication and Authorization](#authentication-and-authorization) 🔐

## Getting Started 🛠️

To run the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/GajananBiradar/Scare.git
1. Navigate to the project directory: cd scare
2. Build and run the project: ./mvnw spring-boot:run
   The application will start, and you can access the endpoints via http://localhost:8080/api.

## Technologies Used 💻

This project is built using a variety of technologies and libraries to provide a robust and efficient backend for the blog platform:

- **Java**: The programming language used to write the application logic.
- **Spring Boot**: The framework that simplifies the setup and development of Spring applications.
- **Spring Data JPA**: A component of the Spring framework for working with relational databases using the Java Persistence API.
- **Spring Security**: A powerful authentication and authorization framework to secure the API endpoints.
- **ModelMapper**: A convenient tool for object mapping, which simplifies data transfer between layers of the application.
- **OpenAPI (Swagger)**: A tool to document and visualize the RESTful API, making it easier to understand and interact with the endpoints.
- **MySQL Database (for development)**: An in-memory database used during development and testing.
- **Maven**: A build automation and project management tool used for building and managing the project's dependencies.

These technologies work together to create a well-structured and efficient backend that supports various functionalities of the blog platform.


 ## Endpoints 🌐
   
The API provides the following main endpoints:

/api/master/users: User management endpoints, including registration, updating, and deletion.

/api/master/brand: Brand-related endpoints, such as fetching brand by ID, updatation, and more.

/api/master/GST: GST endpoints for getting, adding, updating, and deleting gst and hsnCode.

/api/master/Category: Category endpoints for adding, updating, and retrieving category.

For more details on available endpoints and request/response formats, refer to the API Documentation.


## Error Handling ❌
The API uses exception handling to provide meaningful error messages in case of failures. Common errors include resource not found, validation errors, and unauthorized access. The GlobalExceptionHandler class centralizes error handling.


## Authentication and Authorization 🔐
The API supports authentication using JWT (JSON Web Tokens) for securing endpoints. Some endpoints may require specific roles for access. Refer to the Security Configuration for more information.
