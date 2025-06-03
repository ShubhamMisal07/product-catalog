# 🛒 Product Catalog – Spring Boot + JWT + MongoDB

This project implements a secure product catalog backend using **Spring Boot**, **JWT** for authentication, and **MongoDB Atlas** for storing data. Users can register, log in, and access protected product APIs using JWT tokens.

---

## 🚀 Features

- For Authentication (JWT Auth)
- Role-based Authorization (Admin/User)
- Secure Product CRUD APIs
- JWT Token Validation Filter
- MongoDB Atlas Integration
- Tested using Postman

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Tokens)
- MongoDB Atlas
- Maven
- Postman (for API testing)

---

## 📁 Project Structure

product-catalog-backend/
├── src/
│ └── main/
│ ├── java/
│ │ └── com/example/productcatalog/
│ │ ├── config/
│ │ │ └── SecurityConfig.java
│ │ ├── controller/
│ │ │ ├── AuthController.java
│ │ │ └── ProductController.java
│ │ ├── model/
│ │ │ ├── Product.java
│ │ │ └── UserEntity.java
│ │ ├── repository/
│ │ │ ├── ProductRepository.java
│ │ │ └── UserRepository.java
│ │ ├── security/
│ │ │ ├── JwtAuthenticationFilter.java
│ │ │ ├── JwtUtil.java
│ │ │ └── CustomUserDetailsService.java
│ │ ├── service/
│ │ │ └── ProductService.java
│ │ └── ProductCatalogApplication.java
│ └── resources/
│ └── application.properties
└── pom.xml



## 🌐 API Endpoints

| Method | Endpoint            | Description           | Auth Required |
|--------|---------------------|-----------------------|---------------|
| POST   | `auth/login`        | Authenticate user     | ✅ (token)    |
| GET    | `/api/products`     | Get all products      | ✅(admin/User)|
| POST   | `/api/products`     | Add new product       | ✅ (Admin)    |
| PUT    | `/api/products/{id}`| Update product        | ✅ (Admin)    |
| DELETE | `/api/products/{id}`| Delete product        | ✅ (Admin)    |

> Use the JWT token in the `Authorization` header:  

> `Authorization: Bearer <your-token-here>`

---

## 📦 Setup Instructions

1. Clone the Repository

```bash
git clone https://github.com/ShubhamMisal07/product-catalog.git
cd product-catalog


2. Configure MongoDB Atlas
Create a free cluster on MongoDB Atlas

Create a database and collection(you can create manually OR also when you run the app First time it will create the database and with the two users automatically and when the post api/product will send then it will create the Product attribute with it details )

Whitelist your IP and get your connection string

Replace your connection string in src/main/resources/application.properties:

spring.data.mongodb.uri=mongodb+srv://<username>:<password>@<cluster-url>/productdb?retryWrites=true&w=majority


3. Build the Project
.\mvnw.cmd clean

4. Run the Application
.\mvnw.cmd spring-boot:run

Server will start at: http://localhost:8080

🧪 API Testing (Postman)
Import the API collection (optional: create one yourself)

Auth using http://localhost:8080/auth/login to receive a JWT token

Use that token for all secured product APIs 



📚 Resources Referenced
Spring Boot: Solve 403 Error [https://www.geeksforgeeks.org/spring-boot-solve-403-error-in-post-request/] follow this link if you get the 403 error

MongoDB Atlas Documentation (on official site)



🙋‍♂️ Author
Shubham Misal

