# Authentication App - Spring Boot

🚧 Work in Progress – Currently implementing the Service Layer and User Management features.
A backend authentication and user management application built using Spring Boot, Spring Data JPA, Hibernate, and MySQL.

## Current Progress

### Implemented

- Spring Boot project setup
- MySQL database integration
- Hibernate & JPA configuration
- User Entity creation
- Role Entity creation
- Provider Enum (LOCAL, GOOGLE, FACEBOOK, GITHUB)
- Entity Relationships (User ↔ Role)
- Repository Layer
  - UserRepository
  - Custom query methods
- DTO Layer
  - UserDto
  - RoleDto
- Service Layer Structure
  - UserServices interface
  - UserServiceImpl implementation skeleton
- Automatic timestamp management using JPA lifecycle callbacks
  - @PrePersist
  - @PreUpdate

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Lombok

## Architecture

```
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## Database Design

### User

- UUID id
- email
- name
- password
- image
- provider
- createdAt
- updatedAt
- roles

### Role

- UUID id
- name

### Relationship

- User ↔ Role (Many-to-Many)

## Upcoming Features

- User Registration
- User Login
- DTO ↔ Entity Mapping
- Validation
- Exception Handling
- Password Encryption
- Spring Security
- JWT Authentication
- Role-Based Authorization
- REST APIs
- API Documentation
- Deployment

## Learning Goal

This project is being developed to gain a deep understanding of:

- Spring Boot
- JPA & Hibernate
- Layered Architecture
- Authentication & Authorization
- Spring Security
- JWT
- Production-style Backend Development
