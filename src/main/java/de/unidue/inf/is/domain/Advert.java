package de.unidue.inf.is.domain;

public final class Advert {

    private double price;
    private String text;
    private String title;

    public Advert() {
    }


    public Advert(double price, String text, String title) {
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