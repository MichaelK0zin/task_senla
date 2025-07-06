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
                case 0 -> running = false;
                default -> view.showMessage("Неверный выбор.");
            }
        }
    }

    private void addRoom() {
        int number = Integer.parseInt(view.readLine("Номер: "));
        double price = Double.parseDouble(view.readLine("Цена: "));
        int capacity = Integer.parseInt(view.readLine("Вместимость: "));
        int stars = Integer.parseInt(view.readLine("Звезды: "));
        admin.addRoom(number, price, capacity, stars);
    }

    private void addService() {
        String name = view.readLine("Название услуги: ");
        double price = Double.parseDouble(view.readLine("Цена: "));
        admin.addService(name, price);
    }

    private void changeRoomPrice() {
        int number = Integer.parseInt(view.readLine("Введите номер комнаты: "));
        double newPrice = Double.parseDouble(view.readLine("Введите новую цену: "));
        admin.setRoomPrice(number, newPrice);
    }

    private void changeRoomStatus() {
        int number = Integer.parseInt(view.readLine("Введите номер комнаты: "));
        view.showMessage("Выберите статус:");
        for (RoomStatus status : RoomStatus.values()) {
            view.showMessage("- " + status);
        }
        try {
            String input = view.readLine("Новый статус (например, AVAILABLE): ").toUpperCase();
            RoomStatus status = RoomStatus.valueOf(input);
            admin.setRoomStatus(number, status);
        } catch (IllegalArgumentException e) {
            view.showMessage("Неверный статус.");
        }
    }

    private void changeServicePrice() {
        String name = view.readLine("Введите название услуги: ");
        double newPrice = Double.parseDouble(view.readLine("Новая цена: "));
        admin.setServicePrice(name, newPrice);
    }

    private void bookRoom() {
        try {
            String guest = view.readLine("Имя гостя: ");
            int room = Integer.parseInt(view.readLine("Номер комнаты: "));
            Date checkIn = sdf.parse(view.readLine("Дата заезда (yyyy-MM-dd): "));
            Date checkOut = sdf.parse(view.readLine("Дата выезда (yyyy-MM-dd): "));
            String[] serviceNames = view.readLine("Услуги через запятую: ").split(",");
            admin.bookRoom(guest, room, checkIn, checkOut, Arrays.stream(serviceNames).map(String::trim).toList());
        } catch (Exception e) {
            view.showMessage("Ошибка при бронировании: " + e.getMessage());
        }
    }

    private void checkIn() {
        int room = Integer.parseInt(view.readLine("Номер комнаты: "));
        try {
            Date today = sdf.parse(view.readLine("Дата заезда (yyyy-MM-dd): "));
            admin.checkIn(room, today);
        } catch (Exception e) {
            view.showMessage("Ошибка: " + e.getMessage());
        }
    }

    private void checkOut() {
        int room = Integer.parseInt(view.readLine("Номер комнаты: "));
        try {
            Date today = sdf.parse(view.readLine("Дата выезда (yyyy-MM-dd): "));
            admin.checkOut(room, today);
        } catch (Exception e) {
            view.showMessage("Ошибка: " + e.getMessage());
        }
    }

    private void listRooms() {
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
    }

    private void listAvailableRooms() {
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
    }

    private void countGuestsAndRooms() {
        view.showMessage("Всего гостей: " + admin.countGuests());
        view.showMessage("Свободных номеров: " + admin.countAvailableRooms());
    }

    private void availableOnDate() {
        try {
            Date date = sdf.parse(view.readLine("Дата (yyyy-MM-dd): "));
            admin.getAvailableRoomsOn(date, Comparator.comparing(Room::getPrice))
                    .forEach(r -> view.showMessage(r.getDetails()));
        } catch (Exception e) {
            view.showMessage("Неверная дата.");
        }
    }

    private void calculateGuestPayment() {
        String guest = view.readLine("Имя гостя: ");
        double total = admin.calculateTotalPayment(guest);
        view.showMessage("Общая сумма оплаты: " + total);
    }

    private void showLastBookings() {
        admin.getLastBookings(3).forEach(view::showMessage);
    }

    private void showGuestServices() {
        String guest = view.readLine("Имя гостя: ");
        admin.getGuestServices(guest).forEach(view::showMessage);
    }

    private void showPrices() {
        admin.getPrices().forEach(view::showMessage);
    }

    private void showRoomDetails() {
        int number = Integer.parseInt(view.readLine("Номер комнаты: "));
        view.showMessage(admin.getRoomDetails(number));
    }

    private void listGuests() {
        admin.getGuestListSorted().forEach(view::showMessage);
    }

    private void addServiceToGuest() {
        String guestName = view.readLine("Имя гостя: ");
        String serviceName = view.readLine("Название услуги: ");
        try {
            Date date = sdf.parse(view.readLine("Дата оказания (yyyy-MM-dd): "));
            boolean success = admin.addServiceToGuest(guestName, serviceName, date);
            if (success) {
                view.showMessage("Услуга успешно добавлена.");
            } else {
                view.showMessage("Ошибка: гость или услуга не найдены.");
            }
        } catch (Exception e) {
            view.showMessage("Ошибка при вводе даты: " + e.getMessage());
        }
    }

}
