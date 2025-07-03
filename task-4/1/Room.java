package hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
    public static final String Available = "Свободен";
    public static final String Occupied = "Занят";
    public static final String Maintenace = "Обслуживание";

    private final int number;
    private double price;
    private String status;
    private int capacity;
    private int stars;
    private final List<Booking> bookings = new ArrayList<>();


    public Room(int number, double price, int capacity, int stars) {
        this.number = number;
        this.price = price;
        this.status = Available;
        this.capacity = capacity;
        this.stars = stars;
    }

    public void checkIn(Date today) {
        for (Booking b : bookings) {
            if (b.getCheckIn().equals(today)) {
                status = Occupied;
                System.out.println("Заселение в номер " + number + " выполнено.");
                return;
            }
        }
        System.out.println("На текущую дату нет бронирования для номера " + number);
    }

    public void checkOut(Date today) {
        for (Booking b : bookings) {
            if (b.getCheckOut().equals(today)) {
                status = Available;
                System.out.println("Выселение из номера " + number + " выполнено.");
                return;
            }
        }
        System.out.println("На текущую дату не найдено бронирования для выселения.");
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
        System.out.println("Статус номера " + number + " изменен на " + newStatus);
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
        System.out.println("Цена номера " + number + " изменена на " + newPrice);
    }

    public int getNumber() {
        return number;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStars() {
        return stars;
    }

    public String getDetails() {
        return "Номер " + number + ": цена " + price + ", вместимость " + capacity + ", звёзды " + stars + ", статус " + status;
    }

    public boolean isAvailableOn(Date date) {
        for (Booking b : bookings) {
            if (!b.getCheckOut().before(date) && !b.getCheckIn().after(date)) {
                return false;
            }
        }
        return status.equals(Available);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        this.status = Occupied;
    }
}
