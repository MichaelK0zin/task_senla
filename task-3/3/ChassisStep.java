package carassembly;

public class ChassisStep  implements ILineStep {
    @Override
    public IProductPart buildProductPart() {
        System.out.println("Крепим шасси");
        return new CarChasis();
    }
}
