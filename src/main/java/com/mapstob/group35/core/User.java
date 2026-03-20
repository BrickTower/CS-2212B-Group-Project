package com.mapstob.group35.core;

/**
 * User object meant to be extended for use with the Student and Guardian classes.
 * <br><br>
 * Any methods created within the initial construction can be extracted using its corresponding Getter.
 * @version 0.01
 * @author Ethan Gregory
 */
public class User {
    private int userID; //
    private String userName;
    private String password;
    private String firstName;
    private String lastName;

    /**
       User constructor. Creates a new User object.

       @param userID Integer representing userID
       @param userName String representing the login's userName
       @param password String representing the account's password
       @param firstName String representing user's First Name
       @param lastName String representing user's last/family name
       @author Ethan Gregory
     */
    public User(int userID, String userName, String password, String firstName, String lastName) {
        this.userID = userID; // ASSIGN VIEWER
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Returns an Integer representation of the user's ID
     * @return the User's userID, represented as an integer
     * @see Integer
     * @author Ethan Gregory
     */
    public int getUserID() {
        return this.userID;
    }

    /**
     * Returns an String representation of the username
     * @return the User's userName, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Returns an String representation of the User's password
     * @return the User's password, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Returns an String representation of the User's full name (First name + Last Name
     * @return the User's full name, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
