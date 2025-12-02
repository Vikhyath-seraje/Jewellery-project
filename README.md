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
