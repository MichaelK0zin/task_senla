package hotel;

public class Main {
    public static void main(String[] args) {
        try {
            HotelFactory factory = new HotelFactory();

            HotelAdmin admin = HotelAdmin.getInstance(factory);
            ConsoleView view = ConsoleView.getInstance();
            HotelController controller = HotelController.getInstance(admin, view);

            controller.run();
        } catch (Exception e) {
            System.err.println("Критическая ошибка при запуске программы: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
