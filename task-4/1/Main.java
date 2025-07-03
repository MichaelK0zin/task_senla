package hotel;

import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        HotelAdmin admin = new HotelAdmin();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        admin.addRoom(101, 3000, 2, 5);
        admin.addRoom(102, 5000, 3, 4);
        admin.addRoom(103, 2000, 1, 2);

        admin.addService("Завтрак", 500);
        admin.addService("Уборка", 300);
        admin.addService("Трансфер", 1000);

        admin.setRoomStatus(101, Room.Maintenace);
        admin.setRoomStatus(101, Room.Available);

        admin.setServicePrice("Завтрак", 300);

        Date today = sdf.parse("2025-07-03");
        Date tomorrow = sdf.parse("2025-07-04");
        Date nextWeek = sdf.parse("2025-07-10");

        System.out.println("\nСвободные номера на " + sdf.format(nextWeek) + ":");
        admin.getAvailableRoomsOn(nextWeek, Comparator.comparing(Room::getNumber))
                .forEach(r -> System.out.println(r.getDetails()));

        admin.bookRoom("Иванов", 101, today, tomorrow, List.of("Завтрак", "Уборка"));
        admin.bookRoom("Петров", 102, today, nextWeek, List.of("Трансфер"));
        admin.bookRoom("Сидоров", 103, tomorrow, nextWeek, List.of());

        admin.checkIn(101, today);
        admin.checkIn(102, today);

        System.out.println("\nНомера по цене:");
        admin.getSortedRooms(Comparator.comparing(Room::getPrice))
                .forEach(r -> System.out.println(r.getDetails()));

        System.out.println("\nНомера по звездам:");
        admin.getSortedRooms(Comparator.comparing(Room::getStars))
                .forEach(r -> System.out.println(r.getDetails()));

        System.out.println("\nСвободных номеров сейчас: " + admin.countAvailableRooms());
        System.out.println("Число постояльцев сейчас: " + admin.countGuests());

        System.out.println("\nСписок постояльцев:");
        admin.getGuestListSorted().forEach(System.out::println);

        double total = admin.calculateTotalPayment("Сидоров");
        System.out.println("\nСидоров должен оплатить: " + total + " руб.");

        System.out.println("\nПоследние 3 постояльца:");
        List<String> recent = admin.getLastBookings(3);
        recent.forEach(System.out::println);

        admin.addServiceToGuest("Иванов", "Завтрак", sdf.parse("2025-07-05"));
        System.out.println("\nУслуги, заказанные Ивановым:");
        admin.getGuestServices("Иванов").forEach(System.out::println);

        System.out.println("\nЦены на номера и услуги:");
        admin.getPrices().forEach(System.out::println);

        System.out.println("\nДетали по номеру 102:");
        System.out.println(admin.getRoomDetails(102) + "\n");

        admin.checkOut(101, tomorrow);
    }
}
