package api;

import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource hotelResource;
    public static CustomerService customerService = CustomerService.getCustomerService();
    public static ReservationService reservationService = ReservationService.getReservationService();

    public static HotelResource getHotelResource() {
        if (hotelResource == null) {
            System.out.println("Hotel Resource: NULL");
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email) {
       return customerService.getCustomer(email);
    }

    public void createACustomer (String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName,lastName);
    }

    public IRoom getRoom(String roomNumber){
        return reservationService.getARoom(roomNumber);
    }

    public void bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        return reservationService.getCustomerReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

}
