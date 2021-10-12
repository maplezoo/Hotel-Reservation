package api;

import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

import static service.CustomerService.getCustomer;

public class HotelResource {
    private static HotelResource hotelResource;


    public static HotelResource getHotelResource() {
        if (hotelResource == null) {
            System.out.println("Hotel Resource: NULL");
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email) {
       return getCustomer(email);
    }

    public void createACustomer (String email, String firstName, String lastName){
        CustomerService.addCustomer(email, firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return ReservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        return ReservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservation(String customerEmail){
        return ReservationService.getCustomerReservation(getCustomer(customerEmail))
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return ReservationService.findRooms(checkInDate, checkOutDate);
    }

}
