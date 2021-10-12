package mode1;

public class freeRoom extends Room{

    public freeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, enumeration);
        this.price = 0.0;
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString(){
        return "Room: " + roomNumber + "; Room Type: " + enumeration + ", Price is free";
    }
}
