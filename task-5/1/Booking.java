package hotel;

import java.util.Date;
import java.util.List;

public class Booking {
    private final Guest guest;
    private final Room room;
    private final Date checkIn;
    private final Date checkOut;
    private final List<Service> services;

    public Booking(Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services) {
        this.guest = guest;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.services = services;
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
        double serviceTotal = 0;
        for (Service s : services) {
            serviceTotal += s.getPriceAtOrderTime();
        }
        return duration * room.getPrice() + serviceTotal;
    }
}
