package de.unidue.inf.is.domain;

public final class Advert {

    private double price;
    private String text;
    private String title;
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

    public double getPrice() {return price;}

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public int getId() { return  id;}
}