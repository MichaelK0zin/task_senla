package hotel;

import java.util.Date;
import java.util.List;

public interface IHotelFactory {
    Room createRoom(int number, double price, int capacity, int stars);
    Guest createGuest(String name);
    Service createService(String name, double price);
    Booking createBooking(Guest guest, Room room, Date checkIn, Date checkOut, List<Service> services);
}

