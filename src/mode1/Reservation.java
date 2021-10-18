package mode1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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
        return "--\nReservation"
                + "\nCustomer Name:" + customer.getFirstName() + " " + customer.getLastName()
                + "\n" + room
                + "\nChecked In: " + sdf.format(checkInDate)
                + "\nChecked Out: " + sdf.format(checkOutDate);
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (!(o instanceof Reservation))
            return false;
        Reservation other = (Reservation) o;
        boolean customerEquals = (this.customer == null && other.customer == null)
                || (this.customer != null && this.customer.equals(other.customer));
        boolean roomEquals = (this.room == null && other.room == null)
                || (this.room != null && this.room.equals(other.room));
        boolean checkInEquals = (this.checkInDate == null && other.checkInDate == null)
                || (this.checkInDate != null && this.checkInDate.equals(other.checkInDate));
        boolean checkOutEquals = (this.checkOutDate == null && other.checkOutDate == null)
                || (this.checkOutDate != null && this.checkOutDate.equals(other.checkOutDate));
        return customerEquals && roomEquals && checkInEquals && checkOutEquals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
