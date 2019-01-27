package de.unidue.inf.is.domain;

public class Comment {

    private int id;
    private String text;
    private String timeStamp;
    private String username;

    public Comment(){}

    public Comment(int id, String text, String timeStamp){
        this.id = id;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public Comment(String username, String text, String timeStamp){
        this.username = username;
        this.text = text;
        this.timeStamp = timeStamp;
    }

    public int getId(){ return id;}

    public String getText(){ return text;}

    public String getTimeStamp(){ return timeStamp;}

    public String getUsername(){ return username;}
}
