package hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Guest {
    private final String name;
    private final List<Booking> history = new ArrayList<>();
    private final List<Service> orderedServices = new ArrayList<>();

    public Guest(String name) {
        this.name = name;
    }

    public void addBooking(Booking booking) {
        history.add(booking);
    }

    public String getName() {
        return name;
    }

    public List<Booking> getHistory() {
        return history;
    }

    public void addService(Service service) {
        orderedServices.add(service);
    }

    public List<Service> getOrderedServices() {
        return orderedServices;
    }
}
