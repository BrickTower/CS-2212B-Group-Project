package com.mapstob.group35.core;

/**
 * User object meant to be extended for use with the Student and Parent classes.
 * <br><br>
 * Any methods created within the initial construction can be extracted using its corresponding Getter.
 * @version 0.01
 * @author Ethan Gregory
 */
public class User {
    private String userName;
    private String password;

    /**
       User constructor. Creates a new User object.

       @param userName String representing the login's userName
       @param password String representing the account's password
       @author Ethan Gregory
     */
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Returns a String representation of the username
     * @return the User's userName, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * Returns a String representation of the User's password
     * @return the User's password, represented as a String
     * @see String
     * @author Ethan Gregory
     */
    public String getPassword() {
        return this.password;
    }
}
