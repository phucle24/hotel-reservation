package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public String toString(){
        return String.format(
                "FreeRoom: Room Number: %s, Room Type: %s, Price: $0.0",
                getRoomNumber(), getRoomType());
    }
}
