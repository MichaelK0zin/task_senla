package hotel3;

public class Room {
    private final int number;
    private double price;
    private RoomStatus status;

    public Room(int number, double price) {
        this.number = number;
        this.price = price;
        this.status = RoomStatus.AVAILABLE;
    }

    private Guest currentGuest;

    public void checkIn(Guest guest) {
        if (status == RoomStatus.AVAILABLE) {
            status = RoomStatus.OCCUPIED;
            currentGuest = guest;
            System.out.println("Гость " + guest.getName() + " заселился в номер " + number);
        } else {
            System.out.println("Номер " + number + " недоступен к заселению");
        }
    }

    public void checkOut() {
        if (status == RoomStatus.OCCUPIED) {
            System.out.println("Гость " + currentGuest.getName() + " выселен из номера " + number);
            currentGuest = null;
            status = RoomStatus.AVAILABLE;
        } else {
            System.out.println("Номер " + number + " не занят");
        }
    }

    public Guest getCurrentGuest() {
        return currentGuest;
    }

    public void setStatus(RoomStatus newStatus) {
        this.status = newStatus;
        System.out.println("Статус номера " + number + " изменен на " + newStatus);
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
        System.out.println("Цена номера " + number + " изменена на " + newPrice);
    }

    public int getNumber() {
        return number;
    }

}
