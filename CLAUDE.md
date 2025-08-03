# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Fivenine** is a movie listing Spring Boot application that allows users to manage movie collections. The application is built with Java 17, Spring Boot 3.4.3, JPA/Hibernate, and MySQL.

## Build and Development Commands

### Maven Commands
- **Build**: `mvn clean compile` or `./mvnw clean compile`
- **Run application**: `mvn spring-boot:run` or `./mvnw spring-boot:run`
- **Run tests**: `mvn test` or `./mvnw test`
- **Package**: `mvn package` or `./mvnw package`

### Database Setup
The application expects a MySQL database:
- Database name: `fivenine`
- URL: `jdbc:mysql://localhost:3306/fivenine`
- Username: `root`
- Password: `root`
- DDL mode: `create-drop` (recreates schema on startup)

## Architecture and Patterns

### Package Structure
- **Domain Models**: `it.dogs.fivenine.model.domain.*` - JPA entities
- **DTOs**: `it.dogs.fivenine.model.dto.*` - Data transfer objects organized by feature
- **Controllers**: `it.dogs.fivenine.controller.*` - REST endpoints
- **Services**: `it.dogs.fivenine.service.*` with implementations in `implementation.*`
- **Repositories**: `it.dogs.fivenine.repository.*` - JPA repositories
- **Builders**: `it.dogs.fivenine.builder.*` - Object construction patterns

### Core Domain Models
- **User**: Central entity with username, email, password, and collections
- **Movie**: Basic movie information (title, director, genre, release date)
- **Collection**: User-owned movie collections with types (enum: CollectionType)
- **EmailChangeRequest**: Handles email update workflow with tokens and expiration

### Key Architectural Patterns

#### Service Layer Pattern
- Service interfaces with implementation classes
- Constructor injection for dependencies (no @Autowired in constructors)
- Functional programming style with Stream API usage

#### Builder Pattern
- `UserBuilder` for constructing User entities from DTOs
- Separates object construction logic from business logic

#### Email Change Workflow
The application implements a token-based email change system:
- Email update requests create `EmailChangeRequest` entities
- Tokens with expiration for security
- Separate service (`EmailChangeService`) handles the workflow

### Data Access
- Spring Data JPA repositories
- Entity relationships: User → Collections (OneToMany), Collection → Movies (ManyToMany)
- MySQL with Hibernate auto-DDL in development

### Security Notes
- **No Spring Security currently enabled** (commented out in dependencies)
- Plain text password storage (not production-ready)
- Basic authentication through username/password comparison

## Development Considerations

### Current State
- Application is in active development with recent email change functionality
- Security is minimal - suitable for development/learning environment
- Database schema recreated on each startup (`create-drop`)

### Dependencies
- Spring Boot Starter Web, Data JPA, Validation
- MySQL Connector
- ModelMapper for DTO conversions
- Spring Security dependencies commented out (not currently used)

### Testing
- Standard Spring Boot test structure in `src/test/java`
- Run tests with Maven test commands