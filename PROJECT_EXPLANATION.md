# Gold Stock Management System - Project Explanation

## Project Overview

The **Gold Stock Management System** is a full-stack web application designed to help gold jewelry shops manage their inventory and automate selling price calculations. The system tracks gold articles, monitors daily gold rates, and calculates accurate selling prices based on current market rates.

---

## Problem Statement

Gold jewelry businesses face several challenges:
- Manually tracking inventory of various gold articles
- Keeping up with daily fluctuating gold rates
- Calculating selling prices accurately (weight + making charges + wastage)
- Applying discounts and computing final prices quickly
- Maintaining historical records of prices and rates

This application solves these problems by automating the entire process.

---

## Technology Stack

### Backend
- **Language:** Java 17
- **Framework:** Spring Boot 3.x
  - Spring Web (REST APIs)
  - Spring Data JPA (Database operations)
  - Spring Scheduler (Automated tasks)
  
### Database
- **H2 Database** (In-memory) - Default configuration
  - Fast, lightweight, perfect for development and testing
  - Data resets when application stops
  - Built-in web console for database management

### Frontend
- **HTML5** - Structure and layout
- **CSS3** - Styling and responsive design
- **JavaScript (Vanilla)** - Dynamic functionality and API calls
- No frameworks required - keeps it simple and lightweight

---

## System Architecture

The application follows a standard **3-tier architecture**:

```
┌─────────────────┐
│   Frontend      │  HTML/CSS/JS - User Interface
│   (Browser)     │
└────────┬────────┘
         │ HTTP/REST
┌────────▼────────┐
│   Backend       │  Spring Boot - Business Logic
│   (Java)        │  Controllers, Services, Repositories
└────────┬────────┘
         │ JPA/Hibernate
┌────────▼────────┐
│   Database      │  H2 - Data Storage
│   (H2)          │  Articles, Rates, History
└─────────────────┘
```

---

## Core Features

### 1. Article Management
- **Add Articles:** Create new gold items with details like:
  - Article name (e.g., "Gold Ring", "Necklace")
  - Weight in grams
  - Making charges
  - Wastage percentage
- **View Articles:** Display all articles in a table
- **Delete Articles:** Remove unwanted items from inventory

### 2. Gold Rate Management
- **Today's Rate:** View the current gold price per gram
- **Manual Update:** Set gold rate manually when needed
- **Auto-fetch:** System can fetch rates automatically (if API configured)
- **Rate History:** Track how gold prices change over time

### 3. Selling Price Calculation
The system automatically calculates selling prices using this formula:

```
Selling Price = (Weight × Gold Rate) + Making Charges + (Wastage × Gold Rate)
```

**Example:**
- Weight: 10 grams
- Gold Rate: ₹6,000/gram
- Making Charges: ₹5,000
- Wastage: 0.5 grams

**Calculation:**
```
= (10 × 6,000) + 5,000 + (0.5 × 6,000)
= 60,000 + 5,000 + 3,000
= ₹68,000
```

### 4. Discount Application
- Apply percentage-based discounts
- See final price instantly
- Helps in customer negotiations

---

## How It Works

### Step-by-Step Workflow

1. **Start Application**
   - Run `mvn spring-boot:run`
   - Application starts on http://localhost:8080

2. **Set Gold Rate**
   - Navigate to "Gold Rate" page
   - Enter today's gold price per gram
   - System saves and uses this for calculations

3. **Add Articles**
   - Go to "Articles" page
   - Enter article details (name, weight, charges, wastage)
   - Click "Add Article"

4. **Calculate Selling Prices**
   - Go to "Selling Price" page
   - System automatically shows calculated prices for all articles
   - Apply discount if needed
   - View final price

5. **View Data**
   - Check H2 Console at http://localhost:8080/h2-console
   - See all stored data in tables

---

## Database Schema

### Tables

**1. ARTICLE**
- `id` - Unique identifier
- `name` - Article name
- `weight_grams` - Weight in grams
- `making_charges` - Manufacturing cost
- `wastage_grams` - Wastage amount

**2. GOLD_RATE_HISTORY**
- `id` - Unique identifier
- `rate_per_gram` - Gold price
- `rate_date` - Date of the rate
- `source` - Manual or API

**3. ARTICLE_COST_HISTORY**
- `id` - Unique identifier
- `article_id` - Reference to article
- `gold_rate` - Rate used for calculation
- `selling_price` - Calculated price
- `calculation_date` - When calculated

---

## API Endpoints

### Articles
- `GET /api/articles` - Get all articles
- `POST /api/articles` - Create new article
- `DELETE /api/articles/{id}` - Delete article

### Gold Rate
- `GET /api/gold-rate/today` - Get current rate
- `POST /api/gold-rate/update` - Update rate manually
- `GET /api/gold-rate/history` - View historical rates

### Selling Prices
- `GET /api/costs/selling-prices` - Calculate prices for all articles

---

## Running the Application

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher

### Steps
1. Open terminal/command prompt
2. Navigate to project directory
3. Run: `mvn spring-boot:run`
4. Wait for "Started GoldShopApplication" message
5. Open browser to: http://localhost:8080

### Accessing H2 Database Console
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:goldshopdb`
- Username: `sa`
- Password: `password`

---

## Project Structure

```
Jewellery Project/
├── src/main/java/com/goldshop/
│   ├── controller/         # REST API endpoints
│   │   ├── ArticleController.java
│   │   ├── GoldRateController.java
│   │   └── CostController.java
│   ├── service/           # Business logic
│   │   ├── ArticleService.java
│   │   └── GoldRateService.java
│   ├── repository/        # Database access
│   │   ├── ArticleRepository.java
│   │   └── GoldRateHistoryRepository.java
│   ├── model/             # Data entities
│   │   ├── Article.java
│   │   └── GoldRateHistory.java
│   └── GoldShopApplication.java  # Main class
├── src/main/resources/
│   ├── application.properties    # Configuration
│   └── static/            # Frontend files
│       ├── index.html     # Dashboard
│       ├── articles.html  # Article management
│       ├── gold-rate.html # Rate management
│       ├── selling-price.html  # Price calculator
│       ├── css/style.css  # Styling
│       └── js/app.js      # JavaScript logic
├── pom.xml                # Maven dependencies
└── README.md              # Quick start guide
```

---

## Key Benefits

✅ **Automation** - No manual price calculations needed  
✅ **Accuracy** - Eliminates human calculation errors  
✅ **Speed** - Instant price updates when gold rate changes  
✅ **History** - Track price changes over time  
✅ **Simple** - Easy-to-use web interface  
✅ **Portable** - Runs on any system with Java  

---

## Future Enhancements

- Multi-user support with authentication
- PDF invoice generation
- Mobile app version
- Cloud deployment
- Integration with payment gateways
- Customer management module
- Sales tracking and reports

---

## Conclusion

The Gold Stock Management System is a practical, efficient solution for gold jewelry businesses to manage inventory and automate price calculations. Built with modern technologies and best practices, it demonstrates the power of full-stack development in solving real-world business problems.

---

**Developed with:** Spring Boot, Java, H2 Database, HTML/CSS/JavaScript  
**Date:** December 2025
