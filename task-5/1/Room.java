package hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Room {
    private final int number;
    private double price;
    private RoomStatus status;
    private int capacity;
    private int stars;
    private final List<Booking> bookings = new ArrayList<>();

    public Room(int number, double price, int capacity, int stars) {
        this.number = number;
        this.price = price;
        this.status = RoomStatus.AVAILABLE;
        this.capacity = capacity;
        this.stars = stars;
    }

    public boolean checkIn(Date today) {
        for (Booking b : bookings) {
            if (b.getCheckIn().equals(today)) {
                status = RoomStatus.OCCUPIED;
                return true;
            }
        }
        return false;
    }

    public boolean checkOut(Date today) {
        for (Booking b : bookings) {
            if (b.getCheckOut().equals(today)) {
                status = RoomStatus.AVAILABLE;
                return true;
            }
        }
        return false;
    }

    public void setStatus(RoomStatus newStatus) {
        this.status = newStatus;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
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

    public String getDetails() {
        return "Номер " + number + ": цена " + price + ", вместимость " + capacity
                + ", звёзды " + stars + ", статус " + status.getLabel();
    }

    public boolean isAvailableOn(Date date) {
        for (Booking b : bookings) {
            if (!b.getCheckOut().before(date) && !b.getCheckIn().after(date)) {
                return false;
            }
        }
        return status == RoomStatus.AVAILABLE;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
