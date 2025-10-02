# 🍽️ JavaFood

**JavaFood** is a practice project built with **Java** to simulate a simple food ordering and restaurant management system.  
The project covers core models, user management, custom exceptions, and unit testing.  

---

## ✨ Features

- 👤 **User Management** (Admin, Customer, Restaurant Owner)  
- 🛒 **Order Management** (create and manage food orders)  
- 🍔 **Food Management** (add and manage foods with prices)  
- 🎟️ **Discount Codes** (apply and validate discount codes)  
- 🚫 **Custom Exceptions** for better error handling  
- 🧪 **Unit Tests** for all major components  

---

## 🗂️ Project Structure

```
src
 ├── main
 │   ├── java
 │   │   └── org.TaskManagerProgram.com
 │   │       ├── Exceptions      # Custom exceptions
 │   │       ├── JavaFood        # Core application files
 │   │       ├── Models          # Core models (Food, Order, Restaurant, Discount)
 │   │       └── Users           # System users (Admin, Customer, RestaurantOwner, User)
 │   └── resources
 └── test
     └── java
         └── org.TaskManagerProgram.com
             ├── Exceptions      # Exception tests
             ├── JavaFood        # AdminPanel tests
             ├── Models          # Food, Order, Discount, Restaurant tests
             └── Users           # User tests
```

---

## ⚙️ Technologies

- ☕ **Java** (recommended JDK 17 or above)  
- 📦 **Maven** for dependency management and build  
- 🧪 **JUnit 5** for unit testing  

---

## 🚀 Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/kian-al/javaFood.git
   cd javaFood
   ```

2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

3. Run the tests:
   ```bash
   mvn test
   ```

---

## 🧪 Unit Tests Coverage

The project includes unit tests for:  
- **ExceptionsTest**  
- **AdminPanelTest**  
- **DiscountTest**  
- **FoodTest**  
- **OrderTest**  
- **RestaurantTest**  
- **UserTest**  

---

## 📌 Future Improvements

- Add a **Graphical User Interface** (JavaFX or Spring Boot)  
- Connect to a real **database** (MySQL / PostgreSQL)  
- Add **RESTful API** for managing orders  

---

## 👨‍💻 Author

- **Kian Almasi**  
  - [GitHub](https://github.com/kian-al)  
  - [LinkedIn](www.linkedin.com/in/kian-almasi)  
