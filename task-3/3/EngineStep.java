package carassembly;

public class EngineStep implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Устанавливаем двигатель");
        return new CarEngine();
    }
}
