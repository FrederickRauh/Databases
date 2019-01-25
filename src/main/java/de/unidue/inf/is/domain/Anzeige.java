package de.unidue.inf.is.domain;

public final class Anzeige {

    private double price;
    private String text;
    private String title;

    public Anzeige() {
    }


    public Anzeige(double price, String text, String title) {
        this.price = price;
        this.text = text;
        this.title = title;

    }

    public double getPrice() {return price;}

    public String getText() {
        return text;
    }


    public String getTitle() {
        return title;
    }

}