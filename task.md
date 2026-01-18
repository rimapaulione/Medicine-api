Assignment: Medicine Management REST API
Goal
The goal of this assignment is to design and implement a RESTful backend application using Spring Boot, following REST principles and a layered architecture:
Controller → Service (Business Logic) → Validator
The assignment focuses especially on business logic implementation in the service layer, not just CRUD operations.
Domain Description
You are building a backend system for managing medicines in a medical or pharmacy-related system. The application stores essential information about medicines and allows controlled operations on them.
The API will be consumed by a frontend application and must behave predictably, efficiently, and according to REST standards.
Functional Requirements
1. Entity
   Create a Medicine entity with 4–5 fields, for example:
   id
   name
   manufacturer
   expiration date
   quantity in stock
   You may adjust the fields if they better fit your design, but keep the total number limited.
2. DTOs (Data Transfer Objects)
   Create request DTOs for:
   Creating a medicine
   Updating a medicine
   Rules:
   Controllers must accept DTOs instead of entities
   DTOs should not contain database identifiers unless strictly necessary
   DTOs should represent only the data needed for the specific request
   Validation annotations may be applied where appropriate
3. REST Endpoints
   Design and implement 5–7 REST endpoints related to medicine management.
   Your API should support operations such as:
   Creating a new medicine
   Updating an existing medicine
   Deleting a medicine by ID
   Retrieving a medicine by ID
   Retrieving all medicines
   Adjusting the quantity of a medicine in stock
   Endpoints must:
   Use correct HTTP methods
   Follow resource-oriented URL design
   Return appropriate HTTP status codes
4. Service Layer (Business Logic)
   The service layer is the core of this assignment.
   The service layer must:
   Contain all business rules and decision-making logic
   Act as the single place where medicines are created, updated, and modified
   Coordinate validation, persistence, and response behavior
   Required Business Logic Examples
   Your service layer should include logic such as:
   • Creation rules
   A medicine cannot be created if a medicine with the same name and manufacturer already exists
   A medicine cannot be created with an expiration date in the past
   Initial stock quantity must be zero or greater
   • Update rules
   Only existing medicines can be updated
   Expiration date cannot be updated to a past date
   Some fields may be immutable (e.g., manufacturer) after creation
   • Stock management
   Medicine quantity cannot become negative
   Separate logic for increasing and decreasing stock
   Prevent reducing stock for expired medicines
   • Deletion rules
   A medicine cannot be deleted if there is still stock available
   Attempting to delete a non-existing medicine should result in a clear error
   The service layer must call the validator service and handle all business decisions before interacting with the repository.
   Controllers must not contain any business logic.
5. Validator Service
   Create a dedicated validator service responsible for enforcing business constraints, such as:
   Field consistency and required values
   Valid expiration dates
   Valid stock quantity values
   Custom business rules that go beyond basic annotations
   The validator service should:
   Be reusable
   Be invoked from the service layer
   Throw meaningful exceptions when validation fails
6. Error Handling and HTTP Responses
   Your application must:
   Return correct HTTP status codes (e.g., 200, 201, 204, 400, 404)
   Handle validation errors gracefully
   Not return unnecessary data (e.g., avoid returning full lists after delete or update operations)
   Provide clear error messages without exposing internal details
   Technical Constraints
   Use Spring Boot
   Follow REST best practices
   Use DTOs for request bodies
   Keep controllers thin
   Keep all business logic in the service layer
   Expected Outcome
   By completing this assignment, you should demonstrate:
   Proper REST API design
   Strong understanding of layered architecture
   Correct separation of concerns
   Meaningful business logic implementation
   Clean, maintainable code structure







   