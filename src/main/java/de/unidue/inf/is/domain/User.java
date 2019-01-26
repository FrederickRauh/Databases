package de.unidue.inf.is.domain;

public final class User {

    private String firstname;
    private String lastname;
    private String username;

    public User() {
    }

    public User(String firstname, String lastname, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username= username;

    }

    public String getFirstname() {return firstname; }

    public String getLastname() {return lastname; }

    public String getUsername(){return username; }
}