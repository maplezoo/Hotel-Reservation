package service;

import mode1.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReservationService {
    private static ReservationService reservationService;
    private static List<IRoom> roomList = new ArrayList<>();
    private static Collection<Reservation> setOfReservations = new HashSet<Reservation>();

    private ReservationService(){}

    public static ReservationService getReservationService() {
        if (reservationService == null) {
            System.out.println("RESERVATION SERVICE: NULL");
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom (String roomNumber, Double price, RoomType roomType){
        if (roomList.contains(getARoom(roomNumber))) {
            System.out.println("Room number" + roomNumber + "already exists. The room can not be created.");
        } else {
            Room room = new Room(roomNumber, price, roomType);
            roomList.add(room);
            System.out.println("The room was successfully added to our room list.");
        }
    }

    public IRoom getARoom (String roomId){
        for (IRoom room: roomList){
            if (room.getRoomNumber().equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Collection<IRoom> getAllRooms(){
        return roomList;
    }

    public void reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reserved = new Reservation(customer, room, checkInDate, checkOutDate);
        setOfReservations.add(reserved);
        System.out.println(reserved);
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new HashSet<>();

        for (IRoom room : roomList){
            if (!isRoomReserved(room, checkInDate, checkOutDate)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomReserved(IRoom room, Date checkin, Date checkout){
        //if no reservations have been made, then this room is empty
        if (setOfReservations.isEmpty()){
            return false;
        }

        for (Reservation reservation : setOfReservations){
            /**
             * if the current room has been reserved, we need to check if the new date range
             * overlaps with the reserved range
             * if yes -> then the room is not free
             * if no -> then the room is free
             */
            if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())){
                /**
                 * Lets say there is a reservation on room with
                 * The room if free with between checkin - checkout
                 * if checkout will occur before reservation checkin
                 * or if checkin will occur after reservation checkout
                 */
                boolean noOverLapp = (checkin.after(reservation.getCheckOutDate()) || checkout.before(reservation.getCheckInDate()));

                if (!noOverLapp){
                    return true;
                }
            }
        }
        return false;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer){
        Collection<Reservation> reservationRecord = new ArrayList<>();

        for (Reservation reservation: setOfReservations){
            if (reservation.getCustomer().getEmail().equals(customer.getEmail())){
                reservationRecord.add(reservation);
            }
        }
        return reservationRecord;
    }

    public void printAllReservations() {
        if (setOfReservations.isEmpty()){
            System.out.println("No Reservation in the database.");
        }
        for (Reservation reservation: setOfReservations){
            System.out.println(reservation);
        }
    }
}