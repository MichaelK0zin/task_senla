package hotel;

public enum RoomStatus {
    AVAILABLE("Свободен"),
    OCCUPIED("Занят"),
    MAINTENANCE("Обслуживание");

    private final String label;

    RoomStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
