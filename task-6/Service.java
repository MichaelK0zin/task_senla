package hotel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Service {
    private static int idCounter = 1;
    private int id;
    private final String name;
    private double currentPrice;

    private Date orderedDate;
    private Double priceAtOrderTime;

    public Service(String name, double currentPrice) {
        this.id = idCounter++;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Service(int id, String name, double currentPrice) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;

        if (id >= idCounter) {
            idCounter = id + 1;
        }
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
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

    public String toCsv() {
        return String.join(",",
                String.valueOf(id),
                name,
                String.valueOf(currentPrice));
    }

    public static Service fromCsv(String line) {
        String[] parts = line.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        double price = Double.parseDouble(parts[2]);

        return new Service(id, name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Service service)) return false;
        return id == service.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
