package org.model;

public class DriverProduct {
   private String position;
   private String name;
   private String article;
   private String barcode;
   private double count;
   private String notFound;
   private String order;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    public String getNotFound() {
        return notFound;
    }

    public void setNotFound(String notFound) {
        this.notFound = notFound;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public DriverProduct(String position, String name, String article, String barcode, double count, String notFound, String order) {
        this.position = position;
        this.name = name;
        this.article = article;
        this.barcode = barcode;
        this.count = count;
        this.notFound = notFound;
        this.order = order;
    }

    @Override
    public String toString() {
        return "DriverProduct{" +
                "position=" + position +
                ", |name='" + name +
                ", |article='" + article +
                ", |barcode=" + barcode +
                ", |count=" + count +
                ", |notFound='" + notFound +
                ", |order=" + order +
                '}';
    }
}