package de.unidue.inf.is.domain;

public class Message {
    private String receiver;
    private String sender;
    private String message;
    private int id;

    public Message() {
    }

    public Message(String receiver, String sender, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message= message;
    }

    public Message(String receiver, String sender, String message, int id) {
        this.receiver = receiver;
        this.sender = sender;
        this.message= message;
        this.id = id;
    }

    public String getReceiver() {return receiver; }

    public String getSender() {return sender; }

    public String getMessage(){return message; }

    public int getId(){return id; }
}
