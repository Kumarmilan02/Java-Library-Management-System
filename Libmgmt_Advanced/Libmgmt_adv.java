/**
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
 */

import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;


// Custom Exception - when a book is not available for issue
class BookNotAvailableException extends Exception 
{
    public BookNotAvailableException(String message) 
    {
        super(message);
    }
}

// Custom Exception - when a book is not found among the issued books for a member
class BookNotIssuedException extends Exception 
{
    public BookNotIssuedException(String message) 
    {
        super(message);
    }
}

class Person implements Serializable 
{
    protected String name;
    protected String id;
    protected String contact;

    // Constructor to initialize a Person object
    public Person(String name, String id, String contact) 
    {
        this.name = name;
        this.id = id;
        this.contact = contact;
    }
    
    // String representation of a Person
    @Override
    public String toString() 
    {
        return "Name: " + name + "\t Id: " + id + "\t Contact: " + contact;
    }
}

class Member extends Person 
{
    public Member(String name, String id, String contact)
    {
        super(name, id, contact);
    }
}

// Implements Serializable for saving/loading objects and Comparable for sorting based on title
class Book implements Serializable, Comparable<Book> 
{
    private String tittle;   
    private String author;   
    private String ISBN;    

    // Constructor to initialize a Book object
    public Book(String tittle, String author, String ISBN) 
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

    // String representation of a Book
    @Override
    public String toString() 
    {
        return "Tittle: " + tittle + "\t Author: " + author + "\t ISBN: " + ISBN;
    }

    // Compare two books by title (case-insensitive)
    @Override
    public int compareTo(Book other) 
    {
        return this.tittle.compareToIgnoreCase(other.tittle);
    }
}

// Comparator class to sort books by author (case-insensitive)
class AuthorComparator implements Comparator<Book> 
{
    public int compare(Book b1, Book b2) 
    {
        return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
    }
}

// Library class manages books, members, and issued books
class Library implements Serializable 
{
    // List of all books available in the library
    private ArrayList<Book> books = new ArrayList<>();
    
    // List of registered members in the library
    private ArrayList<Member> members = new ArrayList<>();
    
    // Mapping of members to the books they have issued along with due days.
    // Each member maps to a HashMap where the key is the issued Book and the value is the due days.
    private HashMap<Member, HashMap<Book, LocalDate>> issuedBooks = new HashMap<>();
    
    // Lookup map for quick retrieval of books by their ISBN
    private HashMap<String, Book> bookLookup = new HashMap<>();
    
    // Adds a new book to the library's collection
    public void addBook(Book book) 
    {
        books.add(book);
        bookLookup.put(book.getISBN(), book);
        System.out.println("\nBook added successfully!");
    }
    
    // Adds a new member to the library
    public void addMember(Member member) 
    {
        members.add(member);
        System.out.println(member + " added successfully!..");
    }

    // Displays all the books available in the library
    public void displayBooks() 
    {
        if (books.isEmpty()) 
        {
            System.out.println("No Books available in the Library.");
            return;
        }
        System.out.println("\nList of Books in Library..");
        for (Book book : books) 
        {
            System.out.println(book);
        }
    }

    // Displays all registered members
    public void displayMembers() 
    {
        if (members.isEmpty()) 
        {
            System.out.println("No members in Library.");
            return;
        }
        System.out.println("\nLibrary Members: ");
        for (Member member : members) 
        {
            System.out.println(member);
        }
    }
    
    // Searches for a book by its title or author name
    public void searchBook(String query) 
    {
        boolean found = false;
        for (Book book : books) {
            if (book.getTittle().equalsIgnoreCase(query) || book.getAuthor().equalsIgnoreCase(query)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) 
        {
            System.out.println("No Book found with the given tittle or author");
        }
    }

    // Searches for a book using its ISBN via the lookup map
    public void searchBookByISBN(String isbn) 
    {
        Book book = bookLookup.get(isbn);
        if (book != null) 
        {
            System.out.println(book);
        } 
        else 
        {
            System.out.println("No book found with the given ISBN.");
        }
    }
    
    // Sorts the list of books based on title using natural ordering (Comparable)
    public void sortBooksByTittle() 
    {
        Collections.sort(books);
        System.out.println("Books sorted by tittle.");
        System.out.println("\nList of Books in Library..");
        for (Book book : books) 
        {
            System.out.println(book);
        }
    }

    // Sorts the list of books based on author using the custom AuthorComparator
    public void sortBooksByAuthor() 
    {
        Collections.sort(books, new AuthorComparator());
        System.out.println("Books sorted by Author.");
        System.out.println("\nList of Books in Library..");
        for (Book book : books) 
        {
            System.out.println(book);
        }
    }

    // Issues a book to a member if available.
    // Removes the book from the available books list and adds it to the issuedBooks map.
    public void issueBook(Member member, String isbn, LocalDate dueDate) throws BookNotAvailableException 
    {
        Book book = bookLookup.get(isbn);
        // Check if the book exists and is still available in the library.
        if (book == null || !books.contains(book)) 
        {
            throw new BookNotAvailableException("Book is not available for issue.");
        }
        
        // Ensure the member's issued books map exists, then add the book with its due days.
        issuedBooks.putIfAbsent(member, new HashMap<Book, LocalDate>());
        issuedBooks.get(member).put(book, dueDate);
        // Remove the book from available collection
        books.remove(book);
        System.out.println("Book issued successfully to " + member.name+ " with due date: " + dueDate);
    }
    
    
    // Processes the return of a book  and calculate penlaty time
    public void returnBook(Member member, String isbn, LocalDate returnDate) throws BookNotIssuedException 
    {
        if (!issuedBooks.containsKey(member)) 
        {
            throw new BookNotIssuedException("No books were issued to this member.");
        }

        HashMap<Book, LocalDate> memberBooks = issuedBooks.get(member);
        Book bookToReturn = null;
        LocalDate dueDate = null;
        // Find the issued book using its ISBN
        for (Map.Entry<Book, LocalDate> entry : memberBooks.entrySet()) 
        {
            if (entry.getKey().getISBN().equals(isbn)) {
                bookToReturn = entry.getKey();
                dueDate = entry.getValue();
                break;
            }
        }

        if (bookToReturn == null) 
        {
            throw new BookNotIssuedException("This book was not issued to this member.");
        }
        // System.out.println(returnDate);
        // System.out.println(dueDate);
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        daysLate= Math.max(daysLate,0);
        // System.out.println(daysLate);
        double penalty = daysLate * 1;

        // Add the book back to the available books and update the lookup
        // books.add(bookToReturn);
        // bookLookup.put(bookToReturn.getISBN(), bookToReturn);
        // Remove the book from the member's issued list
        memberBooks.remove(bookToReturn);

        // If the member has no more issued books, remove them from the map
        if (memberBooks.isEmpty()) 
        {
            issuedBooks.remove(member);
        }

        bookLookup.put(bookToReturn.getISBN(), bookToReturn);
        System.out.println("Book returned successfully. Penalty:" + penalty);
    }

    // Getting method to retrieve the issued books map , members , available books
    public HashMap<Member, HashMap<Book, LocalDate>> getIssuedBooks() 
    {
        return issuedBooks;
    }

    public List<Member> getMembers() 
    {
        return members;
    }
    
    public List<Book> getBooks() 
    {
        return books;
    }
    
    // Finds and returns a book issued to a member by matching its title.
    public Book getIssuedBook(Member member, String bookTittle) 
    {
        if (issuedBooks.containsKey(member)) 
        {
            for (Book book : issuedBooks.get(member).keySet()) 
            {
                if (book.getTittle().equalsIgnoreCase(bookTittle)) 
                {
                    return book;
                }
            }
        }
        return null;
    }

    // Saves the current state of the library to a file ("library.dat")
    public void saveLibraryData() 
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("library.dat"))) 
        {
            out.writeObject(this);
            System.out.println("Library data saved successfully.");
        } 
        catch (IOException e) 
        {
            System.out.println("Error saving library data: " + e.getMessage());
        }
    }

    // Loads the library data from a file ("library.dat") if it exists; otherwise, returns a new Library object.
    public static Library loadLibraryData() 
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("library.dat"))) 
        {
            return (Library) in.readObject();
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            System.out.println("No saved library data found, starting fresh.");
            return new Library();
        }
    }
}

// Main class to run the Library Management System application
public class Libmgmt_adv 
{
    public static void main(String[] args) 
    {
        // Create a new Library instance
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        while (true) 
        {
            // Display the menu options to the user
            System.out.println("\nLibrary Management System ");
            System.out.println("1. Add Books");
            System.out.println("2. Add Member");
            System.out.println("3. Display All Books");
            System.out.println("4. Display All Members");
            System.out.println("5. Search Books");
            System.out.println("6. Search Books by ISBN ");
            System.out.println("7. Sort Books by Tittle");
            System.out.println("8. Sort Books by Author");
            System.out.println("9. Issue Book");
            System.out.println("10. Return Book");
            System.out.println("11. Display Issued Books");
            System.out.println("12. Exit");
            System.out.println("Enter your Choice");

            // Read the user's menu choice
            int Choice = sc.nextInt();
            sc.nextLine(); // Consume newline left-over
            try 
            {
                switch (Choice) 
                {
                    case 1:
                        // Add a new book
                        System.out.println("Enter Book Tittle: ");
                        String tittle = sc.nextLine();
                        System.out.println("Enter Author Name: ");
                        String author = sc.nextLine();
                        System.out.println("Enter ISBN: ");
                        String ISBN = sc.nextLine();
                        lib.addBook(new Book(tittle, author, ISBN));
                        break;
                    
                    case 2:
                        // Add a new member
                        System.out.println("Enter Member Name: ");
                        String name = sc.nextLine();
                        System.out.println("Enter Member Id: ");
                        String id = sc.nextLine();
                        System.out.println("Enter Contact: ");
                        String contact = sc.nextLine();
                        lib.addMember(new Member(name, id, contact));
                        break;
                    
                    case 3:
                        // Display all available books
                        lib.displayBooks();
                        break;
                    
                    case 4:
                        // Display all registered members
                        lib.displayMembers();
                        break;
                    
                    case 5:
                        // Search for a book by title or author
                        System.out.println("Enter Book Tittle or Author to search: ");
                        String query = sc.nextLine();
                        lib.searchBook(query);
                        break;
                    
                    case 6:
                        // Search for a book using its ISBN
                        System.out.print("Enter ISBN to Search: ");
                        String searchISBN = sc.nextLine();
                        lib.searchBookByISBN(searchISBN);
                        break;
                    
                    case 7:
                        // Sort books by title and display them
                        lib.sortBooksByTittle();
                        break;
                    
                    case 8:
                        // Sort books by author and display them
                        lib.sortBooksByAuthor();
                        break;
                    
                    case 9:
                        System.out.print("Enter Member ID: ");
                        String memberId = sc.nextLine();
                        System.out.print("Enter ISBN: ");
                        String bookISBN = sc.nextLine();
                        System.out.print("Enter Due Date (dd-MM-yyyy): ");
                        LocalDate dueDate = LocalDate.parse(sc.nextLine(), formatter);

                        // Find the member using the entered ID
                        Member member = lib.getMembers().stream().filter(m -> m.id.equals(memberId)).findFirst().orElse(null);
                        if (member != null) 
                        {
                            lib.issueBook(member, bookISBN, dueDate);
                        } 
                        else 
                        {
                            System.out.println("Member not found!");
                        }
                        break;
                    
                    case 10:
                        // Return an issued book
                        System.out.print("Enter Member ID: ");
                        String returnMemberId = sc.nextLine();
                        System.out.print("Enter ISBN: ");
                        String returnISBN = sc.nextLine();
                        System.out.print("Enter Return Date (dd-MM-yyyy): ");
                        LocalDate returnDate = LocalDate.parse(sc.nextLine(), formatter);

                        // Find the member using the entered ID
                        Member returnMember = lib.getMembers().stream().filter(m -> m.id.equals(returnMemberId)).findFirst().orElse(null);
                        if (returnMember != null) 
                        {
                            try 
                            {
                                lib.returnBook(returnMember, returnISBN, returnDate);
                            } 
                            catch (BookNotIssuedException e) 
                            {
                                System.out.println(e.getMessage());
                            }
                        } 
                        else 
                        {
                            System.out.println("Member not found!");
                        }
                        break;
                    
                    case 11:
                        // Display all currently issued books along with their due days
                        if (lib.getIssuedBooks().isEmpty()) 
                        {
                            System.out.println("No books have been issued.");
                        } 
                        else 
                        {
                            System.out.println("Issued Books:");
                            lib.getIssuedBooks().forEach((mem, books) -> {
                                System.out.println(mem.name + " (ID: " + mem.id + ") has issued:");
                                books.forEach((book, due) -> 
                                    System.out.println(" - " + book + " (Due in: " + due + " days)"));
                            });
                        }
                        break;
                    
                    case 12:
                        // Exit the application after saving library data to file
                        lib.saveLibraryData();
                        System.out.println("Exiting... Library data saved.");
                        sc.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid Choice! please try again..");
                }
            } 
            catch (Exception e) 
            {
                // Catch any exceptions, display the error message, and clear the input buffer
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }
        }
    }
}
