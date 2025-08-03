# Fivenine - Movie Collection Manager

A Spring Boot REST API application for managing personal movie collections with user authentication and email confirmation.

## Features

- **User Management**
  - User registration with email confirmation
  - JWT-based authentication
  - Password change with email verification
  - Account activation/deactivation
  - Admin role management

- **Movie Management**
  - Comprehensive movie information storage
  - Collection management per user
  - Multiple collection types support

- **Email System**
  - ProtonMail Bridge integration
  - Registration confirmation emails
  - Password change confirmation emails
  - Email update verification

- **Security & Auditing**
  - BCrypt password hashing
  - Comprehensive audit logging
  - JWT token management
  - Email verification enforcement

## Tech Stack

- **Backend**: Spring Boot 3.4.3, Java 17
- **Database**: MySQL 8.0
- **Security**: BCrypt, JWT
- **Email**: JavaMailSender with ProtonMail Bridge
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven

## Prerequisites

- Java 17 or higher
- MySQL 8.0+
- ProtonMail Bridge (for email functionality)
- Maven 3.6+

## Setup

### 1. Database Setup
```sql
CREATE DATABASE fivenine;
```

### 2. Environment Variables
Set the following environment variables:
```bash
MAIL_USERNAME=your-protonmail@example.com
MAIL_PASSWORD=your-bridge-password
```

### 3. ProtonMail Bridge
- Install and configure ProtonMail Bridge
- Ensure it's running on `127.0.0.1:1025`
- Use Bridge credentials for MAIL_USERNAME and MAIL_PASSWORD

### 4. Application Configuration
The application uses these default settings:
- **Database**: `jdbc:mysql://localhost:3306/fivenine`
- **DB Username**: `root`
- **DB Password**: `root`
- **Server Port**: `8080`

## Running the Application

### Using Maven
```bash
./mvnw spring-boot:run
```

### Using Java
```bash
./mvnw package
java -jar target/fivenine-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### User Authentication
- `POST /users/signUp` - Register new user
- `POST /users/login` - User login
- `GET /users/email/confirm-registration?token=<token>` - Confirm email registration
- `POST /users/email/resend-confirmation` - Resend confirmation email

### Account Management
- `POST /users/{userId}/email/request` - Request email change
- `POST /users/email/confirm` - Confirm email change
- `POST /users/{userId}/password/request` - Request password change
- `POST /users/password/confirm` - Confirm password change
- `POST /users/deactivate` - Deactivate account
- `POST /users/reactivate` - Reactivate account
- `DELETE /users/delete` - Delete account

### Admin Endpoints
- `POST /users/get-all` - Get all users (Admin only)
- `POST /users/{targetUserId}/make-admin` - Make user admin (Admin only)

## Usage Examples

### User Registration
```bash
curl -X POST http://localhost:8080/users/signUp \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","email":"john@example.com","password":"securepass123"}'
```

### User Login (after email confirmation)
```bash
curl -X POST http://localhost:8080/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","password":"securepass123"}'
```

### Resend Confirmation Email
```bash
curl -X POST http://localhost:8080/users/email/resend-confirmation \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","password":"securepass123"}'
```

## Database Schema

### Key Entities
- **Users**: User accounts with authentication info
- **Movies**: Comprehensive movie information
- **Collections**: User-owned movie collections
- **Email Confirmation Requests**: Email verification tokens
- **Email Change Requests**: Email update verification
- **Password Change Requests**: Password update verification
- **Audit Logs**: System activity tracking

## Email Confirmation Flow

1. User registers → Confirmation email sent automatically
2. User clicks email link → Account confirmed via GET endpoint
3. User attempts login → Blocked until email confirmed
4. User can request new confirmation email if needed

## Development Notes

- Database schema auto-updates on startup (`hibernate.ddl-auto=update`)
- Passwords are BCrypt hashed
- JWT tokens used for authentication
- Comprehensive audit logging for all user actions
- Email confirmation required for login

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.