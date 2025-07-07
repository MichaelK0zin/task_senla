package hotel;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class HotelAdmin {
    private static HotelAdmin instance;

    private final Map<Integer, Room> roomsByNumber = new HashMap<>();
    private final Map<Integer, Room> roomsById = new HashMap<>();
    private final Map<Integer, Service> services = new HashMap<>();
    private final List<Guest> guests = new ArrayList<>();
    private final Map<Integer, Booking> bookingsById = new HashMap<>();

    private final HotelFactory factory;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private HotelAdmin(HotelFactory factory) {
        this.factory = factory;
    }

    public static HotelAdmin getInstance(HotelFactory factory) {
        if (instance == null) {
            instance = new HotelAdmin(factory);
        }
        return instance;
    }

    public void addRoom(int number, double price, int capacity, int stars) {
        if (!roomsByNumber.containsKey(number)) {
            Room room = factory.createRoom(number, price, capacity, stars);
            roomsByNumber.put(number, room);
            roomsById.put(room.getId(), room);
            System.out.println("Добавлен номер " + number);
        }
    }

    public void addService(String name, double price) {
        boolean exists = services.values().stream()
                .anyMatch(s -> s.getName().equals(name));
        if (!exists) {
            Service newService = factory.createService(name, price);
            services.put(newService.getId(), newService);
            System.out.println("Добавлена услуга: " + name);
        }
    }

    public boolean addServiceToGuest(String guestName, String serviceName, Date date) {
        Guest guest = guests.stream()
                .filter(g -> g.getName().equals(guestName))
                .findFirst()
                .orElse(null);

        Service service = services.values().stream()
                .filter(s -> s.getName().equals(serviceName))
                .findFirst()
                .orElse(null);

        if (guest != null && service != null) {
            guest.addService(Service.createOrdered(service.getName(), service.getCurrentPrice(), date));
            return true;
        } else {
            System.out.println("Гость или услуга не найдены.");
            return false;
        }
    }

    public void setRoomStatus(int roomNumber, RoomStatus status) {
        Room room = roomsByNumber.get(roomNumber);
        if (room != null) {
            room.setStatus(status);
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setRoomPrice(int roomNumber, double price) {
        Room room = roomsByNumber.get(roomNumber);
        if (room != null) {
            room.setPrice(price);
        } else {
            System.out.println("Номер не найден");
        }
    }

    public void setServicePrice(String name, double price) {
        Service service = services.values().stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (service != null) {
            service.setPrice(price);
        } else {
            System.out.println("Услуга не найдена");
        }
    }

    public void checkIn(int roomNumber, Date today) {
        Room room = roomsByNumber.get(roomNumber);
        if (room != null) {
            boolean result = room.checkIn(today);
            if (result) System.out.println("Заселение выполнено.");
            else System.out.println("На текущую дату нет бронирования для номера " + roomNumber);
        } else {
            System.out.println("Номер " + roomNumber + " не найден.");
        }
    }

    public void checkOut(int roomNumber, Date today) {
        Room room = roomsByNumber.get(roomNumber);
        if (room != null) {
            boolean result = room.checkOut(today);
            if (result) System.out.println("Выселение выполнено.");
            else System.out.println("На текущую дату не найдено бронирования для выселения.");
        } else {
            System.out.println("Номер " + roomNumber + " не найден.");
        }
    }

    public void bookRoom(String guestName, int roomNumber, Date checkIn, Date checkOut, List<String> serviceNames) {
        Room room = roomsByNumber.get(roomNumber);
        if (room == null) {
            System.out.println("Номер не найден");
            return;
        }

        Guest guest = guests.stream()
                .filter(g -> g.getName().equalsIgnoreCase(guestName))
                .findFirst()
                .orElseGet(() -> {
                    Guest g = factory.createGuest(guestName);
                    guests.add(g);
                    return g;
                });

        List<Service> bookedServices = serviceNames.stream()
                .map(name -> services.values().stream()
                        .filter(s -> s.getName().equals(name))
                        .findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Booking booking = factory.createBooking(guest, room, checkIn, checkOut, bookedServices);
        guest.addBooking(booking);
        room.addBooking(booking);

        for (Service service : bookedServices) {
            guest.addService(Service.createOrdered(service.getName(), service.getCurrentPrice(), checkIn));
        }

        System.out.println("Гость " + guestName + " забронировал номер " + roomNumber);
    }

    public List<Room> getSortedRooms(Comparator<Room> comparator) {
        return roomsByNumber.values().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Room> getAvailableRoomsOn(Date date, Comparator<Room> comparator) {
        return roomsByNumber.values().stream()
                .filter(r -> r.isAvailableOn(date))
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public long countAvailableRooms() {
        return roomsByNumber.values().stream()
                .filter(r -> r.getStatus() == RoomStatus.AVAILABLE)
                .count();
    }

    public long countGuests() {
        return guests.size();
    }

    public List<String> getGuestListSorted() {
        return guests.stream()
                .filter(g -> !g.getHistory().isEmpty())
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
                .mapToDouble(g ->
                        g.getHistory().stream().mapToDouble(Booking::getTotalCost).sum() +
                                g.getOrderedServices().stream().mapToDouble(Service::getPriceAtOrderTime).sum()
                )
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
                        .toList())
                .orElse(List.of("Гость не найден"));
    }

    public List<String> getPrices() {
        List<String> result = new ArrayList<>();
        result.addAll(roomsByNumber.values().stream()
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
        Room room = roomsByNumber.get(roomNumber);
        return room != null ? room.getDetails() : "Номер не найден";
    }

    public boolean exportToCsv(String type, String path) {
        try (PrintWriter writer = new PrintWriter(path)) {
            switch (type) {
                case "room" -> roomsById.values().forEach(r ->
                        writer.println(r.getId() + "," + r.getNumber() + "," + r.getPrice() + "," +
                                r.getCapacity() + "," + r.getStars() + "," + r.getStatus().name())
                );
                case "guest" -> guests.forEach(g ->
                        writer.println(g.getId() + "," + g.getName())
                );
                case "service" -> services.values().forEach(s ->
                        writer.println(s.getId() + "," + s.getName() + "," + s.getCurrentPrice())
                );
                case "booking" -> bookingsById.values().forEach(b -> {
                    String servicesCsv = b.getServices().stream()
                            .map(s -> s.getId() + ":" + s.getName() + ":" + s.getCurrentPrice())
                            .collect(Collectors.joining(";"));
                    writer.println(
                            b.getId() + "," +
                                    b.getGuest().getId() + "," +
                                    b.getRoom().getId() + "," +
                                    sdf.format(b.getCheckIn()) + "," +
                                    sdf.format(b.getCheckOut()) + "," +
                                    servicesCsv
                    );
                });
                default -> {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка экспорта: " + e.getMessage());
            return false;
        }
    }

    public boolean importFromCsv(String type, String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (type) {
                    case "room" -> {
                        Room room = Room.fromCsv(line);
                        Room existingRoom = roomsById.get(room.getId());
                        if (existingRoom != null) {
                            roomsByNumber.remove(existingRoom.getNumber());
                            roomsByNumber.put(room.getNumber(), room);
                            roomsById.put(room.getId(), room);
                            System.out.println("Обновлен номер " + room.getNumber() + " с id " + room.getId());
                        } else {
                            roomsByNumber.put(room.getNumber(), room);
                            roomsById.put(room.getId(), room);
                            System.out.println("Добавлен номер " + room.getNumber() + " с id " + room.getId());
                        }
                    }
                    case "guest" -> {
                        Guest importedGuest = Guest.fromCsv(line);
                        Guest existingGuest = guests.stream()
                                .filter(g -> g.getId() == importedGuest.getId())
                                .findFirst()
                                .orElse(null);
                        if (existingGuest != null) {
                            int index = guests.indexOf(existingGuest);
                            guests.set(index, importedGuest);
                            System.out.println("Обновлен гость с id " + importedGuest.getId());
                        } else {
                            guests.add(importedGuest);
                            System.out.println("Добавлен гость с id " + importedGuest.getId());
                        }
                    }
                    case "service" -> {
                        Service importedService = Service.fromCsv(line);
                        if (services.containsKey(importedService.getId())) {
                            services.put(importedService.getId(), importedService);
                            System.out.println("Обновлена услуга с id " + importedService.getId());
                        } else {
                            services.put(importedService.getId(), importedService);
                            System.out.println("Добавлена услуга с id " + importedService.getId());
                        }
                    }
                    case "booking" -> {
                        String[] parts = line.split(",", 6);
                        int bookingId = Integer.parseInt(parts[0]);
                        int guestId = Integer.parseInt(parts[1]);
                        int roomId = Integer.parseInt(parts[2]);
                        Date checkIn = sdf.parse(parts[3]);
                        Date checkOut = sdf.parse(parts[4]);

                        Guest guest = guests.stream()
                                .filter(g -> g.getId() == guestId)
                                .findFirst()
                                .orElse(null);
                        if (guest == null) {
                            System.out.println("Гость с id " + guestId + " не найден для бронирования.");
                            continue;
                        }

                        Room room = roomsById.get(roomId);
                        if (room == null) {
                            System.out.println("Номер с id " + roomId + " не найден для бронирования.");
                            continue;
                        }

                        List<Service> bookingServices = new ArrayList<>();
                        if (parts.length == 6 && !parts[5].isEmpty()) {
                            String[] serviceNames = parts[5].split(";");
                            for (String serviceName : serviceNames) {
                                services.values().stream()
                                        .filter(s -> s.getName().equals(serviceName))
                                        .findFirst()
                                        .ifPresent(bookingServices::add);
                            }
                        }

                        Booking booking = new Booking(bookingId, guest, room, checkIn, checkOut, bookingServices);

                        Booking existingBooking = bookingsById.get(bookingId);
                        if (existingBooking != null) {
                            guest.updateBooking(booking);
                            room.updateBooking(booking);
                            bookingsById.put(bookingId, booking);
                            System.out.println("Обновлено бронирование с id " + bookingId);
                        } else {
                            guest.addBooking(booking);
                            room.addBooking(booking);
                            bookingsById.put(bookingId, booking);
                            System.out.println("Добавлено бронирование с id " + bookingId);
                        }
                    }
                    default -> {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка импорта: " + e.getMessage());
            return false;
        }
    }
}
