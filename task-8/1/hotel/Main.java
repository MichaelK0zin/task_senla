package hotel;

import config.ConfigInjector;

public class Main {
    public static void main(String[] args) {
        try {
            AppSettings settings = new AppSettings();
            ConfigInjector.inject(settings);

            HotelFactory factory = new HotelFactory();
            HotelAdmin admin = HotelAdmin.getInstance(factory);

            admin.loadState("state.ser");

            admin.setSettings(settings);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                admin.saveState("state.ser");
            }));

            ConsoleView view = ConsoleView.getInstance();
            HotelController controller = HotelController.getInstance(admin, view);

            controller.run();

        } catch (Exception e) {
            System.err.println("Критическая ошибка при запуске программы: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
