package mode1;

import java.util.Objects;

public class Room implements IRoom{
    private String roomNumber;
    private Double price;
    private RoomType type;

    public Room(String n, Double p, RoomType enumeration){
        this.roomNumber = n;
        this.price = p;
        this.type = enumeration;
    }

    public Room(String roomNumber, RoomType enumeration) {
    }

    public void setPrice(double p) {
        this.price = p;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return type;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return "Room: " + roomNumber + "; Room Type: " + type + ", Price: $" + price;
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;
        if (!(o instanceof IRoom))
            return false;
        IRoom other = (IRoom) o;
        boolean numberEquals = (this.roomNumber == null && other.getRoomNumber() == null)
                || (this.roomNumber != null && this.roomNumber.equals(other.getRoomNumber()));
        boolean priceEquals = (this.price == null && other.getRoomPrice() == null)
                || (this.price != null && this.price.equals(other.getRoomPrice()));
        boolean typeEquals = (this.type == null && other.getRoomType() == null)
                || (this.type != null && this.type.equals(other.getRoomType()));
        return numberEquals && priceEquals && typeEquals;
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (roomNumber != null) {
            result = 31 * result + roomNumber.hashCode();
        }
        if (price != null) {
            result = 31 * result + price.hashCode();
        }
        if (type != null) {
            result = 31 * result + type.hashCode();
        }
        return result;
    }
}
