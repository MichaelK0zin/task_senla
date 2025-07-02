package hotel;

public class Main {
    public static void main(String[] args) {
        HotelAdmin admin = new HotelAdmin();

        admin.addRoom(101, 3000);
        admin.addRoom(102, 3500);

        admin.addService("WiFi", 100);
        admin.addService("Завтрак", 250);

        admin.checkIn(101);
        admin.setRoomStatus(102, Room.Maintenace);
        admin.checkIn(102);
        admin.checkOut(102);
        admin.setRoomPrice(101, 3200);
        admin.setServicePrice("Завтрак", 300);
        admin.checkOut(101);
    }
}

