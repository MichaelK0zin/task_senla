package flowers;

import java.util.ArrayList;
import java.util.List;

public class Bouquet {
    private List<Flower> flowers = new ArrayList<>();

    public void addFlower(Flower flower) {
        flowers.add(flower);
    }

    public double calculateTotalCost() {
        double sum = 0;
        for (Flower flower : flowers) {
            sum += flower.getCost() * flower.getAmount();
        }
        return sum;
    }

    public void printBouquet() {
        System.out.println("Состав букета: ");
        for (Flower flower : flowers) {
            System.out.println(flower.getName() + "(" + flower.getColor() + " x" + flower.getAmount() + ")");
        }
        System.out.println("Общая стоимость: " + calculateTotalCost());
    }
}
