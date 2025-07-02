package hotel;

public class Service {
    private final String name;
    private double price;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
        System.out.println("Цена услуги \"" + name + "\" изменена на " + newPrice);
    }

    public String getName() {
        return name;
    }
}

