package hotel;

import config.ConfigInjector;
import di.DependencyInjector;

public class Main {
    public static void main(String[] args) {
        try {
            AppSettings settings = new AppSettings();
            ConfigInjector.inject(settings);

            HotelFactory factory = new HotelFactory();
            HotelAdmin admin = new HotelAdmin();
            ConsoleView view = new ConsoleView();
            HotelController controller = new HotelController();


            admin.loadState("state.ser");

            admin.setSettings(settings);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                admin.saveState("state.ser");
            }));

            DependencyInjector injector = new DependencyInjector();

            injector.register(AppSettings.class, settings);
            injector.register(HotelFactory.class, factory);
            injector.register(HotelAdmin.class, admin);
            injector.register(ConsoleView.class, view);
            injector.register(HotelController.class, controller);

            injector.injectDependencies(admin);
            injector.injectDependencies(view);
            injector.injectDependencies(controller);

            controller.run();

        } catch (Exception e) {
            System.err.println("Критическая ошибка при запуске программы: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
