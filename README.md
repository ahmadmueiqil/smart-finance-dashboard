# Smart Finance Dashboard

A full-stack personal finance management system that allows users to manage wallets, transactions, bills, and currency conversions through a secure dashboard.

## Features

### Authentication & Security

* JWT Authentication
* Secure Login & Registration
* Protected Routes
* Spring Security Integration

### Wallet Management

* View Wallet Balance
* Deposit Tracking
* Transfer Money Between Wallets

### Transactions

* View Transaction History
* Filter Transactions
* Transfer In / Transfer Out Tracking
* Expense & Deposit Monitoring

### Bills Management

* Create Bills
* View Bills
* Pay Bills
* Track Bill Status (Pending / Paid)

### Dashboard

* Financial Overview
* Recent Transactions
* Recent Bills
* Financial Analytics Charts
* Currency Converter Widget

### Currency Exchange

* Real-time Currency Conversion
* Popular Currency Rates

---

## Tech Stack

### Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* PostgreSQL
* JWT Authentication
* OpenFeign
* Swagger / OpenAPI
* JUnit & Mockito

### Frontend

* React 19
* TypeScript
* Vite
* React Query
* Zustand
* React Hook Form
* Zod
* Tailwind CSS
* Axios
* Recharts

### DevOps

* Docker
* Docker Compose
* GitHub

---

## Project Structure

```text
smart-finance-dashboard
│
├── backend
│   └── demo
│
├── frontend
│
└── README.md
```

---

## Running the Backend

```bash
cd backend/demo

mvn clean package

docker compose up --build
```

Backend runs on:

```text
http://localhost:8082
```

Swagger:

```text
http://localhost:8082/swagger-ui/index.html
```

---

## Running the Frontend

```bash
cd frontend

npm install

npm run dev
```

Frontend runs on:

```text
http://localhost:5173
```

---

## Docker Setup

Backend:

```bash
docker compose up --build
```

Frontend:

```bash
docker build -t smartfinance-frontend .

docker run -d -p 5173:80 smartfinance-frontend
```

---

## Testing

Backend tests:

```bash
mvn test
```

---

## Future Improvements

* Email Notifications
* Scheduled Bill Reminders
* Admin Dashboard
* Budget Planning
* Multi-Wallet Support
* External Banking Integration

---

## Author

Ahmad Mueiqil

GitHub:
https://github.com/ahmadmueiqil
