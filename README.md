# Java Spring boot based Banking system APIs

This is a simple Java Spring Boot application that simulates a basic banking system with in-memory data storage. It supports account creation, balance inquiries, and transaction transfers. The project follows a layered architecture using DTOs, services, repositories, and controllers.

---
## Features

- Create new bank accounts
- Transfer funds between accounts
- View transaction history per account
- Input validation and error handling
- Swagger UI for API testing
- In-memory data storage (no external DB)
- Rate-limiting and health check

---

## Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Web, Spring Transactions, Spring Validation
- SpringDoc OpenAPI (Swagger)
- Maven
- Lombok
## Project Structure
---

```
src/main/java/com/example/banking/
├── controller/
│   ├── AccountController.java
│   └── TransactionController.java
├── dto/
│   ├── AccountCreationRequest.java
│   ├── AccountResponse.java
│   ├── TransactionRequest.java
│   └── TransactionResponse.java
├── exception/
│   ├── AccountNotFoundException.java
│   ├── InsufficientFundsException.java
│   ├── SameAccountTransferException.java
│   └── GlobalExceptionHandler.java
├── model/
│   ├── Account.java
│   └── Transaction.java
├── repository/
│   ├── AccountRepository.java
│   └── TransactionRepository.java
├── service/
│   ├── AccountService.java
│   ├── AccountServiceImpl.java
│   ├── TransactionService.java
│   └── TransactionServiceImpl.java
└── BankingApplication.java
```

---

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- IDE (IntelliJ or VS Code)
- Browser for Swagger testing

### Setup & Run

1. **Clone the repository:**
   ```bash
   git clone 
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access Swagger UI:**
   Open [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/) in your browser or directly access banking/index.html in a browser (make sure to keep the Spring Boot app running).

5. **Health Check:**
   Open [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) in your browser for health check.

---

## How It Works

###  Account API

| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| POST   | `/api/accounts`           | Create a new account     |
| GET    | `/api/accounts/{userId}`  | Get account by user ID   |

#### Example: `AccountCreationRequest`
```json
{
  "name": "John Doe",
  "initialBalance": 1000.0
}
```

#### Example: `AccountResponse`
```json
{
  "userId": 1000000,
  "name": "John Doe",
  "balance": 1000.0
}
```

---

### Transaction API

| Method | Endpoint                                  | Description                         |
|--------|-------------------------------------------|-------------------------------------|
| POST   | `/api/transactions/transfer`              | Transfer money between accounts     |
| GET    | `/api/transactions/history/{accountId}`   | Get transaction history for account |

#### Example: `TransactionRequest`
```json
{
  "fromAccount": 1000000,
  "toAccount": 1000001,
  "amount": 250.0
}
```

#### Example: `TransactionResponse`
```json
{
  "fromAccount": 1000000,
  "toAccount": 1000001,
  "amount": 250.0,
  "timestamp": "2025-05-20T12:00:00Z",
  "status": "SUCCESS"
}
```

---

##  Testing with Swagger

1. Navigate to: [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)
2. Try the available endpoints by filling in the request bodies and executing the requests directly from the browser.
3. All schema models are defined and documented in the Swagger interface.

---

## ❗ Exception Handling

Your application handles various error scenarios using custom exceptions and a global exception handler:

###  `AccountNotFoundException`
Thrown when a requested account does not exist.

**Response:**
```json
{
  "timestamp": "2025-05-20T16:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Account with ID 1000002 not found"
}
```

---

###  `InsufficientFundsException`
Thrown when the sender's account balance is too low to complete a transfer.

**Response:**
```json
{
  "timestamp": "2025-05-20T16:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient funds in account 1000000"
}
```

---

###  `SameAccountTransferException`
Thrown when the source and destination account IDs in a transfer are the same.

**Response:**
```json
{
  "timestamp": "2025-05-20T16:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Cannot transfer to the same account"
}
```

---

### `GlobalExceptionHandler`
Handles:
- Input validation errors
- Illegal arguments
- Any unhandled or runtime exceptions

**Example Response (Validation Error):**
```json
{
  "timestamp": "2025-05-20T16:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: 'name' must not be blank"
}
```

---

## Rate Limiting
<img src="https://github.com/user-attachments/assets/f74ae567-d246-4508-a5fe-5137a0a3404f" width="600" />
<img src="https://github.com/user-attachments/assets/4ce50837-853d-4341-8aa0-947279831832" width="600" style="margin-top: 20px;" />
<img src="https://github.com/user-attachments/assets/4f2c40c9-16f0-4a7c-a663-4ce66f90fb33" width="600" style="margin-top: 20px;" />
<img src="https://github.com/user-attachments/assets/9ce969bb-e9ea-41ec-9333-50acdc699c3e" width="600" style="margin-top: 20px;" />

## Screenshots

- Swagger UI interface

![image](https://github.com/user-attachments/assets/ece400d3-2770-4b77-8082-c04748f30e4d)

![image](https://github.com/user-attachments/assets/b51a8695-ae9d-481f-9427-27e471921ad2)


- Sample request/response with custom HTML interface for testing
### Create Account

![image](https://github.com/user-attachments/assets/c1d95749-3aec-4877-84eb-4be8501d686f)

![image](https://github.com/user-attachments/assets/c75aca63-151d-4c7f-839e-bf245179c550)

### Get Account Details

![image](https://github.com/user-attachments/assets/8a90adf7-ad06-4ffd-a0ef-f70d415fca8f)

![image](https://github.com/user-attachments/assets/c4616d23-4243-43da-9598-a4e700d82d20)


---

### Transfer Funds

![image](https://github.com/user-attachments/assets/7fa0c87f-fad1-446c-8b0d-b2ea9d1e19ed)

![image](https://github.com/user-attachments/assets/872988a4-64b6-4d50-a4cf-359e033e701d)

![image](https://github.com/user-attachments/assets/368e7b95-c8d6-443a-9e64-aa4ed9eeddb8)

### Get Transaction History

![image](https://github.com/user-attachments/assets/1f2fcdec-c0f4-493e-a18c-7f94f5de1bfb)

![image](https://github.com/user-attachments/assets/453536e4-a14b-46d7-91ea-28f5f57baffd)

### Insufficient Funds

![image](https://github.com/user-attachments/assets/a9090919-d2e6-4918-9a7e-aee29811514a)

### Same Account Error

![image](https://github.com/user-attachments/assets/1df2c569-d682-41fb-a27e-3d742481f2e9)

### Account Doesn't Exist

![image](https://github.com/user-attachments/assets/70bf1d4f-0288-4e82-b615-4e0b31c0298f)


---

## License

This project is for testing purposes only.

---

## Author

**Jayateja Alugolu**
