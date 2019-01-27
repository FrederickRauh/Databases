package de.unidue.inf.is.domain;

public final class Advert {

    private double price;
    private String text;
    private String title;
    private String creator;
    private String status;
    private String timeStamp;
    private int id;

    public Advert() {
    }

    public Advert(double price, String text, String title) {
        this.price = price;
        this.text = text;
        this.title = title;
    }
    public Advert(double price, String text, String title, int id) {
        this.price = price;
        this.text = text;
        this.title = title;
        this.id = id;
    }
    public Advert(double price, String text, String title, String creator,  String status, String timeStamp) {
        this.price = price;
        this.text = text;
        this.title = title;
        this.creator = creator;
        this.status = status;
        this.timeStamp = timeStamp;
    }

    public double getPrice() {return price;}

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getCreator(){ return creator;}

    public String getStatus(){ return status;}

    public String getTimeStamp(){ return timeStamp;}

    public int getId() { return  id;}
}