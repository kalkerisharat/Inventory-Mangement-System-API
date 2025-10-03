Inventory Management System API
A backend-heavy RESTful API built with Spring Boot for managing products in a warehouse. This project demonstrates clean code, clear logic, and solid fundamentals, with robust entity modeling, RESTful endpoints, validation, error handling, and interactive documentation.
Features

Core CRUD Operations: Create, Read, Update, and Delete products with attributes: name, description, stockQuantity, and lowStockThreshold.
Inventory Logic: Add or remove stock with validation to prevent negative quantities or insufficient stock.
Bonus Feature: Endpoint to list products below their low stock threshold.
Validation: Enforces data integrity (e.g., non-negative stock, required fields).
Error Handling: Consistent error responses with meaningful messages.
Pagination: Scalable retrieval of products.
Documentation: Interactive API docs via Swagger UI.
Testing: Unit tests for business logic and a Postman collection for API testing.

Tech Stack

Framework: Spring Boot 3.x
Language: Java 17
Database: H2 (in-memory for demo; easily swappable to PostgreSQL/MySQL)
Dependencies: Spring Web, Spring Data JPA, H2 Database, Lombok, Springdoc OpenAPI, Spring Validation
Testing: JUnit 5, Mockito, Postman

Setup Instructions

Clone the Repository:git clone <your-repo-url>
cd inventory-management-system


Prerequisites:
Java 17
Maven 3.8+


Build and Run:mvn clean install
mvn spring-boot:run


The API runs at http://localhost:8080/api/products.
Access H2 console at http://localhost:8080/h2-console (username: sa, password: empty).


Swagger UI: Explore and test endpoints at http://localhost:8080/swagger-ui.html.

API Endpoints
All endpoints are under /api/products. Use Swagger UI for interactive testing or the provided Postman collection.



Method
Endpoint
Description
Example Request Body / Params



GET
/api/products?page=0&size=10
List all products (paginated)
?sort=stockQuantity,desc


GET
/api/products/{id}
Get a product by ID
Path: id=1


POST
/api/products
Create a new product
{ "name": "Widget", "description": "Gadget", "stockQuantity": 100, "lowStockThreshold": 20 }


PUT
/api/products/{id}
Update a product
Path: id=1, Body: { "name": "Updated Widget", "stockQuantity": 150 }


DELETE
/api/products/{id}
Delete a product
Path: id=1


POST
/api/products/{id}/add-stock
Add stock to a product
Path: id=1, Query: quantity=50


POST
/api/products/{id}/remove-stock
Remove stock from a product
Path: id=1, Query: quantity=30


GET
/api/products/low-stock
List products below low stock threshold
None


Example Responses

Success (Create Product):{
    "id": 1,
    "name": "Widget",
    "description": "Gadget",
    "stockQuantity": 100,
    "lowStockThreshold": 20
}


Error (Insufficient Stock):{
    "error": "Insufficient stock: available 100, requested 150"
}



Testing

Unit Tests: Located in src/test/java/dev/sharat/inventory/service/ProductServiceTest.java. Covers stock addition/removal and edge cases (e.g., negative quantities).mvn test


Postman: Import InventoryAPI.postman_collection.json from the repository to test all endpoints, including success and error cases.
Set environment variable: baseUrl = http://localhost:8080/api/products.
Run tests for CRUD, stock operations, and low-stock queries.


Swagger UI: Access at http://localhost:8080/swagger-ui.html for interactive testing and documentation.

Design Decisions

Entity Modeling: Simple Product entity with validation (@NotBlank, @PositiveOrZero) to ensure data integrity.
RESTful Design: Clear, intuitive endpoints with proper HTTP methods and status codes (e.g., 201 for creation, 204 for deletion).
Validation: Bean Validation for inputs, layered with service-level checks for business rules (e.g., no negative stock).
Error Handling: Global @ControllerAdvice for consistent, user-friendly error responses.
Pagination: Used for /api/products to handle scalability.
Documentation: Springdoc OpenAPI for self-documenting APIs, enhancing usability.
Testing: Focused unit tests isolate service logic; Postman collection verifies end-to-end functionality.

Future Enhancements

Add integration tests with @SpringBootTest and Testcontainers.
Implement authentication (e.g., JWT with Spring Security).
Support persistent database (e.g., PostgreSQL).
Add filtering for low-stock endpoint (e.g., by category).

Contact
For questions or feedback, reach out to [your-name] at [your-email] or [your-GitHub-profile].
