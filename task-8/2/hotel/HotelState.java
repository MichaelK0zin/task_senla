package hotel;

import java.io.Serializable;
import java.util.*;

public class HotelState implements Serializable {
    public Map<Integer, Room> roomsByNumber;
    public Map<Integer, Room> roomsById;
    public Map<Integer, Service> services;
    public List<Guest> guests;
    public Map<Integer, Booking> bookingsById;

    public HotelState(HotelAdmin admin) {
        this.roomsByNumber = admin.getRoomsByNumber();
        this.roomsById = admin.getRoomsById();
        this.services = admin.getServices();
        this.guests = admin.getGuests();
        this.bookingsById = admin.getBookingsById();
    }
}
