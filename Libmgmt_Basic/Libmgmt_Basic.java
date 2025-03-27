/**
 Build a Library Management System (Basic Version)
Objective:
To apply Java fundamentals and basic OOP concepts.
Features to Implement:
Add Books: Allow users to add books with attributes like:
Title
Author
ISBN
Display All Books: List all books in the library.
Search Books: Search books by title or author.
Key Concepts Used:
Classes and objects
Encapsulation
Basic input/output (Scanner class)
ArrayList for storing book objects
Example Tasks:
Design a Book class.
Create a Library class to manage book objects.
Write a main() method to provide a menu-driven program.main()
---
Enhance the Library Management System (Intermediate Version)
Objective:
To practice inheritance, polymorphism, and collections.
Features to Add:
Member Management:
Add members with attributes like Name, ID, and Contact.
Issue and Return Books:
Allow issuing books to members.
Track issued books.
Search and Sort:
Sort books by title or author using Comparable/Comparator.
Key Concepts Used:
Inheritance: Add Member class with base class Person.
Polymorphism: Override toString() for meaningful outputs.
Collections: Use HashMap to map issued books to members.
Example Tasks:
Add a Member class with attributes and behaviors.
Use HashMap<Member, List<Book>> to track issued books.
Implement a Comparator for sorting books.
---
Advanced Library Management System (Full Version)
Objective:
To implement advanced collections, data structures, and exception handling.
Features to Add:
Penalty Calculation:
Add a penalty for overdue books (assume 1 point per day overdue).
Improved Search:
Use a HashMap to search books by ISBN for faster lookup.
Exception Handling:
Handle scenarios like:
Issuing a book that's already issued.
Returning a book that's not issued.
File I/O:
Save and retrieve library data from a file.
Key Concepts Used:
Advanced Collections (HashMap, Set)
Exception Handling
File I/O
Data persistence for books and members.
Example Tasks:
Write methods for penalty calculation and overdue tracking.
Serialize and deserialize library data using ObjectOutputStream and ObjectInputStream.
Add meaningful exception classes like BookNotAvailableException.
---
Submission Requirements:
For each week, students must submit:
Source code (well-commented and modular).
A brief report explaining their design approach.
Sample outputs or screenshots of the working program.
---
Assessment Criteria:
Code Quality: Proper use of OOP principles, modularity, and readability.
Functionality: Features implemented as per the requirements.
Error Handling: Robust handling of invalid inputs and edge cases.
Documentation: Code comments and a brief explanation of logic.
This structured plan ensures students progressively build skills while creating a real-world application. Let me know if you'd like any modifications or detailed examples for the tasks!
 */


 import java.util.*;

 class Book
 {
     private String tittle;
     private String author;
     private String ISBN;
 
     public Book(String tittle, String author, String ISBN) // Constructor
     {
         this.tittle = tittle;
         this.author = author;
         this.ISBN = ISBN;
     }
 
     public String getTittle()
     {
         return tittle;
     }
     public String getAuthor()
     {
         return author;
     }
     public String getISBN()
     {
         return ISBN;
     }
 
     //Override
     // Display Book details
     public String toString()
     {
         return "Tittle: " + tittle + "\tAuthor: " + author + "\tISBN: " + ISBN;
     }
 }
 
 class Library
 {
     private ArrayList<Book> books = new ArrayList<>();
     
     public void addBook(Book book)
     {
         books.add(book);
         System.out.println(book + "Book added sucessfully!");
     }
     
     public void displayBooks()
     {
         if(books.isEmpty())
         {
             System.out.println("No Books available in the Library.");
         }
         System.out.println("\nList of Books in Library..");
         for(Book book:books)
         {
             System.out.println(book);
         }
     }
     
     public void searchBook(String query)
     {
         boolean found = false;
         for(Book book: books)
         {
             if(book.getAuthor().equalsIgnoreCase(query) || book.getAuthor().equalsIgnoreCase(query))
             {
                 System.out.println(book);
                 found = true;
             }
         }
         if(!found)
         {
             System.out.println("No Book found with the given tittle or author");
         }
     }
 }
 
 public class Libmgmt_Basic
 {
     public static void main(String[] args)
     {
         Library lib = new Library();
         Scanner sc = new Scanner(System.in);
 
         while(true)
         {
             System.out.println("\nLibrary Management System ");
             System.out.println("1. Add Books");
             System.out.println("2. Display All Books");
             System.out.println("3. Search Books");
             System.out.println("4. Exit");
             System.out.println("Enter your Choice");
 
             int Choice= sc.nextInt();
             sc.nextLine();
 
             switch(Choice)
             {
                 case 1:
                     System.out.println("Enter Book Tittle: ");
                     String tittle = sc.nextLine();
                     System.out.println("Enter Author Name: ");
                     String author = sc.nextLine();
                     System.out.println("Enter ISBN: ");
                     String ISBN = sc.nextLine();
 
                     Book newbook = new Book(tittle,author,ISBN);
                     lib.addBook(newbook);
                     break;
 
                 case 2:
                     lib.displayBooks();
                     break;
 
                 case 3:
                     System.out.println("Enter Book Tittle or Author to search: ");
                     String query = sc.nextLine();
                     lib.searchBook(query);
                     break;
 
                 case 4:
                     System.out.println("Exiting the system....");
                     sc.close();
                     System.exit(0);
                     break;
                 
                 default:
                     System.out.println("Invalid Choice! please try again..");
             }
         }
     }
 }