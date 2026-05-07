# CodeCraftHub

CodeCraftHub is a beginner-friendly REST API project built with Java and Spring Boot.

The purpose of this project is to help developers learn:
- Spring Boot fundamentals
- REST API development
- CRUD operations
- JSON file handling
- Validation and exception handling

Instead of using a database, all course data is stored in a simple JSON file called `courses.json`.

---

# Features

- Create courses
- View all courses
- View a course by ID
- Update existing courses
- Delete courses
- Store data in a JSON file
- Automatic JSON file creation
- Input validation
- Error handling
- Beginner-friendly code structure

---

# Technologies Used

- Java 25
- Spring Boot 3
- Maven
- Jackson JSON Library

---

# Project Structure

```text
src/main/java/com/codecrafthub/
│
├── CodeCraftHubApplication.java
│
├── controller/
│   └── CourseController.java
│
├── model/
│   └── Course.java
│
└── service/
    └── CourseService.java
```

---

# Installation

## 1. Clone the Repository

```bash
git clone https://github.com/your-username/codecrafthub.git
```

---

## 2. Navigate to the Project Folder

```bash
cd codecrafthub
```

---

## 3. Install Dependencies

Make sure Maven is installed on your machine.

Run:

```bash
mvn clean install
```

---

# Run the Application

Start the Spring Boot application using:

```bash
mvn spring-boot:run
```

---

# Application URL

The API will run at:

```text
http://localhost:8080
```

---

# JSON File Storage

The application automatically creates a file called:

```text
courses.json
```

This file stores all course data.

Example:

```json
[
  {
    "id": 1,
    "name": "Spring Boot Basics",
    "description": "Learn REST APIs",
    "target_date": "2026-06-10",
    "status": "In Progress",
    "created_at": "2026-05-07 10:30:15"
  }
]
```

---

# API Documentation

## Base URL

```text
http://localhost:8080/api/courses
```

---

# 1. Create a Course

## Endpoint

```http
POST /api/courses
```

## Request Body

```json
{
  "name": "Spring Boot Basics",
  "description": "Learn Spring Boot APIs",
  "target_date": "2026-06-15",
  "status": "In Progress"
}
```

## Success Response

```json
{
  "id": 1,
  "name": "Spring Boot Basics",
  "description": "Learn Spring Boot APIs",
  "target_date": "2026-06-15",
  "status": "In Progress",
  "created_at": "2026-05-07 11:20:30"
}
```

## Status Code

```text
201 Created
```

---

# 2. Get All Courses

## Endpoint

```http
GET /api/courses
```

## Example Response

```json
[
  {
    "id": 1,
    "name": "Spring Boot Basics",
    "description": "Learn Spring Boot APIs",
    "target_date": "2026-06-15",
    "status": "In Progress",
    "created_at": "2026-05-07 11:20:30"
  }
]
```

## Status Code

```text
200 OK
```

---

# 3. Get Course by ID

## Endpoint

```http
GET /api/courses/{id}
```

## Example

```http
GET /api/courses/1
```

## Success Response

```json
{
  "id": 1,
  "name": "Spring Boot Basics",
  "description": "Learn Spring Boot APIs",
  "target_date": "2026-06-15",
  "status": "In Progress",
  "created_at": "2026-05-07 11:20:30"
}
```

## Status Code

```text
200 OK
```

---

# 4. Update a Course

## Endpoint

```http
PUT /api/courses/{id}
```

## Example

```http
PUT /api/courses/1
```

## Request Body

```json
{
  "name": "Advanced Spring Boot",
  "description": "Learn advanced Spring Boot topics",
  "target_date": "2026-07-01",
  "status": "Completed"
}
```

## Success Response

```json
{
  "id": 1,
  "name": "Advanced Spring Boot",
  "description": "Learn advanced Spring Boot topics",
  "target_date": "2026-07-01",
  "status": "Completed",
  "created_at": "2026-05-07 11:20:30"
}
```

## Status Code

```text
200 OK
```

---

# 5. Delete a Course

## Endpoint

```http
DELETE /api/courses/{id}
```

## Example

```http
DELETE /api/courses/1
```

## Success Response

```json
{
  "message": "Course deleted successfully"
}
```

## Status Code

```text
200 OK
```

---

# Validation Rules

The API validates all incoming requests.

## Required Fields

- name
- description
- target_date
- status

---

# Allowed Status Values

The `status` field must be exactly one of:

```text
Not Started
In Progress
Completed
```

---

# Example Validation Error

```json
{
  "error": "Invalid status. Allowed values are: Not Started, In Progress, Completed"
}
```

---

# Example Missing Field Error

```json
{
  "name": "Course name is required"
}
```

---

# HTTP Status Codes

| Status Code | Meaning |
|-------------|---------|
| 200 | Success |
| 201 | Created |
| 400 | Bad Request |
| 404 | Not Found |

---

# Testing the API

You can test the API using:
- Postman
- Insomnia
- curl

---

# Example curl Commands

## Create Course

```bash
curl -X POST http://localhost:8080/api/courses \
-H "Content-Type: application/json" \
-d '{
  "name":"Java Basics",
  "description":"Learn Java",
  "target_date":"2026-06-01",
  "status":"Not Started"
}'
```

---

## Get All Courses

```bash
curl http://localhost:8080/api/courses
```

---

## Get Course by ID

```bash
curl http://localhost:8080/api/courses/1
```

---

## Update Course

```bash
curl -X PUT http://localhost:8080/api/courses/1 \
-H "Content-Type: application/json" \
-d '{
  "name":"Spring Boot Advanced",
  "description":"Advanced concepts",
  "target_date":"2026-08-01",
  "status":"Completed"
}'
```

---

## Delete Course

```bash
curl -X DELETE http://localhost:8080/api/courses/1
```

---

# Troubleshooting Guide

---

## 1. Port 8080 Already in Use

### Error

```text
Web server failed to start. Port 8080 was already in use.
```

### Solution

Change the server port.

Create or update:

```text
src/main/resources/application.properties
```

Add:

```properties
server.port=8081
```

---

## 2. Maven Command Not Found

### Error

```text
mvn: command not found
```

### Solution

Install Maven and verify installation:

```bash
mvn -version
```

---

## 3. Java Version Error

### Error

```text
Unsupported Java version
```

### Solution

Install Java 25 or newer.

Verify installation:

```bash
java -version
```

---

## 4. courses.json Not Created

### Cause

The application may not have permission to create files.

### Solution

Make sure the application has write permission in the project directory.

---

## 5. Invalid JSON Request

### Error

```json
{
  "error": "JSON parse error"
}
```

### Solution

Check for:
- Missing commas
- Invalid quotes
- Incorrect JSON formatting
- Wrong date format

Correct date format:

```json
"target_date": "2026-06-01"
```

---

# Future Improvements

Possible future upgrades:
- Swagger/OpenAPI documentation
- Search and filtering
- Pagination
- Authentication and authorization
- MySQL/PostgreSQL integration
- Frontend using React or Angular
- Docker support

---

# Learning Goals

This project helps beginners understand:
- Spring Boot architecture
- REST API design
- CRUD operations
- JSON serialization/deserialization
- Validation
- Exception handling
- File handling in Java

---

# License

This project is open-source and free to use for educational purposes.