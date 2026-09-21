# CRUD Spring Boot

A REST API CRUD application built with Java, Spring Boot, Spring Data JPA, Hibernate, and PostgreSQL.

## Tech Stack

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

## Features

- Create a student
- Get a student by ID
- Get all students
- Update a student
- Hard delete a student
- Soft delete a student

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/students/create` | Create a student |
| GET | `/api/students/get?id={id}` | Get a student |
| GET | `/api/students/getAll` | Get all students |
| PUT | `/api/students/update?id={id}` | Update a student |
| DELETE | `/api/students/delete?id={id}` | Delete a student |
| PATCH | `/api/students/delete-soft?id={id}` | Soft delete a student |

## Run

```bash
./mvnw spring-boot:run
