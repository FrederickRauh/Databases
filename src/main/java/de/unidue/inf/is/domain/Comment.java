package de.unidue.inf.is.domain;
import java.sql.Timestamp;


public final class Comment {

    private String text;
    private int id;
    private Timestamp created;

    public Comment() {
    }

    public Comment(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public Comment(String text, Timestamp created, int id) {
        this.text = text;
        this.created = created;
        this.id = id;
    }


    public String getText() {
        return text;
    }


    public int getId() {
        return id;
    }


    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}