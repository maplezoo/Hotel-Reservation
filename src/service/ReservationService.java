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

    /**The customer can not reserve 2 room in the same range of time*/
    //private boolean doubleReserveCustomer(Customer customer, Date checkInDate, Date checkOutDate){
    //    boolean result = false;
    //    for (Reservation r: getCustomerReservation(customer)){
    //        if (r.getCheckInDate().before(checkOutDate) && r.getCheckOutDate().after(checkInDate)){
    //            result = true;
    //            break;
    //        }
    //    }
    //    return result;
    //}


    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new HashSet<>();
        availableRooms.clear();

        if (setOfReservations.size() == 0) {
            availableRooms = roomList;
        } else {
            for (Reservation reserved : setOfReservations) {
                for (IRoom room : roomList) {
                    boolean sameNumber = room.getRoomNumber().equals(reserved.getRoom().getRoomNumber());
                    boolean noConflictDate = !((checkInDate.before(reserved.getCheckOutDate()))
                            && (checkOutDate.after(reserved.getCheckInDate())));

                    if ((sameNumber && noConflictDate) || (!(sameNumber))) {
                        availableRooms.add(room);
                    }
                }
            }
        }
        return availableRooms;
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