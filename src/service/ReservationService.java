package service;

import mode1.*;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService;
    private static List<IRoom> roomList = new ArrayList<>();
    private static Set<Reservation> setOfReservations = new HashSet<Reservation>();

    public static ReservationService getReservationService() {
        if (reservationService == null) {
            System.out.println("RESERVATION SERVICE: NULL");
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public static void addRoom (String roomNumber, Double price, RoomType roomType){
        if (roomList.contains(getARoom(roomNumber))) {
            System.out.println("This room number already exists. The room can not be created.");
        } else {
            Room room = new Room(roomNumber, price, roomType);
            roomList.add(room);
            System.out.println("The room was successfully added to our room list.");
        }

    }

    public static IRoom getARoom (String roomId){
        for (IRoom room: roomList){
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }


    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reserved = new Reservation(customer, room, checkInDate, checkOutDate);
        setOfReservations.add(reserved);
        return reserved;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        Collection<IRoom> availableRooms = new LinkedHashSet<>();

        if (setOfReservations.size() == 0) {
            availableRooms = roomList;
        } else {
            for (IRoom room: roomList){
                for (Reservation reserved: setOfReservations){
                    if (((room.getRoomNumber().equals(reserved.getRoom().getRoomNumber()))) &&
                    (checkInDate.after(reserved.getCheckOutDate())) &&
                            (checkOutDate.before(reserved.getCheckInDate()))){
                        availableRooms.add(room);
                    } else{
                        if (room.getRoomNumber().equals(reserved.getRoom().getRoomNumber())) {
                            availableRooms.remove(room);
                    }
                }
            }
        }
    }
        System.out.println(availableRooms);
        return availableRooms;
    }

    public static Collection<Reservation> getCustomerReservation(Customer customer){
        Collection<Reservation> reservationRecord = new ArrayList<>();

        for (Reservation reservation: setOfReservations){
            if (reservation.getCustomer().getEmail().equals(customer.getEmail())){
                reservationRecord.add(reservation);
            }
        }
        return reservationRecord;
    }

    public static void printAllReservations() {
        for (Reservation reservation: setOfReservations){
            System.out.println(reservation);
        }
    }

}