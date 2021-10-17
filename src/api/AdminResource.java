package api;

import mode1.Customer;
import mode1.IRoom;
import mode1.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AdminResource {
    private static AdminResource adminResource;
    public static CustomerService customerService = CustomerService.getCustomerService();
    public static ReservationService reservationService = ReservationService.getReservationService();

    public static AdminResource getReservationService() {
        if (adminResource == null) {
            System.out.println("Admin Resource: NULL");
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){
        return CustomerService.getCustomer(email);
    }

    public void addRoom(List<IRoom> rooms){
        for(IRoom r: rooms){
            reservationService.addRoom(r.getRoomNumber(), r.getRoomPrice(), r.getRoomType());
        }
    }

    public Collection<IRoom> getAllRooms(){
        return reservationService.getAllRooms();
    }

    public Map<String, Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservation(){
        reservationService.printAllReservations();
    }
}
