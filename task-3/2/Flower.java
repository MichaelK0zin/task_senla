package flowers;

abstract class Flower {
    private String name;
    private double cost;
    private String color;
    private int amount;

    public Flower(String name, double cost, String color, int amount) {
        this.name = name;
        this.cost = cost;
        this.color = color;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public String getColor() {
        return color;
    }

    public int getAmount() {
        return amount;
    }
}
