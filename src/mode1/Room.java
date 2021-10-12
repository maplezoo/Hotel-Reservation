package mode1;

public class Room implements IRoom{
    String roomNumber;
    Double price;
    RoomType enumeration;

    public Room(String n, Double p, RoomType enumeration){
        this.roomNumber = n;
        this.price = p;
        this.enumeration = enumeration;
    }

    public Room(String roomNumber, RoomType enumeration) {
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
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return "Room: " + roomNumber + "; Room Type: " + enumeration + ", Price: $" + price;
    }
}
