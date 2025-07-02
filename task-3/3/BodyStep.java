package carassembly;

public class BodyStep implements ILineStep{
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Делаем кузов автомобиля");
        return new CarBody();
    }
}
