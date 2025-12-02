# Gold Stock Management System

A web application to manage gold jewelry inventory and calculate selling prices automatically.

## What It Does
- Stores gold articles (ring, necklace, etc.) with weight and making charges
- Fetches daily gold rates automatically
- Calculates selling prices: `(Weight × Gold Rate) + Making Charges + (Wastage × Gold Rate)`
- Applies discounts and shows final price

## How to Run

**Requirements:** Java 17, Maven 3.6+

```bash
mvn spring-boot:run
```

Then open: **http://localhost:8080**

## Features

1. **Articles** - Add/view/delete gold items
2. **Gold Rate** - Auto-updates daily or set manually
3. **Selling Price** - See calculated prices with optional discounts

## Tech Stack
- **Backend:** Spring Boot 3.x, Java 17
- **Database:** H2 (in-memory) - data resets on restart
- **Frontend:** HTML, CSS, JavaScript

## Database Access
H2 Console: **http://localhost:8080/h2-console**
- URL: `jdbc:h2:mem:goldshopdb`
- User: `sa` | Password: `password`




# Project Report – Gold Stock Management System

## 1. Introduction

The **Gold Stock Management System** is a full-stack web application designed to help jewellery shops manage gold inventory and automate selling price calculations. It tracks gold articles, maintains daily gold rates, and computes accurate selling prices based on current market rates. The system is built with Spring Boot and Java on the backend, H2 as the database, and a simple HTML/CSS/JavaScript frontend. 

## 2. Modules

The application is logically divided into three main modules:

- Article Management Module  
- Gold Rate Management Module  
- Selling Price & Discount Module 

### 2.1 Article Management Module

**Input**

- Article ID  
- Name  
- Description  
- Weight in grams  
- Making charges  
- Wastage percentage  
- Manufactured date (optional; defaults to current date if not provided)   

**Processing**

- The backend validates and persists article data into the `articles` table using `ArticleService` and `ArticleRepository`.    
- REST APIs exposed at `/api/articles` (via `ArticleController`) provide CRUD operations used by the frontend.   

**Output**

- JSON responses for created, updated, listed, and deleted articles.  
- On the UI, articles are displayed in a table on `articles.html` with options to add and delete.    

### 2.2 Gold Rate Management Module

**Input**

- Manual gold rate per gram entered by the user.  
- Gold rate fetched automatically from an external API (when triggered / scheduled).   

**Processing**

- `GoldRateService` either fetches the latest rate from an external API or stores a user-provided rate.   
- A scheduled job (`updateDailyGoldRate`) runs every day at 9 AM to update and store the rate in the `gold_rate_history` table through `GoldRateHistoryRepository`.    
- APIs at `/api/gold-rate/today`, `/api/gold-rate/update`, `/api/gold-rate/fetch`, `/api/gold-rate/history` are implemented in `GoldRateController`.    

**Output**

- Today’s gold rate as a numeric value.  
- Complete gold rate history for display in the “Rate History” table on `gold-rate.html`.    

### 2.3 Selling Price & Discount Module

**Input**

- Article data (weight, making charges, wastage%) from the Article module.   
- Today’s gold rate from the Gold Rate module.   
- Discount percentage entered by the user on the Selling Prices page.   

**Processing**

- `ArticleService.updateDailyPrices()` retrieves today’s rate and calculates manufacturing cost and selling price for each article.    
- Per-article per-day prices are stored in `article_cost_history` using `ArticleCostHistoryRepository`.   
- `/api/costs/selling-prices` (in `CostController`) returns today’s selling prices to the frontend.   
- On the client side, JavaScript applies a user-entered discount to compute the final price dynamically in the browser.   

**Output**

- Base selling price for each article (server-side).  
- Final discounted price for each article displayed in the Selling Prices table.    

## 3. Class Diagram

### 3.1 Main Classes

**Entity Classes**

- `Article`  
  - Fields: `id`, `articleId`, `name`, `description`, `weightGrams`, `makingCharges`, `wastagePercentage`, `manufacturedDate`   
  - Methods: getters and setters for all fields  

- `GoldRateHistory`  
  - Fields: `id`, `date`, `ratePerGram`   
  - Methods: getters and setters  

- `ArticleCostHistory`  
  - Fields: `id`, `article`, `date`, `goldRate`, `calculatedCost`, `sellingPrice`   
  - Methods: getters and setters  

**Service Classes**

- `ArticleService`  
  - Fields: `ArticleRepository`, `ArticleCostHistoryRepository`, `GoldRateService`   
  - Key methods:  
    - `saveArticle(Article article)`  
    - `getAllArticles()`  
    - `getArticleById(Long id)`  
    - `deleteArticle(Long id)`  
    - `calculateCost(Article article, Double goldRate)`  
    - `updateDailyPrices()`  
    - `getTodaySellingPrices()`  

- `GoldRateService`  
  - Fields: `GoldRateHistoryRepository`   
  - Key methods:  
    - `fetchGoldRateFromApi()`  
    - `updateDailyGoldRate()`  
    - `manualUpdate(Double rate)`  
    - `getTodayRate()`  

**Controller Classes**

- `ArticleController`  
  - Field: `ArticleService articleService`   
  - Methods: `createArticle`, `getAllArticles`, `getArticleById`, `updateArticle`, `deleteArticle`  

- `GoldRateController`  
  - Fields: `GoldRateService`, `GoldRateHistoryRepository`   
  - Methods: `getTodayRate`, `manualUpdate`, `forceFetch`, `getHistory`  

- `CostController`  
  - Field: `ArticleService articleService`   
  - Method: `getTodaySellingPrices`  

### 3.2 Class Diagram (Mermaid)

```mermaid
classDiagram
    class Article {
        Long id
        String articleId
        String name
        String description
        Double weightGrams
        Double makingCharges
        Double wastagePercentage
        LocalDate manufacturedDate
    }

    class GoldRateHistory {
        Long id
        LocalDate date
        Double ratePerGram
    }

    class ArticleCostHistory {
        Long id
        LocalDate date
        Double goldRate
        Double calculatedCost
        Double sellingPrice
    }

    class ArticleService {
        +saveArticle(article)
        +getAllArticles()
        +getArticleById(id)
        +deleteArticle(id)
        +calculateCost(article, goldRate)
        +updateDailyPrices()
        +getTodaySellingPrices()
    }

    class GoldRateService {
        +fetchGoldRateFromApi()
        +updateDailyGoldRate()
        +manualUpdate(rate)
        +getTodayRate()
    }

    class ArticleController {
        +createArticle(article)
        +getAllArticles()
        +getArticleById(id)
        +updateArticle(id, details)
        +deleteArticle(id)
    }

    class GoldRateController {
        +getTodayRate()
        +manualUpdate(rate)
        +forceFetch()
        +getHistory()
    }

    class CostController {
        +getTodaySellingPrices()
    }

    ArticleCostHistory --> Article : article
    ArticleService --> Article
    ArticleService --> ArticleCostHistory
    ArticleService --> GoldRateService
    GoldRateService --> GoldRateHistory
    ArticleController --> ArticleService
    GoldRateController --> GoldRateService
    CostController --> ArticleService
