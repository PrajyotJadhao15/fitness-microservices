# 🚀 AI Fitness Recommendation Platform

A production-style microservices application built using **Spring Boot**, **Spring Cloud**, **Apache Kafka**, **Redis**, **Resilience4j**, **Prometheus**, **Grafana**, and **Google Gemini AI**.

The platform analyzes user fitness activity and generates AI-powered personalized fitness recommendations while demonstrating scalable backend architecture, fault tolerance, observability, event-driven communication, and modern deployment practices.

---

## 🎯 Project Overview

This project follows a microservices architecture to provide secure, scalable, and intelligent fitness recommendations.

Users can:

- Register and authenticate securely
- Track fitness activities
- View activity history
- Receive AI-generated fitness recommendations
- Experience fast responses through multi-level caching
- Benefit from resilient service communication

---

# ✨ Key Features

### 🔐 Security
- JWT-Based Authentication & Authorization
- Stateless Security Architecture
- Protected APIs

### 🤖 AI Integration
- Google Gemini API Integration
- Personalized Fitness Recommendations
- Structured Prompt Engineering
- AI Response Parsing

### 🏗️ Microservices Architecture
- API Gateway
- Eureka Service Discovery
- Config Server
- User Service
- Activity Service
- AI Service

### ⚡ Performance Optimization
- L1 Cache (Caffeine)
- L2 Cache (Redis)
- Pagination & Filtering

### 📡 Event-Driven Communication
- Apache Kafka Integration
- Asynchronous Service Communication
- Decoupled Architecture

### 🛡️ Fault Tolerance
- Resilience4j Circuit Breaker
- Graceful Degradation
- Failure Isolation

### 📊 Observability
- Spring Boot Actuator
- Prometheus Metrics Collection
- Grafana Dashboards
- Health Monitoring

### 🚀 DevOps
- Dockerized Deployment
- Docker Compose
- GitHub Actions CI/CD

---

# 🏗️ Architecture

## 🏗️ System Architecture

```text
                                      +------------------+
                                      |  Config Server   |
                                      +---------+--------+
                                                |
                                                |
+------------------+                  +---------v--------+
|  Eureka Server   |<---------------->|   API Gateway    |
| Service Registry |                  | JWT Security     |
+--------+---------+                  +---------+--------+
         ^                                       |
         |                                       |
         |                                       |
         |                                       |
         |        Service Discovery              |
         |                                       |
         |                                       |
         v                                       v

+------------------+   +------------------+   +------------------+
|   User Service   |   | Activity Service |   |    AI Service    |
+--------+---------+   +--------+---------+   +--------+---------+
         |                      |                      |
         |                      |                      |
         |                      |                      |
         |                      |                      |
         v                      v                      v

+------------------+    +------------------+    +------------------+
|   PostgreSQL     |    |     MongoDB      |    | Google Gemini AI |
+------------------+    +------------------+    +------------------+

         ^
         |
         |
+------------------+
| Redis Cache (L2) |
+--------+---------+
         ^
         |
+--------+---------+
| Caffeine (L1)    |
+------------------+

Services Communication
         |
         v

+------------------+
|      Kafka       |
+------------------+

Monitoring & Observability

+------------------+
|    Prometheus    |
+--------+---------+
         |
         v
+------------------+
|     Grafana      |
+------------------+
```

Infrastructure:
- Eureka Server
- Config Server
- Prometheus
- Grafana
```

---

# 🛠️ Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Cloud

## Security
- Spring Security
- JWT

## AI
- Google Gemini API

## Messaging
- Apache Kafka

## Caching
- Redis
- Caffeine

## Resilience
- Resilience4j

## Monitoring
- Spring Boot Actuator
- Prometheus
- Grafana

## DevOps
- Docker
- Docker Compose
- GitHub Actions

## Build Tool
- Maven

---

# 📂 Services

| Service | Responsibility |
|----------|---------------|
| API Gateway | Request Routing & Security |
| Eureka Server | Service Discovery |
| Config Server | Centralized Configuration |
| User Service | User Management |
| Activity Service | Activity Tracking |
| AI Service | AI-Powered Recommendations |

---

# 🔥 Implemented Features

## User Management
- User Registration
- User Authentication
- JWT Token Generation
- User Profile Retrieval

## Activity Tracking
- Create Activities
- View Activities
- Pagination Support
- Filtering Support

## AI Recommendations
- Fitness Recommendation Generation
- Prompt Engineering
- Response Parsing

## Multi-Level Caching
- Caffeine (L1 Cache)
- Redis (L2 Cache)
- Reduced Database Load
- Improved Response Time

## Event-Driven Architecture
- Kafka Producer
- Kafka Consumer
- Asynchronous Communication

## Fault Tolerance
- Circuit Breaker
- Fallback Mechanisms
- Service Protection

## Monitoring
- Application Metrics
- JVM Metrics
- Health Checks
- Dashboard Visualization

## Exception Handling
- Global Exception Handler
- Standardized Error Responses

---

# 📊 Performance Improvements

### Multi-Level Caching
- Reduced repeated database queries
- Faster response times
- Improved scalability

### Kafka Integration
- Decoupled services
- Better throughput
- Improved reliability

### Circuit Breaker
- Prevents cascading failures
- Improves system stability

---

# 🚀 Getting Started

## Clone Repository

```bash
git clone <your-repository-url>
cd fitness-microservices
```

## Build Application

```bash
mvn clean install
```

## Build Docker Images

```bash
docker-compose build
```

## Start Services

```bash
docker-compose up -d
```

## Verify Containers

```bash
docker ps
```

---

# 📊 Monitoring

## Prometheus

```text
http://localhost:9090
```

## Grafana

```text
http://localhost:3000
```

---

# 🔄 CI/CD Pipeline

Implemented GitHub Actions for:

- Automated Build
- Automated Testing
- Continuous Integration
- Deployment Validation

---

# 📷 Screenshots

## Architecture Diagram

Add architecture diagram here

## Docker Containers

Add Docker screenshot here

## Grafana Dashboard

Add Grafana dashboard screenshot here

## Redis Cache Logs

Add cache hit/miss screenshot here

---

# 📚 Concepts Demonstrated

- Microservices Architecture
- API Gateway Pattern
- Service Discovery
- Centralized Configuration
- JWT Authentication
- Event-Driven Architecture
- Distributed Caching
- Fault Tolerance
- AI Integration
- Observability
- Containerization
- CI/CD

---

# 🔮 Future Enhancements

- Kubernetes Deployment
- OpenTelemetry Distributed Tracing
- Feature Flags
- SLO & Error Budget Monitoring
- Blue-Green Deployment

---

# 👨‍💻 Author

**Prajyot Jadhao**

Backend Developer | Java | Spring Boot | Microservices | Kafka | Redis | Docker | AI Integration

---

⭐ If you found this project useful, consider giving it a star.
