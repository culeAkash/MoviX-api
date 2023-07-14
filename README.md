# Movie Review System App - Backend Microservices

This repository contains the code for the backend application of the Movie Review System. The backend is built using microservices architecture, with three main microservices: Movie service, User service, and Rating service. The application also utilizes Eureka server for service registry and an API gateway for routing requests. The backend application incorporates unit testing using JUnit and Mockito, as well as logging using SLF4J.

## Microservices

### 1. Movie Service

The Movie service is responsible for managing movie-related functionality, including storing movie information, handling reviews, and providing APIs for movie operations. It interacts with the database to store and retrieve movie data. The service has comprehensive unit tests implemented using JUnit and Mockito to ensure the reliability and correctness of its functionality.

### 2. User Service

The User service handles user-related functionality, such as user management, authentication, and authorization. It provides APIs for user registration, login, and profile management. The service ensures proper authentication and authorization for user-specific operations. Unit tests using JUnit and Mockito are implemented to verify the correctness of the user service's logic.

### 3. Rating Service

The Rating service focuses on managing movie ratings. It allows users to rate movies and retrieves aggregated ratings for movies. The service maintains a record of user ratings and calculates average ratings for movies. JUnit and Mockito are used to write unit tests that validate the rating service's functionality and ensure accurate rating calculations.

## Service Registry - Eureka Server

The application utilizes the Eureka server for service registration and discovery. Eureka server enables the microservices to register themselves and discover other services without hardcoding their locations. It provides a centralized registry of all the microservices in the system, allowing for efficient communication between the services.

## API Gateway

The API Gateway is responsible for routing and filtering requests to the appropriate microservices. It acts as an entry point for external clients and forwards requests to the corresponding microservices. The API Gateway can perform authentication and authorization checks before allowing access to certain routes. In this application, the API Gateway performs role-based authorization and blocks unauthorized requests to protected routes.

## Logging - SLF4J

The backend application utilizes SLF4J (Simple Logging Facade for Java) for logging purposes. SLF4J acts as a facade for various logging frameworks, allowing flexibility in the choice of logging implementation. It provides a unified API for logging across the application. Appropriate logging statements are added throughout the backend codebase to capture important events and assist with debugging and monitoring.

## Project Structure

The project is structured as follows:

```
├── eureka-server
│   ├── src
│   │   └── ...
│   ├── pom.xml
│   └── ...
├── api-gateway
│   ├── src
│   │   └── ...
│   ├── pom.xml
│   └── ...
├── movie-service
│   ├── src
│   │   └── ...
│   ├── pom.xml
│   └── ...
├── user-service
│   ├── src
│   │   └── ...
│   ├── pom.xml
│   └── ...
├── rating-service
│   ├── src
│   │   └── ...
│   ├── pom.xml
│   └── ...
└── pom.xml
```

- `eureka-server`: Contains the Eureka server code and configuration files.
- `api-gateway`: Includes the API Gateway implementation and configuration files.
- `movie-service`: Contains the Movie service code, APIs, database interactions, and unit tests.
- `user-service`: Includes the User service code, authentication, authorization, user management, and unit tests.
- `rating-service`: Contains the Rating service code, APIs for rating movies, rating calculations, and unit tests.
- `pom.xml`: The project's parent pom file that manages the dependencies and common configurations.

## Installation

To run the Movie Review System backend application, follow the steps below:

1. Clone the repository:

   ```
   git clone <repository-url>
   ```

2. Start the Eureka server:

   ```
   cd eureka-server
   ./mvnw spring-boot:run
   ```

3. Start the API Gateway:

   ```
   cd api-gateway
   ./mvnw spring-boot:run
   ```

4. Start the microservices (Movie Service, User Service, and Rating Service):

   ```
   cd movie-service
   ./mvnw test  # Run unit tests
   ./mvnw spring-boot:run
   ```

   ```
   cd user-service
   ./mvnw test  # Run unit tests
   ./mvnw spring-boot:run
   ```

   ```
   cd rating-service
   ./mvnw test  # Run unit tests
   ./mvnw spring-boot:run
   ```

5. Access the application using the appropriate endpoints and routes.

## Contributing

Contributions to this project are welcome. If you encounter any issues or have suggestions for improvement, please open an issue or submit a pull request. Please ensure that any code changes are thoroughly tested and follow the existing code style and guidelines.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use and modify the code as per your requirements.
