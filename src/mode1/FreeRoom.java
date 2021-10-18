package mode1;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType enumeration) {
        super(roomNumber, enumeration);
        this.setPrice(0.0);
    }

    @Override
    public boolean isFree() {
        return true;
    }

    @Override
    public String toString(){
        return "Room: " + this.getRoomNumber() + "; Room Type: " + this.getRoomType() + ", Price is free";
    }
}
