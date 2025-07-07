package hotel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Booking {
    private static int idCounter = 1;
    private int id;
    private final Guest guest;
    private final Room room;
    private final Date checkIn;
    private final Date checkOut;
    private final List<Service> services;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Booking(Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services) {
        this.id = idCounter++;
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.services = new ArrayList<>(services);
    }

    public Booking(int id, Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services) {
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
        return id + "," + guest.getId() + "," + room.getNumber() + ","
                + sdf.format(checkIn) + "," + sdf.format(checkOut) + "," + serviceNames;
    }

    public static Booking fromCsv(String csvLine, Guest guest, Room room, List<Service> availableServices) throws ParseException {
        String[] parts = csvLine.split(",", 6);
        int id = Integer.parseInt(parts[0]);
        Date checkIn = sdf.parse(parts[3]);
        Date checkOut = sdf.parse(parts[4]);
        List<Service> services = new ArrayList<>();
        if (!parts[5].isEmpty()) {
            for (String serviceName : parts[5].split(";")) {
                availableServices.stream()
                        .filter(s -> s.getName().equals(serviceName))
                        .findFirst()
                        .ifPresent(services::add);
            }
        }
        return new Booking(id, guest, room, checkIn, checkOut, services);
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
