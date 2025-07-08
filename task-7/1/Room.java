package hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Room {
    private static int idCounter = 1;
    private int id;
    private final int number;
    private double price;
    private RoomStatus status;
    private int capacity;
    private int stars;
    private final List<Booking> bookings = new ArrayList<>();

    public Room(int number, double price, int capacity, int stars) {
        if (number <= 0) throw new IllegalArgumentException("Номер комнаты должен быть положительным.");
        if (price < 0) throw new IllegalArgumentException("Цена не может быть отрицательной.");
        if (capacity <= 0) throw new IllegalArgumentException("Вместимость должна быть положительной.");
        if (stars < 0 || stars > 5) throw new IllegalArgumentException("Количество звёзд должно быть от 0 до 5.");

        this.id = idCounter++;
        this.number = number;
        this.price = price;
        this.status = RoomStatus.AVAILABLE;
        this.capacity = capacity;
        this.stars = stars;
    }

    public Room(int id, int number, double price, int capacity, int stars) {
        this(number, price, capacity, stars);
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public boolean checkIn(Date today) {
        if (today == null) throw new IllegalArgumentException("Дата заселения не может быть null.");
        for (Booking b : bookings) {
            if (b.getCheckIn().equals(today)) {
                status = RoomStatus.OCCUPIED;
                return true;
            }
        }
        return false;
    }

    public boolean checkOut(Date today) {
        if (today == null) throw new IllegalArgumentException("Дата выселения не может быть null.");
        for (Booking b : bookings) {
            if (b.getCheckOut().equals(today)) {
                status = RoomStatus.AVAILABLE;
                return true;
            }
        }
        return false;
    }

    public void setStatus(RoomStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("Статус комнаты не может быть null.");
        }
        this.status = newStatus;
    }

    public void setPrice(double newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной.");
        }
        this.price = newPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID должен быть положительным.");
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStars() {
        return stars;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public String getDetails() {
        return "Номер " + number + ": цена " + price + ", вместимость " + capacity
                + ", звёзды " + stars + ", статус " + status.getLabel();
    }

    public boolean isAvailableOn(Date date) {
        if (date == null) throw new IllegalArgumentException("Дата проверки доступности не может быть null.");
        for (Booking b : bookings) {
            if (!b.getCheckOut().before(date) && !b.getCheckIn().after(date)) {
                return false;
            }
        }
        return status == RoomStatus.AVAILABLE;
    }

    public void addBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Бронирование не может быть null.");
        bookings.add(booking);
    }

    public String toCsv() {
        return String.join(",",
                String.valueOf(id),
                String.valueOf(number),
                String.valueOf(price),
                String.valueOf(capacity),
                String.valueOf(stars),
                status.name());
    }

    public static Room fromCsv(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 6) {
                throw new IllegalArgumentException("Некорректный формат строки для Room: " + line);
            }

            int id = Integer.parseInt(parts[0]);
            int number = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            int capacity = Integer.parseInt(parts[3]);
            int stars = Integer.parseInt(parts[4]);
            RoomStatus status = RoomStatus.valueOf(parts[5]);

            Room room = new Room(id, number, price, capacity, stars);
            room.setStatus(status);
            return room;

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при парсинге Room из CSV: " + e.getMessage(), e);
        }
    }


    public void updateBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Бронирование не может быть null.");
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId() == booking.getId()) {
                bookings.set(i, booking);
                return;
            }
        }
        bookings.add(booking);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return id == room.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
