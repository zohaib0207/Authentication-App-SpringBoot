# 🔐 Authentication App - Spring Boot

A production-style backend authentication and user management application built using **Spring Boot**, **Spring Security**, **JWT Authentication**, **Spring Data JPA**, **Hibernate**, and **MySQL**.

This project is being developed while learning modern backend development practices and focuses on building secure, scalable REST APIs following layered architecture and industry standards.

> 🚧 **Status:** Active Development

---

# ✨ Features Implemented

## Project Setup

- Spring Boot project initialization
- Maven build system
- Layered Architecture
- Environment-based configuration (YAML)

---

## User Management

- User Entity
- Role Entity
- Provider Enum
  - LOCAL
  - GOOGLE
  - FACEBOOK
  - GITHUB
- UUID-based Primary Keys
- Automatic Timestamp Management
    - `@PrePersist`
    - `@PreUpdate`
- Many-to-Many User ↔ Role relationship

---

## Database Layer

- Spring Data JPA
- Hibernate ORM
- MySQL Integration
- Repository Layer
- Custom Query Methods

---

## DTO Layer

- UserDto
- RoleDto
- Request & Response DTOs
- DTO Validation

---

## Service Layer

- User Service
- Authentication Service
- Clean Service Interfaces
- Business Logic Separation

---

## REST APIs

Implemented APIs for:

- User Registration
- User Login
- User CRUD Operations
- Role Management

---

## Validation

- Bean Validation
- Request Validation
- Custom Validation Messages

---

## Exception Handling

- Global Exception Handler
- Custom Exceptions
- Standardized Error Responses

---

## Authentication & Security

### Spring Security

- Custom Security Configuration
- Security Filter Chain
- Password Encoding using BCrypt
- Stateless Authentication

### JWT Authentication

- JWT Generation
- Access Token
- Refresh Token
- JWT Validation
- JWT Parsing
- Token Type Validation
- User Authentication using JWT Filter
- SecurityContext Integration

---

## Current Authentication Flow

```text
Client
    │
    │ Login Request
    ▼
Authentication Service
    │
    ▼
JWT Generated
    │
    ▼
Client Stores Access Token
    │
    ▼
Subsequent Requests
    │
Authorization: Bearer <JWT>
    ▼
JwtAuthenticationFilter
    │
Validate Token
    │
Extract User
    │
Load Authorities
    │
SecurityContext
    ▼
Controller
```

---

# 🏗️ Project Architecture

```text
Controller
      │
      ▼
Service
      │
      ▼
Repository
      │
      ▼
Database
```

Authentication Layer

```text
Client
      │
      ▼
Security Filter Chain
      │
      ▼
JwtAuthenticationFilter
      │
      ▼
SecurityContext
      │
      ▼
Controller
```

---

# 🛠 Tech Stack

### Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Database

- MySQL

### Authentication

- JWT (JSON Web Tokens)
- BCrypt Password Encoder

### Build Tool

- Maven

### Utilities

- Lombok

---

# 📂 Project Structure

```text
src
 ├── config
 ├── controllers
 ├── dtos
 ├── entities
 ├── enums
 ├── exceptions
 ├── helpers
 ├── repositories
 ├── security
 ├── services
 └── AuthApplication
```

---

# 🗄 Database Design

## User

- UUID id
- String name
- String email
- String password
- String image
- Provider provider
- LocalDateTime createdAt
- LocalDateTime updatedAt
- List<Role> roles

---

## Role

- UUID id
- String name

---

## Relationship

```text
User
   ▲
   │ Many-to-Many
   ▼
Role
```

---

# 🚀 Upcoming Features

- Email Verification
- Password Reset
- Refresh Token Rotation
- Logout Endpoint
- Google OAuth Login
- Swagger / OpenAPI Documentation
- Unit Testing (JUnit + Mockito)
- Docker Support
- CI/CD Pipeline
- Production Deployment
- Role-Based Authorization
- Method-Level Security
- API Rate Limiting

---

# 📚 Concepts Covered

This project helped me understand:

- Layered Architecture
- RESTful API Development
- DTO Pattern
- Spring Data JPA
- Hibernate Relationships
- Validation
- Global Exception Handling
- Spring Security
- Authentication vs Authorization
- JWT Authentication
- Security Filter Chain
- SecurityContext
- BCrypt Password Encoding
- Repository Pattern
- Service Layer Design

---

# 🎯 Purpose

The goal of this project is to build a production-style authentication backend while learning the internal working of Spring Boot, Spring Security, and JWT instead of simply following tutorials.

The project is continuously being improved by adding new security features, production-ready practices, and backend best practices.

---

## ⭐ Current Status

✔ User Management

✔ Spring Security

✔ JWT Authentication

✔ Validation

✔ Exception Handling

✔ Layered Architecture

🚧 Refresh Token Improvements

🚧 Swagger Documentation

🚧 Testing

🚧 Docker & Deployment
