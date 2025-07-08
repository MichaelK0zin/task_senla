package hotel3;

public class Main {
    public static void main(String[] args) {
        HotelAdmin admin = new HotelAdmin();

        admin.addRoom(101, 3000);
        admin.addRoom(102, 3500);

        admin.addService("WiFi", 100);
        admin.addService("Завтрак", 250);

        Guest ivanov = new Guest("Иван Иванов");
        admin.checkIn(101, ivanov);

        admin.setRoomStatus(102, RoomStatus.MAINTENANCE);
        admin.checkIn(102, new Guest("Петр Петров"));
        admin.checkOut(102);

        admin.setRoomPrice(101, 3200);
        admin.setServicePrice("Завтрак", 300);
        admin.checkOut(101);
    }
}
