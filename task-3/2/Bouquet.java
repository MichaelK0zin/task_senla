package flowers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bouquet {
    private List<Flower> flowers = new ArrayList<>();

    public void addFlower(Flower flower, int amount) {
        for (int i = 0; i < amount; i++) {
            flowers.add(flower);
        }
    }

    public double calculateTotalCost() {
        double sum = 0;
        for (Flower flower : flowers) {
            sum += flower.getCost();
        }
        return sum;
    }

    public void printBouquet() {
        System.out.println("Состав букета:");

        Map<String, Integer> counts = new HashMap<>();
        for (Flower flower : flowers) {
            String key = flower.getName() + " (" + flower.getColor() + ")";
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println(entry.getKey() + " x" + entry.getValue());
        }
        System.out.println("Общая стоимость: " + calculateTotalCost());
    }
}
