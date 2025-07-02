package hotel;

public class Room {
    public static final String Available = "Свободен";
    public static final String Occupied = "Занят";
    public static final String Maintenace = "Обслуживание";

    private final int number;
    private double price;
    private String status;

    public Room(int number, double price) {
        this.number = number;
        this.price = price;
        this.status = Available;
    }

    public void checkIn() {
        if (status.equals(Available)) {
            status = Occupied;
            System.out.println("Гость заселился в номер " + number);
        } else {
            System.out.println("Номер " + number + " недоступен к заселению");
        }
    }

    public void checkOut() {
        if (status.equals(Occupied)) {
            status = Available;
            System.out.println("Гость выселен из номера " + number);
        } else {
            System.out.println("Номер " + number + " не занят");
        }
    }

    public void setStatus(String newStatus) {
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
