# Library Management System

A **Library Management System** built in Java, demonstrating object-oriented programming (OOP) concepts through progressive development stages. The system evolves from a basic book management tool to a full-fledged system with member management, book issuance, penalty calculation, and file persistence.

## 📌 Features
### 🔹 Basic Version
- Add books with attributes: **Title, Author, ISBN**.
- Display all books.
- Search books by **title** or **author**.

### 🔹 Intermediate Version
- **Member Management**: Add members with Name, ID, and Contact.
- **Issue and Return Books**: Track issued books.
- **Search and Sort**: Sort books using Comparable/Comparator.

### 🔹 Advanced Version
- **Penalty Calculation**: Track overdue books (1 point per day).
- **Improved Search**: Use **HashMap** for fast ISBN-based lookup.
- **Exception Handling**: Handle cases like issuing an unavailable book.
- **File I/O**: Save and retrieve data using serialization.

## 🛠️ Technologies & Concepts Used
- **Java** (OOP principles: Encapsulation, Inheritance, Polymorphism)
- **Collections Framework** (ArrayList, HashMap, Set)
- **Exception Handling** (Custom exceptions for book availability)
- **File Handling** (Object serialization for data persistence)
- **Scanner Class** for user input

## 🚀 How to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/Kumarmilan02/Library-Management-System.git
   ```
2. Navigate to the project directory:
   ```sh
   cd Library-Management-System
   ```
3. Compile the Java files:
   ```sh
   javac LibraryManagement.java
   ```
4. Run the program:
   ```sh
   java LibraryManagement
   ```
5. Follow the menu prompts to manage books and members.

## 📸 Sample Output
```
Library Management System
1. Add Books
2. Add Member
3. Display All Books
4. Display All Members
5. Search Books
6. Search Books by ISBN
7. Sort Books by Title
8. Sort Books by Author
9. Issue Book
10. Return Book
11. Display Issued Books
12. Exit
Enter your choice: 
```

## 🤝 Contribution
Feel free to fork this repository, open an issue, or submit a pull request for improvements and new features!

## 🔚 Conclusion
This Library Management System is a step-by-step implementation of core Java concepts, gradually progressing to advanced topics like file handling and exception management. It serves as a great learning resource for students and developers looking to enhance their Java programming skills.

---
🎯 **Developed by Milan Kumar Sahoo**
