# Library Management Application

## Overview
The **Library Management Application** is a Spring Boot project designed to manage a library system effectively.
The application includes APIs for managing books and users, along with features like issuing books, returning them, and paying fines.
It supports both user and admin roles for secure operations.

## Features
### Book Management
- Add, update, delete, and retrieve books.
- Issue books to users and return them.
- Fine payment functionality for overdue books.

### User Management
- Add, update, delete, and retrieve users.

### Security
- Role-based access control:
    - `USER`: Limited access.
    - `ADMIN`: Full access.
- API authentication using Spring Security .
- Used OAuth2.0 along with

### Documentation
- Swagger UI for API documentation.
- Excel file (`api-documentation.xlsx`) with a detailed list of APIs.

---
### Project Structure 


Here's the modified README.md with the correct indentation for the project structure section, so that it matches the overall formatting of your file:

markdown
Copy
# Library Management Application

## Overview
The **Library Management Application** is a Spring Boot project designed to manage a library system effectively.
The application includes APIs for managing books and users, along with features like issuing books, returning them, and paying fines.
It supports both user and admin roles for secure operations.

## Features
### Book Management
- Add, update, delete, and retrieve books.
- Issue books to users and return them.
- Fine payment functionality for overdue books.

### User Management
- Add, update, delete, and retrieve users.

### Security
- Role-based access control:
  - `USER`: Limited access.
  - `ADMIN`: Full access.
- API authentication using Spring Security.
- Used OAuth2.0 along with Keycloak for authentication and authorization.

### Documentation
- Swagger UI for API documentation.
- Excel file (`api-documentation.xlsx`) with a detailed list of APIs.

---

### Project Structure

```
Project Structure -

├── src 
│ ├── main 
│ │ ├── java 
│ │ │ └── com.example.library_manage 
│ │ │ ├── config  
│ │ │ ├── controller 
│ │ │ ├── dto
│ │ │ ├── entity 
│ │ │ ├── exceptions
│ │ │ ├── repository  
│ │ │ ├── service
│ │ ├── resources 
│ │ │ ├── documentation 
│ │ │ ├── application.properties  
│ │ │ ├── application.yaml  
│ │ │ └── ehcache.xml  
│ │ └── README.md  
│ └── test  
└── target  
```

### Key Packages
- **controller**: Contains REST controllers for book and user management.
- **dto**: Data Transfer Objects for interacting with APIs.
- **service**: Business logic implementation.
- **model**: Entity classes representing database tables.

---

## Technologies Used
- **Spring Boot**: Core framework for building the application.
- **Spring Security**: Role-based authentication and authorization.
- **Hibernate/JPA**: Database interaction and ORM.
- **H2 Database**: In-memory database for development and testing.
- **Swagger**: API documentation and testing interface.

---

## Prerequisites
- Java 17 or above
- Maven 3.8+
- IntelliJ IDEA or another Java IDE

---


## API Documentation
The API details are available:
1. **Swagger UI**: Access at `/swagger-ui.html` after running the application.
2. **Excel File**: Located in `src/main/resources/documentation/api-documentation.xlsx`.

---

## Role-Based API Access
| API Endpoint                 | HTTP Method | Roles Allowed    |
|------------------------------|-------------|------------------|
| `/api/v1/books`              | GET         | USER, ADMIN      |
| `/api/v1/books`              | POST        | ADMIN            |
| `/api/v1/books/{id}`         | PUT         | ADMIN            |
| `/api/v1/books/{id}`         | DELETE      | ADMIN            |
| `/api/v1/users`              | GET         | ADMIN            |
| `/api/v1/users`              | POST        | ADMIN            |
| `/api/v1/users/{id}`         | PUT         | ADMIN            |
| `/api/v1/users/{id}`         | DELETE      | ADMIN            |

---


# Steps to Set Up and Use Keycloak with Docker and Postman


## 1. Run Keycloak using Docker

1. Pull the Keycloak Docker image:

docker pull quay.io/keycloak/keycloak:latest


2. Run the Keycloak container:

docker run -d --name keycloak \
-e KEYCLOAK_ADMIN=admin \
-e KEYCLOAK_ADMIN_PASSWORD=admin \
-p 8080:8080 \
quay.io/keycloak/keycloak:latest start-dev

Key Point - You can replace admin and admin with your desired admin username and password.

3. Access the Keycloak Admin Console:

Open your browser and go to: http://localhost:8080/admin/ \
( You can change the port no. here , when you run the docker image , FYI I am using 9091 )

## 2. Create a Realm
Navigate to Manage Realms > Add Realm.
Enter the name for your realm (e.g., LibraryManagement) and click Create.

## 3. Create a Client
In the newly created realm, go to Clients > Create Client.
Enter a client ID (e.g., library-app) and choose OpenID Connect as the protocol.
Click Save.
Configure the client:
Access Type: Set to confidential.
Root URL: Enter your application URL (e.g., http://localhost:8080).
Save the changes.

## 4. Define Roles
Go to Roles > Add Role.
Create roles such as:
ADMIN
USER
Save each role.

## 5. Create Users
Go to Users > Add User.
Enter user details (e.g., username, email) and click Save.
Go to the Credentials tab:
Set a password for the user.
Toggle Temporary to OFF to avoid mandatory password reset.
Go to the Role Mappings tab:
Assign roles (e.g., ADMIN, USER) to the user.
\

## Testing through Postman 

You can use Postman , and perform the Authorization by going down 
in the Authorization Tab & Select OAuth2.0 , and you can generate the 
Auth code , through the Keycloak .