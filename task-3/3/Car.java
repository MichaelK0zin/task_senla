package carassembly;

public class Car implements IProduct {
    private CarBody body;
    private CarChasis chasis;
    private CarEngine engine;

    public void installFirstPart(IProductPart part) {
        this.body = (CarBody) part;
        System.out.println("Кузов создан");
    }

    public void installSecondPart(IProductPart part) {
        this.chasis = (CarChasis) part;
        System.out.println("Шасси установлено");
    }

    public void installThirdPart(IProductPart part) {
        this.engine = (CarEngine) part;
        System.out.println("Двигатель установлен");
    }

    public String toString() {
        return "Автомобиль собран";
    }
}
