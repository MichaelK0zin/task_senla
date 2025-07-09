package hotel;

import java.util.Date;
import java.util.List;

public class HotelFactory implements IHotelFactory {

    public HotelFactory() {

    }

    @Override
    public Room createRoom(int number, double price, int capacity, int stars) {
        return new Room(number, price, capacity, stars);
    }

    @Override
    public Guest createGuest(String name) {
        return new Guest(name);
    }

    @Override
    public Service createService(String name, double price) {
        return new Service(name, price);
    }

    @Override
    public Booking createBooking(Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services) {
        return new Booking(guest, room, checkIn, checkOut, services);
    }
}
