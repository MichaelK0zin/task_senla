package flowers;

public abstract class Flower {
    private String name;
    private double cost;
    private String color;

    public Flower(String name, double cost, String color) {
        this.name = name;
        this.cost = cost;
        this.color = color;
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

}
