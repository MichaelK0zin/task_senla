package hotel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Service {
    private final String name;
    private double currentPrice;

    private Date orderedDate;
    private Double priceAtOrderTime;

    public Service(String name, double currentPrice) {
        this.name = name;
        this.currentPrice = currentPrice;
    }

     public static Service createOrdered(String name, double priceAtOrderTime, Date date) {
        Service ordered = new Service(name, priceAtOrderTime);
        ordered.orderedDate = date;
        ordered.priceAtOrderTime = priceAtOrderTime;
        return ordered;
    }

    public void setPrice(double newPrice) {
        this.currentPrice = newPrice;
        System.out.println("Цена услуги \"" + name + "\" изменена на " + newPrice);
    }

    public String getName() {
        return name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public Double getPriceAtOrderTime() {
        return priceAtOrderTime != null ? priceAtOrderTime : currentPrice;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    @Override
    public String toString() {
        if (orderedDate != null && priceAtOrderTime != null) {
            return name + " (" + priceAtOrderTime + " руб., " +
                    new SimpleDateFormat("yyyy-MM-dd").format(orderedDate) + ")";
        } else {
            return name + " (" + currentPrice + " руб.)";
        }
    }
}
