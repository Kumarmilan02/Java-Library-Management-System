/*
 * Enhance the Library Management System (Intermediate Version)
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
 */
import java.util.*;

class Person
{
    protected String name;
    protected String id;
    protected String contact;

    public Person(String name, String id, String contact)
    {
        this.name = name;
        this.id = id;
        this.contact = contact;
    }
    public String toString()
    {
        return "Name: " + name + "\t Id: "+ id + "\t Contact: " + contact;
    }
}

//Inheritance
class Member extends Person
{
    public Member(String name, String id, String contact)
    {
        super(name,id,contact);
    }
}
//  Comparable interface is used to compare an object of the same class with an instance of that class, 
// and it provides ordering of data for objects of the user-defined class.
// Comparable: It is used to define the natural ordering of the objects within the class.
//Comparator: It is used to define custom sorting logic externally.

// Book class implemneting Comparable for Sorting
class Book implements Comparable<Book>
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
        return "Tittle: " + tittle + "\t Author: " + author + "\t ISBN: " + ISBN;
    }

    public int compareTo(Book other)
    {
        return this.tittle.compareToIgnoreCase(other.tittle);
    }
}

//Comparator for soting by Author
class AuthorComparator implements Comparator<Book>
{
    public int compare(Book b1, Book b2)
    {
        return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
    }
}


// manage books and members
class Library
{
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();
    private HashMap<Member, List<Book>> issuedBooks = new HashMap<>();
    
    // Add a book to the Library
    public void addBook(Book book)
    {
        books.add(book);
        System.out.println("\nBook added sucessfully!");
    }
    
    // Add member to the Library
    public void addMember(Member member)
    {
        members.add(member);
        System.out.println(member + " added sucessfully!..");
    }

    // Display all books
    public void displayBooks()
    {
        if(books.isEmpty())
        {
            System.out.println("No Books available in the Library.");
            return;
        }
        System.out.println("\nList of Books in Library..");
        for(Book book:books)
        {
            System.out.println(book);
        }
    }

    // Display all members
    public void displayMembers()
    {
        if(members.isEmpty())
        {
            System.out.println("No members in Library.");
            return;
        }
        System.out.println("\nLibrary Members: ");
        for(Member member:members)
        {
            System.out.println(member);
        }
    }
    
    // Search books Author or tittle
    public void searchBook(String query)
    {
        boolean found = false;
        for(Book book: books)
        {
            if(book.getTittle().equalsIgnoreCase(query) || book.getAuthor().equalsIgnoreCase(query))
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

    // Sort books by Tittle
    public void sortBooksByTittle()
    {
        Collections.sort(books);
        System.out.println("Books sorted by tittle.");
        System.out.println("\nList of Books in Library..");
        for(Book book:books)
        {
            System.out.println(book);
        }
    }

    // Sort books by Author
    public void sortBooksByAuthor()
    {
        Collections.sort(books, new AuthorComparator());
        System.out.println("Books sorted by Author.");
        System.out.println("\nList of Books in Library..");
        for(Book book:books)
        {
            System.out.println(book);
        }
    }

    // Issue a Books by a member
    public  void issueBook(Member member, Book book)
    {
        if(!books.contains(book))
        {
            System.out.println("Book not available.");
        }
        issuedBooks.putIfAbsent(member, new ArrayList<>());
        issuedBooks.get(member).add(book);
        books.remove(book);
        System.out.println("Book issued sucessfully to " + member.name);
        
    }

    // Return  a book from a member
    public void returnBook(Member member, Book book)
    {
        if(issuedBooks.containsKey(member))
        {
            List<Book> memberBooks = issuedBooks.get(member);
            Book actualBook = memberBooks.stream().filter(b -> b.getTittle().equalsIgnoreCase(book.getTittle())).findFirst().orElse(null);
            if(actualBook != null)
            {
                memberBooks.remove(actualBook);
                books.add(actualBook);
                System.out.println("Book returned sucessfully.");
                return;
            }  
        }
            System.out.println("This book was not issued to this member.");
    }

    // Display Issued Book
    public void displayIssuedBooks()
    {
        boolean hasIssuedBooks = false;
        for(Map.Entry<Member, List<Book>> entry : issuedBooks.entrySet())
        {
            System.out.println("\nIssued Books: ");
            if(!entry.getValue().isEmpty())
            {
                hasIssuedBooks = true;
                System.out.println(entry.getKey().name + " has borrowed:");
                for(Book book : entry.getValue())
                {
                    System.out.println(" " + book);
                }
            }
        }
        if(!hasIssuedBooks)
        {
            System.out.println("No books have been issued.");
        }
    }

    public List<Member> getMembers()
    {
        return members;
    }
    public List<Book> getBooks()
    {
        return books;
    }
    public Book getIssuedBook(Member member, String bookTittle)
    {
        if(issuedBooks.containsKey(member))
        {
            return issuedBooks.get(member).stream().filter(b -> b.getTittle().equalsIgnoreCase(bookTittle)).findFirst().orElse(null);
        }
        return null;
    }
}

public class Libmgmt_imd
{
    public static void main(String[] args)
    {
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);

        while(true)
        {
            System.out.println("\nLibrary Management System ");
            System.out.println("1. Add Books");
            System.out.println("2. Add Member");
            System.out.println("3. Display All Books");
            System.out.println("4. Display All Members");
            System.out.println("5. Search Books");
            System.out.println("6. Sort Books by Tittle");
            System.out.println("7. Sort Books by Author");
            System.out.println("8. Issue Book");
            System.out.println("9. Return Book");
            System.out.println("10. Display Issued Books");
            System.out.println("11. Exit");
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

                    lib.addBook(new Book(tittle,author,ISBN));
                    break;
                case 2: 
                    System.out.println("Enter Member Name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter Member Id: ");
                    String id = sc.nextLine();
                    System.out.println("Enter Contact: ");
                    String contact = sc.nextLine();

                    lib.addMember(new Member(name,id,contact));
                    break;

                case 3:
                    lib.displayBooks();
                    break;
                
                case 4:
                    lib.displayMembers();
                    break;

                case 5:
                    System.out.println("Enter Book Tittle or Author to search: ");
                    String query = sc.nextLine();
                    lib.searchBook(query);
                    break;
                
                case 6:
                    // System.out.println("Sorting Book by Tittle: ");
                    lib.sortBooksByTittle();
                    break;
                case 7:
                    // System.out.println("Sorting book by Author: ");
                    lib.sortBooksByAuthor();
                    break;

                case 8:
                    System.out.println("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.println("Enter Book Tittle: ");
                    String bookTittle= sc.nextLine();
                    
                    Member memberToIssue = lib.getMembers().stream().filter(m->m.id.equals(memberId)).findFirst().orElse(null);
                    Book bookToIssue = lib.getBooks().stream().filter(b -> b.getTittle().equalsIgnoreCase(bookTittle)).findFirst().orElse(null);
                    if(memberToIssue != null && bookToIssue != null)
                    {
                        lib.issueBook(memberToIssue,bookToIssue);
                    }
                    else
                    {
                        System.out.println("Invalid member ID or Book Tittle.");
                    }
                    break;

                case 9:
                    System.out.println("Enter Member ID: ");
                    String membId = sc.nextLine();
                    System.out.println("Enter Book Tittle: ");
                    String retbookTittle= sc.nextLine();
                    
                    Member memberToReturn = lib.getMembers().stream().filter(m -> m.id.equals(membId)).findFirst().orElse(null);
                    if(memberToReturn == null)
                    {
                        System.out.println("Invalid Member ID...");
                        break;
                    }
                    Book bookToReturn = lib.getIssuedBook(memberToReturn,retbookTittle);
                    if(bookToReturn == null )
                    {
                        System.out.println("This Book was not issued to this meber.");
                        break;
                    }
                    lib.returnBook(memberToReturn,bookToReturn);
                    break;

                case 10:
                    lib.displayIssuedBooks();
                    break;

                case 11:
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