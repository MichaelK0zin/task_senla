package hotel;

import java.text.SimpleDateFormat;
import java.util.*;

public class HotelController {
    private static HotelController instance;
    private final HotelAdmin admin;
    private final ConsoleView view;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private HotelController(HotelAdmin admin, ConsoleView view) {
        this.admin = admin;
        this.view = view;
    }

    public static HotelController getInstance(HotelAdmin admin, ConsoleView view) {
        if (instance == null) {
            instance = new HotelController(admin, view);
        }
        return instance;
    }

    public void run() {
        boolean running = true;
        while (running) {
            view.showMenu();
            int choice = view.readInt();

            switch (choice) {
                case 1 -> addRoom();
                case 2 -> addService();
                case 3 -> changeRoomPrice();
                case 4 -> changeRoomStatus();
                case 5 -> changeServicePrice();
                case 6 -> bookRoom();
                case 7 -> checkIn();
                case 8 -> checkOut();
                case 9 -> listRooms();
                case 10 -> listAvailableRooms();
                case 11 -> countGuestsAndRooms();
                case 12 -> availableOnDate();
                case 13 -> calculateGuestPayment();
                case 14 -> showLastBookings();
                case 15 -> showGuestServices();
                case 16 -> showPrices();
                case 17 -> showRoomDetails();
                case 18 -> listGuests();
                case 19 -> addServiceToGuest();
                case 20 -> exportCsv();
                case 21 -> importCsv();
                case 0 -> running = false;
                default -> view.showMessage("Неверный выбор.");
            }
        }
    }

    private void addRoom() {
        try {
            int number = Integer.parseInt(view.readLine("Номер: "));
            double price = Double.parseDouble(view.readLine("Цена: "));
            int capacity = Integer.parseInt(view.readLine("Вместимость: "));
            int stars = Integer.parseInt(view.readLine("Звезды: "));
            admin.addRoom(number, price, capacity, stars);
            view.showMessage("Номер добавлен.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректные числовые значения.");
        } catch (Exception e) {
            view.showMessage("Ошибка при добавлении номера: " + e.getMessage());
        }
    }

    private void addService() {
        try {
            String name = view.readLine("Название услуги: ");
            double price = Double.parseDouble(view.readLine("Цена: "));
            admin.addService(name, price);
            view.showMessage("Услуга добавлена.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректное число для цены.");
        } catch (Exception e) {
            view.showMessage("Ошибка при добавлении услуги: " + e.getMessage());
        }
    }

    private void changeRoomPrice() {
        try {
            int number = Integer.parseInt(view.readLine("Введите номер комнаты: "));
            double newPrice = Double.parseDouble(view.readLine("Введите новую цену: "));
            admin.setRoomPrice(number, newPrice);
            view.showMessage("Цена комнаты изменена.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректные числовые значения.");
        } catch (Exception e) {
            view.showMessage("Ошибка при изменении цены: " + e.getMessage());
        }
    }

    private void changeRoomStatus() {
        if (!ConfigManager.getInstance().isRoomStatusChangeEnabled()) {
            view.showMessage("Изменение статуса номеров отключено в настройках.");
            return;
        }

        try {
            int number = Integer.parseInt(view.readLine("Введите номер комнаты: "));
            view.showMessage("Выберите статус:");
            for (RoomStatus status : RoomStatus.values()) {
                view.showMessage("- " + status);
            }

            String input = view.readLine("Новый статус (например, AVAILABLE): ").toUpperCase();
            RoomStatus status = RoomStatus.valueOf(input);

            admin.setRoomStatus(number, status);
            view.showMessage("Статус комнаты изменён.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректный номер комнаты.");
        } catch (IllegalArgumentException e) {
            view.showMessage("Неверный статус. Попробуйте снова.");
        } catch (Exception e) {
            view.showMessage("Ошибка при изменении статуса: " + e.getMessage());
        }
    }


    private void changeServicePrice() {
        try {
            String name = view.readLine("Введите название услуги: ");
            double newPrice = Double.parseDouble(view.readLine("Новая цена: "));
            admin.setServicePrice(name, newPrice);
            view.showMessage("Цена услуги изменена.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректное число для цены.");
        } catch (Exception e) {
            view.showMessage("Ошибка при изменении цены услуги: " + e.getMessage());
        }
    }

    private void bookRoom() {
        try {
            String guest = view.readLine("Имя гостя: ");
            int room = Integer.parseInt(view.readLine("Номер комнаты: "));
            Date checkIn = sdf.parse(view.readLine("Дата заезда (yyyy-MM-dd): "));
            Date checkOut = sdf.parse(view.readLine("Дата выезда (yyyy-MM-dd): "));
            String servicesLine = view.readLine("Услуги через запятую (можно пусто): ");
            List<String> serviceNames = servicesLine.isBlank() ? List.of() : Arrays.stream(servicesLine.split(","))
                    .map(String::trim).toList();

            admin.bookRoom(guest, room, checkIn, checkOut, serviceNames);
            view.showMessage("Бронирование успешно.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: номер комнаты должен быть числом.");
        } catch (java.text.ParseException e) {
            view.showMessage("Ошибка: неверный формат даты, используйте yyyy-MM-dd.");
        } catch (Exception e) {
            view.showMessage("Ошибка при бронировании: " + e.getMessage());
        }
    }

    private void checkIn() {
        try {
            int room = Integer.parseInt(view.readLine("Номер комнаты: "));
            Date today = sdf.parse(view.readLine("Дата заезда (yyyy-MM-dd): "));
            admin.checkIn(room, today);
            view.showMessage("Заселение выполнено.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: номер комнаты должен быть числом.");
        } catch (java.text.ParseException e) {
            view.showMessage("Ошибка: неверный формат даты.");
        } catch (Exception e) {
            view.showMessage("Ошибка при заселении: " + e.getMessage());
        }
    }

    private void checkOut() {
        try {
            int room = Integer.parseInt(view.readLine("Номер комнаты: "));
            Date today = sdf.parse(view.readLine("Дата выезда (yyyy-MM-dd): "));
            admin.checkOut(room, today);
            view.showMessage("Выселение выполнено.");
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: номер комнаты должен быть числом.");
        } catch (java.text.ParseException e) {
            view.showMessage("Ошибка: неверный формат даты.");
        } catch (Exception e) {
            view.showMessage("Ошибка при выселении: " + e.getMessage());
        }
    }

    private void listRooms() {
        try {
            view.showMessage("Сортировка по:");
            view.showMessage("1. Цене\n2. Вместимости\n3. Звездам");
            int sortChoice = view.readInt();
            Comparator<Room> comparator = switch (sortChoice) {
                case 2 -> Comparator.comparing(Room::getCapacity);
                case 3 -> Comparator.comparing(Room::getStars);
                default -> Comparator.comparing(Room::getPrice);
            };
            admin.getSortedRooms(comparator)
                    .forEach(r -> view.showMessage(r.getDetails()));
        } catch (Exception e) {
            view.showMessage("Ошибка при выводе списка номеров: " + e.getMessage());
        }
    }

    private void listAvailableRooms() {
        try {
            view.showMessage("Сортировка по:");
            view.showMessage("1. Цене\n2. Вместимости\n3. Звездам");
            int sortChoice = view.readInt();
            Comparator<Room> comparator = switch (sortChoice) {
                case 2 -> Comparator.comparing(Room::getCapacity);
                case 3 -> Comparator.comparing(Room::getStars);
                default -> Comparator.comparing(Room::getPrice);
            };
            admin.getAvailableRoomsOn(new Date(), comparator)
                    .forEach(r -> view.showMessage(r.getDetails()));
        } catch (Exception e) {
            view.showMessage("Ошибка при выводе доступных номеров: " + e.getMessage());
        }
    }

    private void countGuestsAndRooms() {
        try {
            view.showMessage("Всего гостей: " + admin.countGuests());
            view.showMessage("Свободных номеров: " + admin.countAvailableRooms());
        } catch (Exception e) {
            view.showMessage("Ошибка при подсчёте: " + e.getMessage());
        }
    }

    private void availableOnDate() {
        try {
            Date date = sdf.parse(view.readLine("Дата (yyyy-MM-dd): "));
            admin.getAvailableRoomsOn(date, Comparator.comparing(Room::getPrice))
                    .forEach(r -> view.showMessage(r.getDetails()));
        } catch (java.text.ParseException e) {
            view.showMessage("Неверный формат даты.");
        } catch (Exception e) {
            view.showMessage("Ошибка: " + e.getMessage());
        }
    }

    private void calculateGuestPayment() {
        try {
            String guest = view.readLine("Имя гостя: ");
            double total = admin.calculateTotalPayment(guest);
            view.showMessage("Общая сумма оплаты: " + total);
        } catch (Exception e) {
            view.showMessage("Ошибка при расчёте оплаты: " + e.getMessage());
        }
    }

    private void showLastBookings() {
        try {
            int roomNumber = view.readInt("Введите номер комнаты: ");
            int limit = ConfigManager.getInstance().getBookingViewLimit();
            List<String> bookings = admin.getLastBookingsForRoom(roomNumber, limit);

            if (bookings.isEmpty()) {
                view.showMessage("Нет бронирований для указанного номера.");
            } else {
                bookings.forEach(view::showMessage);
            }
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректный номер комнаты.");
        }
    }



    private void showGuestServices() {
        try {
            String guest = view.readLine("Имя гостя: ");
            admin.getGuestServices(guest).forEach(view::showMessage);
        } catch (Exception e) {
            view.showMessage("Ошибка при выводе услуг гостя: " + e.getMessage());
        }
    }

    private void showPrices() {
        try {
            admin.getPrices().forEach(view::showMessage);
        } catch (Exception e) {
            view.showMessage("Ошибка при выводе цен: " + e.getMessage());
        }
    }

    private void showRoomDetails() {
        try {
            int number = Integer.parseInt(view.readLine("Номер комнаты: "));
            String details = admin.getRoomDetails(number);
            view.showMessage(details);
        } catch (NumberFormatException e) {
            view.showMessage("Ошибка: введите корректный номер комнаты.");
        } catch (Exception e) {
            view.showMessage("Ошибка при выводе деталей номера: " + e.getMessage());
        }
    }

    private void listGuests() {
        try {
            admin.getGuestListSorted().forEach(view::showMessage);
        } catch (Exception e) {
            view.showMessage("Ошибка при выводе списка гостей: " + e.getMessage());
        }
    }

    private void addServiceToGuest() {
        try {
            String guestName = view.readLine("Имя гостя: ");
            String serviceName = view.readLine("Название услуги: ");
            Date date = sdf.parse(view.readLine("Дата оказания (yyyy-MM-dd): "));
            boolean success = admin.addServiceToGuest(guestName, serviceName, date);
            if (success) {
                view.showMessage("Услуга успешно добавлена.");
            } else {
                view.showMessage("Гость или услуга не найдены.");
            }
        } catch (java.text.ParseException e) {
            view.showMessage("Ошибка: неверный формат даты.");
        } catch (Exception e) {
            view.showMessage("Ошибка при добавлении услуги: " + e.getMessage());
        }
    }

    private void exportCsv() {
        try {
            view.showMessage("Что экспортировать? (room/guest/service/booking):");
            String type = view.readLine("Тип: ").toLowerCase();

            String path = view.readLine("Путь к файлу для экспорта (например, data.csv): ");
            boolean success = admin.exportToCsv(type, path);

            if (success) view.showMessage("Экспорт завершён.");
            else view.showMessage("Ошибка при экспорте.");
        } catch (Exception e) {
            view.showMessage("Ошибка при экспорте: " + e.getMessage());
        }
    }

    private void importCsv() {
        try {
            view.showMessage("Что импортировать? (room/guest/service/booking):");
            String type = view.readLine("Тип: ").toLowerCase();

            String path = view.readLine("Путь к CSV файлу (например, data.csv): ");
            boolean success = admin.importFromCsv(type, path);

            if (success) view.showMessage("Импорт завершён.");
            else view.showMessage("Ошибка при импорте.");
        } catch (Exception e) {
            view.showMessage("Ошибка при импорте: " + e.getMessage());
        }
    }
}
