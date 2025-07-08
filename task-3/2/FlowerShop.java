package flowers;

public class FlowerShop {
    public static void main(String[] args) {

        Bouquet bouquet = new Bouquet();

        bouquet.addFlower(new Rose("Красная"), 2);
        bouquet.addFlower(new Tulip("Желтый"), 3);
        bouquet.addFlower(new Carnation("Белая"), 1);
        bouquet.addFlower(new Rose("Белая"), 3);

        bouquet.printBouquet();
    }
}
