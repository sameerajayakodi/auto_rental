# Auto Rental System

A JavaFX-based application for managing vehicle rentals. This project demonstrates CRUD operations for vehicles, order management, and bill generation using modern software development principles.

---

## 🚗 Features

### Vehicle Management
- Add, update, view, and delete vehicles.
- Manage details like:
  - Vehicle Model
  - Brand
  - Color
  - Qty
  - Rental Price Per Day

### Order Management
- Place rental orders.
- Track order details.
- Generate detailed bills for transactions.

### Validated Forms
- Ensures accurate input with JavaFX Alerts for user guidance.

### User Interface
- Built using **FXML** for layout and **CSS** for styling.

---

## 💻 Technology Stack

- **Programming Language:** Java SE  
- **Framework:** JavaFX (FXML and CSS for UI)  
- **Database:** MySQL with JDBC Integration  
- **Architecture:** MVC (Model-View-Controller)  
- **Design Pattern:** Singleton (Database Connection)

---

## 📂 Project Structure

```
AutoRentalSystem/
├── src/
│   ├── model/         # Database models and Controllers
│   ├── controller/    # Application Controllers
│   └── utils/         # Utility classes (e.g., Singleton DB connection)
├── resources/
│   ├── css/           # External CSS styles
│   └── fxml/          # FXML layout files
└── README.md          # Project documentation
```

---

## 🔑 Key Concepts Demonstrated

1. **Java Basics:** Classes, objects, loops, conditionals, and exceptions.
2. **OOP Principles:** Encapsulation, inheritance, and polymorphism.
3. **Database Integration:** JDBC connection and MySQL queries.
4. **Design Patterns:** Singleton for efficient database connection management.
5. **MVC Architecture:** Clear separation of logic, UI, and data handling.

---

## 🛠️ Setup Instructions

1. Clone this repository:
   ```bash
   git clone https://https://github.com/sameerajayakodi/auto_rental.git
   ```
2. Import the project into your favorite IDE (e.g., IntelliJ IDEA, Eclipse).
3. Set up the database:
   - Create a MySQL database named `auto_rental`.
   - Import the provided `database.sql` file to set up tables.
4. Update the database connection details in the `DBConnection` class:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/auto_rental";
   private static final String USER = "your-username";
   private static final String PASSWORD = "your-password";
   ```
5. Run the application.

---

## 📚 What I Learned

- Implementing **MVC architecture** for maintainability.
- Using the **Singleton pattern** for database connectivity.
- Designing user-friendly forms with validation.
- Building a professional UI using FXML and CSS.

---

## 📄 License
This project is licensed under the [MIT License](LICENSE).

---

## 🤝 Contributions
Contributions are welcome! Feel free to fork this repository and submit pull requests.

---

## 📧 Contact
For questions or feedback, feel free to reach out:
- **Email:** sameerajayakodi456@gmail.com
- **LinkedIn:** [Your LinkedIn Profile](https://www.linkedin.com/in/sameera-jayakodi-6a3a81226/)
