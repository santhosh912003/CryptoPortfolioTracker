#  Crypto Portfolio Tracker - Spring Boot Backend

## Overview

**Crypto Portfolio Tracker** is a secure backend application built using **Spring Boot 3.5.0**, enabling users to manage cryptocurrency assets. The system allows users to track holdings, simulate or fetch real-time prices, set price alerts, and monitor profit/loss across their portfolio. It also provides role-based access with secure authentication, and includes full CRUD support for portfolios and alerts.

---

##  Key Features

###  User & Role Management
- Register/login with email & password
- BCrypt password encryption
- Role-based access (USER, ADMIN)
- Email/password authentication (no JWT used)

###  Portfolio Management
- Add/update/delete crypto assets
- View holdings with buy price, date, quantity


###  Price Simulation
- Simulated crypto prices (BTC, ETH, ADA, SOL, etc.)
- Price stored with timestamp
- Automatic valuation and PnL (Profit/Loss)

###  Alert Management
- Users can create price alerts (e.g., BTC > $30,000)
- Background scheduler checks every 30s–1min
- Triggered alerts are persisted and visible to the user


---

##  Tech Stack

| Layer        | Technology                               |
|--------------|-------------------------------------------|
| Language     | Java 21                                   |
| Framework    | Spring Boot 3.5.0                         |
| Security     | Spring Security- BCrypt                   |
| ORM/DB       | Spring Data JPA, MySQL  |
| Validation   | Hibernate Validator, Jakarta Validation   |
| Docs         | Springdoc OpenAPI (Swagger UI)            |
| Mapping      | ModelMapper                               |
| Testing      | JUnit, Spring Boot Test                   |
| Build Tool   | Maven                                     |

---

##  Project Structure

```
com.CryptoPortfolioTracker/
├── config/                    # Security & configuration (JWT, BCrypt, etc.)
├── controller/                # REST APIs for Portfolio, Alerts, Users
├── controller.auth/           # Auth and registration endpoints
├── dto/                       # Data Transfer Objects
├── entity/                    # JPA entities (User, CryptoAsset, Price, Alert)
├── exception/                 # Custom exceptions & global handler
├── repository/                # Spring Data repositories
├── security/                  # Spring Security configurations
├── service/                   # Business logic services
├── service.Imp/               # Service interfaces for abstraction
├── CryptoPortfolioTrackerApplication.java  # Main application class
└── resources/
    └── application.properties
```

---

##  How to Run

###  Prerequisites
- Java 21
- Maven 3.8+
- MySQL / H2

###  Run Locally (H2 default)
```bash
git clone https://github.com/your-org/CryptoPortfolioTracker.git
cd CryptoPortfolioTracker
mvn spring-boot:run
```

> Make sure your MySQL configuration is set correctly in `application.properties`.

###  Credentials
- Default roles: `USER`, `ADMIN`  
- Admin-restricted routes require `ADMIN` role

---

##  API Endpoints

###  Authentication
| Method | Endpoint            | Description              |
|--------|---------------------|--------------------------|
| POST   | /auth/register       | Register user            |
| POST   | /auth/login          | Login user |

###  Portfolio
| Method | Endpoint                   | Description                 |
|--------|----------------------------|-----------------------------|
| POST   | /portfolio/add             | Add crypto asset            |
| GET    | /portfolio/my              | View all holdings           |
| PUT    | /portfolio/update/{id}     | Update asset                |
| DELETE | /portfolio/delete/{id}     | Delete asset                |

###  Price
| Method | Endpoint     | Description               |
|--------|--------------|---------------------------|
| GET    | /prices      | View all current prices   |
| (auto) | @Scheduled   | Background simulation     |

###  Alerts
| Method | Endpoint             | Description                     |
|--------|----------------------|---------------------------------|
| POST   | /alerts/create       | Create new alert                |
| GET    | /alerts/my           | View user's alerts              |
| GET    | /alerts/triggered    | View triggered alerts           |

---

##  Sample Entity Overview

###  `User`
```java
id, name, email, password, roles
```

###  `CryptoAsset`
```java
id, userId, coinName, symbol, quantityHeld, buyPrice, buyDate
```

###  `CryptoPrice`
```java
symbol, currentPrice, timestamp
```

###  `Alert`
```java
id, userId, symbol, triggerPrice, direction, status, triggeredAt
```

---

##  Background Jobs

- Uses `@Scheduled` tasks to:
  - Simulate price updates every 30s–1min
  - Trigger user alerts automatically

---

##  Testing

Unit tests are available under:
```
src/test/java/
└── com.CryptoPortfolioTracker.service/
```

Use:

mvn test


---

##  Swagger API Docs

Visit:
```
http://localhost:8080/swagger-ui/index.html
```
> Enabled via `springdoc-openapi` dependency


---

##  Contact

_For queries, contact the project maintainers._
