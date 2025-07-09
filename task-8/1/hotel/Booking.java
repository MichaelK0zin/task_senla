package hotel;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Booking implements Serializable {
    private static int idCounter = 1;
    private int id;
    private final Guest guest;
    private final Room room;
    private final Date checkIn;
    private final Date checkOut;
    private final List<Service> services;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Booking(Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services) {
        if (guest == null) throw new IllegalArgumentException("Гость не может быть null");
        if (room == null) throw new IllegalArgumentException("Номер не может быть null");
        if (checkIn == null || checkOut == null) throw new IllegalArgumentException("Даты не могут быть null");
        if (checkOut.before(checkIn)) throw new IllegalArgumentException("Дата выезда не может быть раньше даты заезда");
        if (services == null) throw new IllegalArgumentException("Список услуг не может быть null");

        this.id = idCounter++;
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.services = new ArrayList<>(services);
    }

    public Booking(int id, Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services) {
        if (id <= 0) throw new IllegalArgumentException("ID должен быть положительным");
        if (guest == null) throw new IllegalArgumentException("Гость не может быть null");
        if (room == null) throw new IllegalArgumentException("Номер не может быть null");
        if (checkIn == null || checkOut == null) throw new IllegalArgumentException("Даты не могут быть null");
        if (checkOut.before(checkIn)) throw new IllegalArgumentException("Дата выезда не может быть раньше даты заезда");
        if (services == null) throw new IllegalArgumentException("Список услуг не может быть null");

        this.id = id;
        if (id >= idCounter) {
            idCounter = id + 1;
        }
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.services = new ArrayList<>(services);
    }

    public int getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public List<Service> getServices() {
        return services;
    }

    public double getTotalCost() {
        long duration = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
        if (duration <= 0) duration = 1;

        double serviceTotal = services.stream()
                .mapToDouble(Service::getPriceAtOrderTime)
                .sum();

        return duration * room.getPrice() + serviceTotal;
    }

    public String toCsv() {
        String serviceNames = String.join(";", services.stream().map(Service::getName).toList());
        return id + "," + guest.getId() + "," + room.getId() + ","  // исправлено: ID комнаты, не номер
                + sdf.format(checkIn) + "," + sdf.format(checkOut) + "," + serviceNames;
    }

    public static Booking fromCsv(String csvLine, Guest guest, Room room, List<Service> availableServices) {
        if (csvLine == null || guest == null || room == null || availableServices == null) {
            throw new IllegalArgumentException("Переданы недопустимые аргументы в fromCsv");
        }

        try {
            String[] parts = csvLine.split(",", 6);
            if (parts.length < 5) {
                throw new IllegalArgumentException("Недостаточно данных для создания Booking: " + csvLine);
            }

            int id = Integer.parseInt(parts[0]);
            Date checkIn = sdf.parse(parts[3]);
            Date checkOut = sdf.parse(parts[4]);

            List<Service> services = new ArrayList<>();
            if (parts.length == 6 && !parts[5].isEmpty()) {
                for (String serviceName : parts[5].split(";")) {
                    availableServices.stream()
                            .filter(s -> s.getName().equals(serviceName))
                            .findFirst()
                            .ifPresent(services::add);
                }
            }

            return new Booking(id, guest, room, checkIn, checkOut, services);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка преобразования числа: " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Ошибка разбора даты: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка при создании Booking: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking booking)) return false;
        return id == booking.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
