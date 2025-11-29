# Gold Stock and Cost Management System

A full-stack application for managing gold articles, manufacturing costs, and daily selling prices.

## Tech Stack
- **Backend**: Java 17, Spring Boot 3.x
- **Database**: H2 (In-memory, default), MySQL (Configurable)
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)

## Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.6+

## Setup and Run

1. **Clone/Download** the repository.
2. **Navigate** to the project root directory:
   ```bash
   cd "Jewellery Project"
   ```
3. **Run the application** using Maven:
   ```bash
   mvn spring-boot:run
   ```
   *Alternatively, if you have the wrapper:* `./mvnw spring-boot:run`

4. **Access the Application**:
   Open your browser and go to: [http://localhost:8080](http://localhost:8080)

## Features

- **Dashboard**: Quick links to all modules.
- **Articles**: Add, view, and delete gold articles.
- **Gold Rate**:
    - View today's rate (auto-fetched or manual).
    - Update rate manually.
    - View rate history.
- **Selling Prices**:
    - Auto-calculated selling prices based on today's gold rate.
    - `(Weight * Rate) + Making Charges + (Wastage * Rate)`
    - Apply discounts and see final prices instantly.

## Database Configuration

By default, the application uses an **in-memory H2 database**. Data will be lost when the application stops.
- **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - JDBC URL: `jdbc:h2:mem:goldshopdb`
    - User: `sa`
    - Password: `password`

To use **MySQL**:
1. Open `src/main/resources/application.properties`.
2. Comment out the H2 section.
3. Uncomment the MySQL section and update credentials.

## API Endpoints

- `GET /api/articles`: List all articles
- `POST /api/articles`: Create article
- `GET /api/gold-rate/today`: Get today's rate
- `POST /api/gold-rate/update`: Manual rate update
- `GET /api/costs/selling-prices`: Get calculated prices
