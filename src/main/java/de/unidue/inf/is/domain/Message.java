package de.unidue.inf.is.domain;

public class Message {
    private String receiver;
    private String sender;
    private String message;

    public Message() {
    }

    public Message(String receiver, String sender, String message) {
        this.receiver = receiver;
        this.sender = sender;
        this.message= message;

    }

    public String getReceiver() {return receiver; }

    public String getSender() {return sender; }

    public String getMessage(){return message; }
}
