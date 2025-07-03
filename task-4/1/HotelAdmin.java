package hotel;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HotelAdmin {
    private final Map<Integer, Room> rooms = new HashMap<>();
    private final Map<String, Service> services = new HashMap<>();
    private final List<Guest> guests = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void addRoom(int number, double price, int capacity, int stars) {
        if (!rooms.containsKey(number)) {
            rooms.put(number, new Room(number, price, capacity, stars));
            System.out.println("Добавлен номер " + number);
        }
    }

    public void addService(String name, double price) {
        if (!services.containsKey(name)) {
            services.put(name, new Service(name, price));
            System.out.println("Добавлена услуга: " + name);
        }
    }

    public boolean addServiceToGuest(String guestName, String serviceName, Date date) {
        Guest guest = guests.stream()
                .filter(g -> g.getName().equals(guestName))
                .findFirst()
                .orElse(null);

        Service service = services.get(serviceName);

        if (guest != null && service != null) {
            guest.addService(Service.createOrdered(service.getName(), service.getCurrentPrice(), date));

            return true;
        } else {
            System.out.println("Гость или услуга не найдены.");
            return false;
        }
    }

    public void setRoomStatus(int roomNumber, String status) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.setStatus(status);
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setRoomPrice(int roomNumber, double price) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.setPrice(price);
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setServicePrice(String name, double price) {
        Service service = services.get(name);
        if (service != null) {
            service.setPrice(price);
        } else {
            System.out.println("Услуга не найдена");
        }
    }

    public void checkIn(int roomNumber, Date today) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.checkIn(today);
        } else {
            System.out.println("Номер " + roomNumber + " не найден.");
        }
    }

    public void checkOut(int roomNumber, Date today) {
        Room room = rooms.get(roomNumber);
        if (room != null) {
            room.checkOut(today);
        } else {
            System.out.println("Номер " + roomNumber + " не найден.");
        }
    }

    public void bookRoom(String guestName, int roomNumber, Date checkIn, Date checkOut, List<String> serviceNames) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            System.out.println("Номер не найден");
            return;
        }

        Guest guest = guests.stream()
                .filter(g -> g.getName().equalsIgnoreCase(guestName))
                .findFirst()
                .orElseGet(() -> {
                    Guest g = new Guest(guestName);
                    guests.add(g);
                    return g;
                });

        List<Service> bookedServices = serviceNames.stream()
                .map(services::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Booking booking = new Booking(guest, room, checkIn, checkOut, bookedServices);
        guest.addBooking(booking);
        room.addBooking(booking);

        for (Service service : bookedServices) {
            guest.addService(Service.createOrdered(service.getName(), service.getCurrentPrice(), checkIn));
        }

        System.out.println("Гость " + guestName + " забронировал номер " + roomNumber);
    }


    public List<Room> getSortedRooms(Comparator<Room> comparator) {
        return rooms.values().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Room> getAvailableRoomsOn(Date date, Comparator<Room> comparator) {
        return rooms.values().stream()
                .filter(r -> r.isAvailableOn(date))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public long countAvailableRooms() {
        return rooms.values().stream()
                .filter(r -> r.getStatus().equals(Room.Available))
                .count();
    }

    public long countGuests() {
        return guests.size();
    }

    public List<String> getGuestListSorted() {
        return guests.stream()
                .sorted(Comparator.comparing(Guest::getName))
                .map(g -> {
                    List<Booking> history = g.getHistory();
                    Booking last = history.get(history.size() - 1);
                    return g.getName() + " — номер " + last.getRoom().getNumber() + ", выезд: " + sdf.format(last.getCheckOut());
                })
                .collect(Collectors.toList());
    }

    public double calculateTotalPayment(String guestName) {
        return guests.stream()
                .filter(g -> g.getName().equalsIgnoreCase(guestName))
                .flatMap(g -> g.getHistory().stream())
                .mapToDouble(Booking::getTotalCost)
                .sum();
    }

    public List<String> getLastBookings(int count) {
        return guests.stream()
                .flatMap(g -> g.getHistory().stream())
                .sorted(Comparator.comparing(Booking::getCheckOut).reversed())
                .limit(count)
                .map(b -> b.getGuest().getName() + " — номер " + b.getRoom().getNumber()
                        + ", с " + sdf.format(b.getCheckIn()) + " по " + sdf.format(b.getCheckOut()))
                .collect(Collectors.toList());
    }


    public List<String> getGuestServices(String guestName) {
        return guests.stream()
                .filter(g -> g.getName().equals(guestName))
                .findFirst()
                .map(g -> g.getOrderedServices().stream()
                        .map(Service::toString)
                        .toList()
                )
                .orElse(List.of("Гость не найден"));
    }


    public List<String> getPrices() {
        List<String> result = new ArrayList<>();
        result.addAll(rooms.values().stream()
                .sorted(Comparator.comparing(Room::getPrice))
                .map(r -> "Номер " + r.getNumber() + ": " + r.getPrice())
                .collect(Collectors.toList()));

        result.addAll(services.values().stream()
                .sorted(Comparator.comparing(Service::getCurrentPrice))
                .map(s -> "Услуга " + s.getName() + ": " + s.getCurrentPrice())
                .collect(Collectors.toList()));

        return result;
    }

    public String getRoomDetails(int roomNumber) {
        Room room = rooms.get(roomNumber);
        return room != null ? room.getDetails() : "Номер не найден";
    }
}
