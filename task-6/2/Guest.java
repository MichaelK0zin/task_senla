package hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Guest {
    private static int idCounter = 1;
    private int id;
    private String name;
    private final List<Booking> history = new ArrayList<>();
    private final List<Service> orderedServices = new ArrayList<>();

    public Guest(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя гостя не может быть пустым");
        }
        this.id = idCounter++;
        this.name = name;
    }

    public Guest(int id, String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя гостя не может быть пустым");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным");
        }
        this.id = id;
        this.name = name;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public void addBooking(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Бронирование не может быть null");
        }
        history.add(booking);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя гостя не может быть пустым");
        }
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным");
        }
        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
    }

    public List<Booking> getHistory() {
        return history;
    }

    public void addService(Service service) {
        if (service == null) {
            throw new IllegalArgumentException("Услуга не может быть null");
        }
        orderedServices.add(service);
    }

    public List<Service> getOrderedServices() {
        return orderedServices;
    }

    public double getAdditionalServiceCost() {
        return orderedServices.stream()
                .mapToDouble(Service::getPriceAtOrderTime)
                .sum();
    }

    public String toCsv() {
        return id + "," + name;
    }

    public static Guest fromCsv(String line) {
        try {
            String[] parts = line.split(",", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException("Некорректный формат строки для Guest: " + line);
            }
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            return new Guest(id, name);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при разборе Guest из CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest guest)) return false;
        return id == guest.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateBooking(Booking booking) {
        if (booking == null) return;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i).getId() == booking.getId()) {
                history.set(i, booking);
                return;
            }
        }
        history.add(booking);
    }
}
