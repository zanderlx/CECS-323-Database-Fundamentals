/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs.pkg323.java.project;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Calendar;
import java.util.Scanner;

/**
 *
 * @author Jose Ramirez
 * @author Lexzander Saplan
 *
 */
public class CECS323JavaTermProject {

    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat = "%-5s%-15s%-15s%-15s\n";
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";

    /**
     * Takes the input string and outputs "N/A" if the string is empty or null.
     *
     * @param input The string to be mapped.
     * @return Either the input string or "N/A" as appropriate.
     */
    public static String dispNull(String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0) {
            return "N/A";
        } else {
            return input;
        }
    }

    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        Calendar cal = Calendar.getInstance();
        boolean loopDatabaseEntry = true;

        // Keep looping if the database credentials are incorrect
        while (loopDatabaseEntry) {
            System.out.println("Enter your database credentials:");
            System.out.print("Name of the database (not the user account): ");
            DBNAME = in.nextLine();
            System.out.print("Database user name: ");
            USER = in.nextLine();
            System.out.print("Database password: ");
            PASS = in.nextLine();

            // Change Password Command in SQL in case programmer wants to change their password:
            // call SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.<my user name>', '<your new password>')
            
            //Constructing the database URL connection string
            DB_URL = DB_URL + DBNAME + ";user=" + USER + ";password=" + PASS;

            // Java variables
            String sql; // Variable to hold SQL Commands
            String userInput = ""; // String used to hold user input throughout the program
            boolean repeatMenu = true; // Boolean to check if menu should be looped again
            int rowCount = 0;

            // SQL variables
            ResultSet rs = null; // Result Set of the SQL Commands
            Statement stmt = null;  // Initialize the statement that we're using
            Connection conn = null; // Initialize the connection
            PreparedStatement pstmt = null; // Initialize the prepared statement that we're using using

            try {
                //STEP 2: Register JDBC driver
                Class.forName("org.apache.derby.jdbc.ClientDriver");

                //STEP 3: Open a connection
                System.out.println("Connecting to database...");
                conn = DriverManager.getConnection(DB_URL);
                System.out.println("Connected!");

                //STEP 4: Execute a query
                System.out.println("\nCreating statement...");
                stmt = conn.createStatement();

                // Access to the database was a success
                loopDatabaseEntry = false;

                //STEP 5: Extract data from result set 
                while (repeatMenu) { // Keep looping menu options until 'Exit' is chosen

                    // Prints menu and checks for validation of menu choice
                    int menuChoice = displayMenu();

                    // Checks the menu choice of the user
                    switch (menuChoice) {

                        // List all writing groups
                        case 1:
                            sql = "SELECT * FROM writingGroup";
                            rs = stmt.executeQuery(sql);

                            int writingGroupCount = 1;
                            System.out.println("\nListing All Writing Groups: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String groupName = rs.getString("groupname");

                                //Display values
                                System.out.print(writingGroupCount + ") ");
                                System.out.printf("%-10s    \n", dispNull(groupName));
                                writingGroupCount++;
                            }
                            break;

                        // List all data for a group specified by you
                        case 2:
                            sql = "SELECT * FROM writingGroup";
                            rs = stmt.executeQuery(sql);

                            int writingGroupCountForCase2 = 1;
                            System.out.println("\nListing All Writing Groups: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String groupName = rs.getString("groupname");

                                //Display values
                                System.out.print(writingGroupCountForCase2 + ") ");
                                System.out.printf("%-10s    \n", dispNull(groupName));
                                writingGroupCountForCase2++;
                            }

                            System.out.print("\nPlease enter the group you want information on: ");
                            userInput = in.nextLine();

                            sql = "select * from writingGroup where groupName = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, userInput);
                            rs = pstmt.executeQuery();

                            System.out.print("\nInformation on '" + userInput + "': ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String groupName = rs.getString("groupName");
                                String headWriter = rs.getString("headWriter");
                                String yearFormed = rs.getString("yearFormed");
                                String subject = rs.getString("subject");

                                //Display values
                                System.out.printf("\nGroup Name: %-10s    \n", dispNull(groupName));
                                System.out.printf("Head Writer: %-10s    \n", dispNull(headWriter));
                                System.out.printf("Year Formed: %-10s    \n", dispNull(yearFormed));
                                System.out.printf("Subject: %-10s    \n", dispNull(subject));
                                rowCount++;
                            }
                            if (rowCount == 0) {
                                System.out.print("N/A \nThe writing group '" + userInput + "' does not exist in the database!");
                            }

                            rowCount = 0;
                            break;

                        // List all publishers
                        case 3:
                            sql = "SELECT * FROM publishers";
                            rs = stmt.executeQuery(sql);

                            int publisherCount = 1;
                            System.out.println("\nListing All Publishers: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String publisherName = rs.getString("publisherName");

                                //Display values
                                System.out.print(publisherCount + ") ");
                                System.out.printf("%-10s    \n", dispNull(publisherName));
                                publisherCount++;
                            }
                            break;

                        // List all data for a publisher specified by you
                        case 4:
                            sql = "SELECT * FROM publishers";
                            rs = stmt.executeQuery(sql);

                            int publisherCountForCase4 = 1;
                            System.out.println("\nListing All Publishers: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String publisherName = rs.getString("publisherName");

                                //Display values
                                System.out.print(publisherCountForCase4 + ") ");
                                System.out.printf("%-10s    \n", dispNull(publisherName));
                                publisherCountForCase4++;
                            }

                            System.out.print("\nPlease enter a publisher's name: ");
                            userInput = in.nextLine();

                            sql = "SELECT * FROM publishers WHERE publisherName = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, userInput);
                            rs = pstmt.executeQuery();

                            System.out.print("\nInformation on '" + userInput + "': ");
                            while (rs.next()) {
                                String publisherName = rs.getString("publishername");
                                String publisherAddress = rs.getString("publisheraddress");
                                String publisherPhone = rs.getString("publisherphone");
                                String publisherEmail = rs.getString("publisheremail");

                                //Display values
                                System.out.printf("\nPublisher Name: %-10s    \n", dispNull(publisherName));
                                System.out.printf("Publisher Address: %-10s    \n", dispNull(publisherAddress));
                                System.out.printf("Publisher Phone: %-10s    \n", dispNull(publisherPhone));
                                System.out.printf("Publisher Email: %-10s    \n", dispNull(publisherEmail));
                                rowCount++;
                            }
                            if (rowCount == 0) {
                                System.out.print("N/A \nThe publisher '" + userInput + "' does not exist in the database!");
                            }

                            rowCount = 0;
                            break;

                        // List all book titles    
                        case 5:
                            sql = "SELECT * FROM books";
                            rs = stmt.executeQuery(sql);

                            int bookCount = 1;
                            System.out.println("\nListing All Book Titles: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String bookTitle = rs.getString("booktitle");

                                //Display values
                                System.out.print(bookCount + ") ");
                                System.out.printf("%-10s    \n", dispNull(bookTitle));
                                bookCount++;
                            }
                            break;

                        // List all the data for a book specified by you
                        case 6:
                            sql = "SELECT * FROM books";
                            rs = stmt.executeQuery(sql);

                            int bookCountForCase6 = 1;
                            System.out.println("\nListing All Book Titles: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String bookTitle = rs.getString("booktitle");

                                //Display values
                                System.out.print(bookCountForCase6 + ") ");
                                System.out.printf("%-10s    \n", dispNull(bookTitle));
                                bookCountForCase6++;
                            }

                            System.out.print("\nPlease enter a book title: ");
                            userInput = in.nextLine();

                            sql = "SELECT * FROM books WHERE booktitle = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, userInput);
                            rs = pstmt.executeQuery();

                            System.out.print("\nInformation on '" + userInput + "': ");
                            while (rs.next()) {
                                String groupname = rs.getString("groupname");
                                String booktitle = rs.getString("booktitle");
                                String publishername = rs.getString("publishername");
                                String yearpublished = rs.getString("yearpublished");
                                String numberofpages = rs.getString("numberpages");

                                //Display values
                                System.out.printf("\nWriting Group Name: %-10s    \n", dispNull(groupname));
                                System.out.printf("Book Title: %-10s    \n", dispNull(booktitle));
                                System.out.printf("Publisher Name: %-10s    \n", dispNull(publishername));
                                System.out.printf("Year Published: %-10s    \n", dispNull(yearpublished));
                                System.out.printf("Number of Pages: %-10s    \n", dispNull(numberofpages));
                                rowCount++;
                            }
                            if (rowCount == 0) {
                                System.out.print("N/A \nThe book '" + userInput + "' does not exist in the database!");
                            }
                            rowCount = 0;
                            break;

                        // Insert a new book
                        case 7:
                            boolean check = true;
                            String groupForABook = "",
                             newBookTitle = "",
                             publisherOfBook = "";
                            int yearPublished = 0,
                             numOfPages = 0;

                            while (check) {
                                try {
                                    System.out.println("\nTo insert a new book, please enter the following information: ");

                                    sql = "SELECT * FROM writingGroup";
                                    rs = stmt.executeQuery(sql);

                                    int writingGroupCountForCase7 = 1;
                                    System.out.println("\nListing All Writing Groups: ");
                                    while (rs.next()) {
                                        //Retrieve by column name                
                                        String groupName = rs.getString("groupname");

                                        //Display values
                                        System.out.print(writingGroupCountForCase7 + ") ");
                                        System.out.printf("%-10s    \n", dispNull(groupName));
                                        writingGroupCountForCase7++;
                                    }

                                    System.out.print("Enter a writing group from the list above: ");
                                    groupForABook = in.nextLine();

                                    System.out.print("\nEnter the title of the book: ");
                                    newBookTitle = in.nextLine();

                                    if (newBookTitle.isEmpty() || newBookTitle.trim().isEmpty()) {
                                        throw new Exception();
                                    }

                                    sql = "SELECT * FROM publishers";
                                    rs = stmt.executeQuery(sql);

                                    int publisherCountForCase7 = 1;
                                    System.out.println("\nListing All Publishers: ");
                                    while (rs.next()) {
                                        //Retrieve by column name                
                                        String publisherName = rs.getString("publisherName");

                                        //Display values
                                        System.out.print(publisherCountForCase7 + ") ");
                                        System.out.printf("%-10s    \n", dispNull(publisherName));
                                        publisherCountForCase7++;
                                    }

                                    System.out.print("Enter a publisher from the list above: ");
                                    publisherOfBook = in.nextLine();

                                    System.out.print("\nEnter the year the book was published: ");
                                    yearPublished = in.nextInt();
                                    in.nextLine();

                                    System.out.print("\nEnter the number of pages for the book: ");
                                    numOfPages = in.nextInt();
                                    in.nextLine();

                                    if (numOfPages < 0) {
                                        System.out.println("Sorry, the number of pages cannot be less than zero...Please try again!");
                                    }
                                    if (yearPublished < 1455 || yearPublished > cal.get(Calendar.YEAR)) {
                                        System.out.println("Sorry, the year published can't be less than 1455 or greater than the current year...Please try again!");
                                    } else {
                                        check = false;
                                    }

                                } catch (Exception e) {
                                    System.out.println("Sorry, you entered an invalid value... Please try again!");
                                }
                            }

                            if (numOfPages < 0 || (yearPublished < 1455 || yearPublished > cal.get(Calendar.YEAR))) {
                                System.out.println("\n'" + newBookTitle + "' could not be added!");
                                break;
                            }

                            // Inserts a new book with user values
                            try {
                                sql = "INSERT INTO books VALUES(?, ?, ?, ?, ?)";
                                PreparedStatement updateBooks = conn.prepareStatement(sql);
                                updateBooks.setString(1, groupForABook);
                                updateBooks.setString(2, newBookTitle);
                                updateBooks.setString(3, publisherOfBook);
                                updateBooks.setInt(4, yearPublished);
                                updateBooks.setInt(5, numOfPages);

                                updateBooks.executeUpdate();
                                System.out.println("'" + newBookTitle + "' has been added to the list of known books!");
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                                System.out.println("\n'" + newBookTitle + "' could not be added!");
                            }
                            break;

                        // Insert a new publisher
                        case 8:
                            boolean loopPublisher = true;
                            String publisherName = "",
                             publisherAddress = "",
                             publisherPhone = "",
                             publisherEmail = "",
                             oldPublisher = "";

                            do {
                                try {
                                    sql = "INSERT INTO publishers VALUES(?, ?, ?, ?)";
                                    PreparedStatement updatePublishers = conn.prepareStatement(sql);

                                    System.out.print("\nEnter the new publisher's name: ");
                                    publisherName = in.nextLine();

                                    if (publisherName.isEmpty() || publisherName.trim().isEmpty()) {
                                        throw new Exception();
                                    }

                                    updatePublishers.setString(1, publisherName);

                                    System.out.print("Enter the publisher's address: ");
                                    publisherAddress = in.nextLine();
                                    updatePublishers.setString(2, publisherAddress);

                                    System.out.print("Enter the publisher's phone number: ");
                                    publisherPhone = in.nextLine();
                                    updatePublishers.setString(3, publisherPhone);

                                    System.out.print("Enter the publisher's email address: ");
                                    publisherEmail = in.next();
                                    updatePublishers.setString(4, publisherEmail);

                                    in.nextLine();

                                    sql = "SELECT * FROM publishers";
                                    rs = stmt.executeQuery(sql);

                                    publisherCount = 1;
                                    System.out.println("\nListing All Publishers: ");
                                    while (rs.next()) {
                                        //Retrieve by column name                
                                        String publisherNameForCase8 = rs.getString("publisherName");

                                        //Display values
                                        System.out.print(publisherCount + ") ");
                                        System.out.printf("%-10s    \n", dispNull(publisherNameForCase8));
                                        publisherCount++;
                                    }

                                    System.out.print("\nEnter an exisiting publisher given above: ");
                                    oldPublisher = in.nextLine();

                                    updatePublishers.executeUpdate();
                                    System.out.print("\nUpdating new publisher...");
                                    System.out.println("New publisher has been added!");
                                    //in.nextLine();

                                    sql = "select * from books where publishername = ?";
                                    PreparedStatement insertBook = conn.prepareStatement(sql);
                                    insertBook.setString(1, oldPublisher);

                                    rs = insertBook.executeQuery();
                                    loopPublisher = false;
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                    System.out.println("Please try again!\n");
                                    //in.nextLine();
                                } catch (Exception e) {
                                    System.out.println("You entered an invalid value for publisher Name");
                                }
                            } while (loopPublisher);

                            while (rs.next()) {

                                //Display values
                                sql = "update books set publishername = ? where publishername = ?";
                                PreparedStatement newBookPublisher = conn.prepareStatement(sql);
                                newBookPublisher.setString(1, dispNull(publisherName));
                                newBookPublisher.setString(2, dispNull(oldPublisher));
                                newBookPublisher.executeUpdate();
                                rowCount++;

                                System.out.println("Updated '" + publisherName + "' with books from '" + oldPublisher + "'...");
                                //in.nextLine();
                            }
                            if (rowCount == 0) {
                                System.out.println("The publisher '" + oldPublisher + "' does not exist in the database or does not have a published book");
                            }

                            rowCount = 0;
                            break;

                        // Remove a book specified by user
                        case 9:
                            sql = "SELECT * FROM books";
                            rs = stmt.executeQuery(sql);
                            int bookCountForCase9 = 1;

                            System.out.println("\nListing All Book Titles: ");
                            while (rs.next()) {
                                //Retrieve by column name                
                                String bookTitle = rs.getString("booktitle");

                                //Display values
                                System.out.print(bookCountForCase9 + ") ");
                                System.out.printf("%-10s    \n", dispNull(bookTitle));
                                bookCountForCase9++;
                            }

                            System.out.print("\nPlease enter the book title you wish to remove: ");
                            userInput = in.nextLine();

                            System.out.print("Please enter the writing group for '" + userInput + "': ");
                            String writingGroupForChosenBook = in.nextLine();

                            sql = "DELETE FROM books WHERE booktitle = ? and groupname = ?";
                            pstmt = conn.prepareStatement(sql);
                            pstmt.setString(1, userInput);
                            pstmt.setString(2, writingGroupForChosenBook);

                            int bookData = pstmt.executeUpdate();
                            if (bookData == 0) {
                                System.out.println("Cannot remove '" + userInput + "'... It does not exist in the database!");
                            } else {
                                System.out.println("'" + userInput + "' has been removed from the list of known books!");
                            }
                            break;

                        // Exit
                        case 10:
                            repeatMenu = false;
                            break;
                    }
                }

                //STEP 6: Clean-up environment
                if (rs != null) {
                    rs.close();
                }
                stmt.close();
                conn.close();
            } catch (SQLNonTransientConnectionException sntce) {
                System.out.println("Error! Please check the credentials of your database\n");
            } catch (SQLException se) {
                //Handle errors for JDBC
                se.printStackTrace();
            } catch (Exception e) {
                //Handle errors for Class.forName
                e.printStackTrace();
            } finally {
                //finally block used to close resources
                USER = "";
                PASS = "";
                DBNAME = "";
                DB_URL = "jdbc:derby://localhost:1527/";
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                } catch (SQLException se2) {
                    // nothing we can do
                }
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException se) {
                    se.printStackTrace();
                }//end finally try
            }//end try
        }
        System.out.println("\nGoodbye!");
    }//end main

    public static int displayMenu() {
        boolean check = true;
        Scanner in = new Scanner(System.in);
        int choice = 10;

        do { // Display the menu options
            System.out.println("\n\nPlease select one of the following options: ");
            System.out.println("(1) List all writing groups");
            System.out.println("(2) List all data for a group specified by you");
            System.out.println("(3) List all publishers");
            System.out.println("(4) List all the data for a publisher specified by you");
            System.out.println("(5) List all book titles");
            System.out.println("(6) List all the data for a book specified by you");
            System.out.println("(7) Insert a new book");
            System.out.println("(8) Insert a new publisher");
            System.out.println("(9) Remove a book");
            System.out.println("(10) Exit");

            try {
                choice = in.nextInt();
                if (choice < 1 || choice > 10) {//if user choice is out of bounds of the menu
                    System.out.println("Sorry, that is not a valid choice... Please try again!");
                    in.nextLine();
                } else {//breaks loop if user entered a valid option
                    in.nextLine();
                    check = false;
                }
            } catch (InputMismatchException e) {//if user inputs anything other than an integer
                System.out.println("Sorry, that is not a valid choice... Please try again!");
                in.nextLine();
            }
        } while (check);

        return choice;//return number for case statement 
    }//end of menu
}//end FirstExample

