package hotel;

import java.util.Scanner;

public class ConsoleView {
    private static ConsoleView instance;
    private final Scanner scanner = new Scanner(System.in);

    private ConsoleView() {
    }

    public static ConsoleView getInstance() {
        if (instance == null) {
            instance = new ConsoleView();
        }
        return instance;
    }

    public void showMenu() {
        System.out.println("\n=== МЕНЮ ===");
        System.out.println("1. Добавить номер");
        System.out.println("2. Добавить услугу");
        System.out.println("3. Изменить цену номера");
        System.out.println("4. Изменить статус номера");
        System.out.println("5. Изменить цену услуги");
        System.out.println("6. Бронирование номера");
        System.out.println("7. Заселение");
        System.out.println("8. Выселение");
        System.out.println("9. Показать все номера (сортировка)");
        System.out.println("10. Показать свободные номера (сортировка)");
        System.out.println("11. Общее число постояльцев и свободных номеров");
        System.out.println("12. Показать свободные номера на дату");
        System.out.println("13. Рассчитать сумму оплаты гостя");
        System.out.println("14. Показать последние 3 бронирования");
        System.out.println("15. Показать услуги гостя (сортировка)");
        System.out.println("16. Показать цены на номера и услуги");
        System.out.println("17. Показать детали номера");
        System.out.println("18. Показать всех гостей");
        System.out.println("19. Добавить услугу гостю");
        System.out.println("20. Экспорт данных в CSV");
        System.out.println("21. Импорт данных из CSV");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    public int readInt() {
        while (true) {
            if (scanner.hasNextInt()) {
                int val = scanner.nextInt();
                scanner.nextLine();
                return val;
            } else {
                System.out.print("Ошибка: введите целое число: ");
                scanner.nextLine();
            }
        }
    }

    public double readDouble() {
        while (true) {
            if (scanner.hasNextDouble()) {
                double val = scanner.nextDouble();
                scanner.nextLine();
                return val;
            } else {
                System.out.print("Ошибка: введите число (можно с плавающей точкой): ");
                scanner.nextLine();
            }
        }
    }

    public int readInt(String prompt) {
        System.out.print(prompt);
        return readInt();
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
