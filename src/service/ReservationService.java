package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {

    private static final ReservationService instance = new ReservationService();;
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final Collection<Reservation> reservations = new LinkedList<>();
    private static final int RECOMMENDED_ROOMS_DAYS = 7;

    private ReservationService() {}

    // Static method to get the singleton instance
    public static ReservationService getInstance() {
        return instance;
    }

    public void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (isRoomAvailable(room, checkInDate, checkOutDate)) {
            Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservations.add(reservation);
            System.out.println("Booked room successfully");
            return reservation;
        } else {
            throw new IllegalArgumentException("Room is not available! Please check again");
        }
    }


    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                if (checkInDate.before(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckInDate())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        return rooms.values().stream()
                .filter(room -> isRoomAvailable(room, checkInDate, checkOutDate))
                .collect(Collectors.toList());
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.stream()
                .filter(reservation -> reservation.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    private Collection<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);

    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return findAvailableRooms(checkInDate, checkOutDate);
    }

    public void getAllReservation() {
        Collection<Reservation> reservations = getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("Could not find reservations ! Please check again");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation + "\n");
            }
        }
    }

    public  Collection<IRoom> suggestAlternativeRoom(Date checkInDate, Date checkOutDate) {
        Date alternativeCheckIn = suggestAlternativeDates(checkInDate);
        Date alternativeCheckOut = suggestAlternativeDates(checkOutDate);

        return findAvailableRooms(alternativeCheckIn, alternativeCheckOut);
    }

    public Date suggestAlternativeDates(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, RECOMMENDED_ROOMS_DAYS); // add 7 days

        return calendar.getTime();
    }
}
