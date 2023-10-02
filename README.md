# Erudite-Ticketing-System

The Erudite Ticketing System is a robust, web-based application designed to simplify and optimize event management. This system offers a seamless experience by efficiently handling event creation and ticket purchase. 

## Prerequisites
Before you begin, ensure you have the following tools installed:

- Java 17
- Spring Boot 3
- Spring Security
- MySQL database
- Gradle Groovy

## Table of Contents

- [Dependencies](#dependencies)
- [Installation](#installation)
- [Database Configuration](#database-configuration)
- [JSON Web Token Configuration (JWT)](#json-web-token-configuration-jwt)
- [Mail Configuration](#mail-configuration)
- [Usage/Examples](#usageexamples)
- [Key Highlights / Uniqueness](#key-highlights--uniqueness)
- [Challenging Moments](#challenging-moments)
- [Further Improvements](#further-improvements)
- [Special Gratitude](#special-gratitude)
- [Author](#author)

## Dependencies

The following dependencies are required:
- Spring Web
- Spring Data Jpa
- Spring WebFlux
- Project Lombok
- Spring Validation
- Http Client
- Spring Doc & Open API
- Java Mail Service
- Spring Security
- JSON Web Token (JWT)
- MySQL
- Spring Boot Dev Tools

## Installation

1. Clone the repository to your local machine
2. Build the project.
3. Configure the application.

The configuration for this API is stored in the `application.properties` file. To configure the API or make changes to its behavior, you can edit this file.

## Database Configuration

```properties
spring.datasource.url=your-database-url
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## JSON Web Token Configuration (JWT)

```properties
application.security.jwt.secret-key=
application.security.jwt.expiration=
application.security.jwt.refresh-token.expiration=
```

## Mail Configuration

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email
spring.mail.password=google-given-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.ssl=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## Key Highlights / Uniqueness

- This software prioritizes security by requiring user email verification before allowing login.
- Special features, such as password reset for forgotten passwords and password changes for authenticated users, have been implemented.
- Emails are generated upon successful event creation and ticket purchase.
- Tight mappings have been minimized through the use of Java 8 streams and lambda expressions.
- The software enforces strong password verification, including a minimum length of 8 characters, a maximum length of 16 characters, and the inclusion of special characters.
- Users can easily search for available events directly from the site.
- This software is built on Java 8 features, utilizing streams and lambda expressions.
- Sample test cases for Quality Assurances as the software was built based on best practices
- Possible Exceptions that may arise has been catered for in the Application Exception Handler Class

## Challenging Moments

- Building this application required analytical skills similar to those needed for platforms like Eventbrite or eventprime, due to complex routing.

## Further Improvements

The project could benefit from a dedicated service for managing full events activities

## Special Gratitude

- [@i-Africa-Prudential-Plc](https://www.linkedin.com/company/africa-prudential-plc/mycompany/)
- [@i-Academy.org](https://www.linkedin.com/company/iacademybyap/)
- [@Obong](https://www.linkedin.com/in/obong-idiong-6a113829/)
- [@Ivy](https://www.linkedin.com/in/ivyikpemembakwem/)
- [@Joy](https://www.linkedin.com/in/joy-amuda/)
- [@Keren](https://www.linkedin.com/in/keren-otiono-337290a9/)

## Author

- [@oluwatobiadebanjo-Linkedin](https://www.linkedin.com/in/adebanjo-oluwatobi-6bb25b156/)
- [@oluwatobiadebanjo-Github](https://github.com/beloved239)
