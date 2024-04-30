## Gadget Shop Project: Backend

### Overview
This backend system for the Gadget Shop Project manages functionalities related to users, products, and shopping carts, utilizing a variety of modern tools and services for robust, scalable deployment.

### Technologies Used
- **Maven**: Dependency management and project builds.
- **Spring Boot**: Simplifies the development of new Spring applications.
- **MySQL**: Database for storage.
- **Docker**: Containerization of the application for consistent deployment environments.
- **AWS**: Deployment of the application on EC2 instances.
- **SpringFox and Swagger**: API documentation generation and interactive interface.

### Modules and Functionalities
- **User Module**:
  - Create user accounts.
  - Upload profile pictures.
  - Modify user profile details.
- **Category Module**:
  - Create product categories.
  - Upload category pictures.
  - Delete a category along with all its related products.
- **Product Module**:
  - Create products and assign them to categories.
  - Delete products.
- **Cart Module**:
  - Add products to a shopping cart.
  - Update product quantities within the cart.

### Key Features
- **Database Integration**: Utilizes MySQL with Spring Data JPA for database connections.
- **Exception Handling**: Manages various custom exceptions to enhance reliability.
- **Input Validation**: Employs Spring's validation module to ensure input data integrity, such as non-empty fields and character limits.
- **Containerization**: Uses Docker to maintain consistent environments from development to production.
- **Deployment**: Leverages AWS EC2 for hosting and running the application.

### Access and Documentation
- **API Documentation**: Available via [Swagger UI](http://35.154.230.166:8081/swagger-ui/index.html#/)
- **GitHub Repository**: For more details and source code, visit the [GitHub repository](https://github.com/utkarshseth/GadgetShopBackendApp).
