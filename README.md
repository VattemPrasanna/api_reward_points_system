Here is a `README.md` file for the `api-reward-points-system` project, covering requirements, structure, and implementation details:

```markdown
# API Reward Points System

A Spring Boot REST API that calculates customer reward points based on their transaction history, as per the requirements for a web API developer assignment.

## Problem Statement

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase:

- **2 points** for every dollar spent over $100 in each transaction.
- **1 point** for every dollar spent between $50 and $100 in each transaction.
- No points for amounts $50 or less.

**Example:**  
A $120 purchase earns: 2x$20 + 1x$50 = **90 points**.

Given a record of every transaction during a three-month period, the API calculates the reward points earned for each customer per month and in total.

## Features

- Calculates reward points per customer, per month, and total.
- No hardcoded months; uses transaction dates for grouping.
- RESTful endpoint to fetch reward points.
- In-memory data simulates transactions for multiple customers.
- Unit and integration tests, including negative and edge cases.
- JavaDocs at class and method levels.
- Follows Java coding standards and best practices.

## Project Structure

```
src/main/java/com/project/api_reward_points_system/
├── config/             # Application configuration
├── util/               # Utility classes (error response, etc.)
├── controller/         # REST API controllers
├── service/            # Business logic for reward calculation
├── model/              # Data models (Transaction, RewardResponse, ErrorResponse)
└── repository/         # Data access layer (mocked)

src/test/java/com/project/api_reward_points_system/
README.md
.gitignore
build.gradle
```

## How to Run

1. Clone the repository.
2. Build and run the Spring Boot application using Gradle:
   ```
./gradlew bootRun
   ```
3. Access the API endpoint:
   ```
GET /api/rewards
   ```
   Returns a list of customers with their monthly and total reward points.

## API Endpoint

- **GET /api/rewards**  
  Returns reward points per customer, per month, and total.

**Sample Response:**
```json
[
  {
    "customerId": 1,
    "monthlyPoints": {
      "2024-04": 90,
      "2024-05": 25,
      "2024-06": 250
    },
    "totalPoints": 365
  },
  ...
]

### API Endpoints

To Fetch all Customer Rewards:
http://www.localhost:8080/api/rewards

To Fetch specific Customer Rewards:
http://www.localhost:8080/api/rewards/{customerId}

- **GET /rewards**  
  Returns a list of customer rewards.  
  If no rewards are found, returns an error response.
  If exception occurred, returns an error response.

**Sample Response for success:**
```json
[
  {
    "customerId": 1,
    "monthlyPoints": {
      "2024-04": 90,
      "2024-05": 25,
      "2024-06": 250
    },
    "totalPoints": 365
  },
  ...
]

**No Content (No Rewards Found):**
```json
{
  "details": null,
  "errorMessage": "No rewards found",
  "statusCode": 404
}
```

**Exception Occurred (e.g., Internal Server Error):**
```json
{
  "details": null,
  "errorMessage": "An unexpected error occurred. Please try again later.",
  "statusCode": 500
}
```

These responses should be returned by your API in the respective scenarios, using your `ErrorResponse` structure.

## Reward Calculation Logic

- For each transaction:
    - If amount > $100:  
      Points = (amount - 100) * 2 + 50
    - If $50 < amount <= $100:  
      Points = (amount - 50) * 1
    - If amount <= $50:  
      Points = 0

## Testing

- Unit and integration tests are included.
- Tests cover multiple customers, multiple transactions, edge cases, and negative scenarios.

## Coding Standards

- Follows Java best practices for naming, structure, and documentation.
- JavaDocs provided for all classes and methods.
- Code is formatted and organized as per standard conventions.

## Exclusions

- `target/`, `bin/`, and build artifacts are excluded via `.gitignore`.
- Do not upload as a zip file.

---

**Author:**  
VattemPrasanna

**License:**  
For educational/demo purposes.
```
