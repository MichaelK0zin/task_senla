package hotel;

import java.util.*;

public class HotelAdmin {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<String, Service> services = new HashMap<>();

    public void addRoom(int number, double price) {
        if (!rooms.containsKey(number)) {
            rooms.put(number, new Room(number, price));
            System.out.println("Добавлен номер " + number);
        }
    }

    public void addService(String name, double price) {
        if (!services.containsKey(name)) {
            services.put(name, new Service(name, price));
            System.out.println("Добавлена услуга: " + name);
        }
    }

    public void checkIn(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.checkIn();
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void checkOut(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.checkOut();
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setRoomStatus(int roomNumber, String status) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.setStatus(status);
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setRoomPrice(int roomNumber, double price) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.setPrice(price);
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setServicePrice(String name, double price) {
        Service service = services.get(name);
        if (service != null) {
            service.setPrice(price);
        } else {
            System.out.println("Услуга не найдена");
        }
    }
}

