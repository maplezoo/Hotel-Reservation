package mode1;

import java.util.Date;

public class Reservation {
    Customer customer;
    IRoom room;
    Date checkInDate;
    Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkIn, Date checkOut){
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkIn;
        this.checkOutDate = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    @Override
    public String toString(){
        return customer.lastName + customer.firstName
                + "\nRoom: " + room
                + "\nChecked In: " + checkInDate
                + "\nChecked Out: " + checkOutDate;
    }
}
