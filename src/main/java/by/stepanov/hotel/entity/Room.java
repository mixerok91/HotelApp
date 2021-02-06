package by.stepanov.hotel.entity;

public class Room {

    private Long id;
    private String roomNumber;
    private int persons;
    private double dayCost;
    private String picturePath;
    private RoomType roomType;

    public Room() {
    }

    public Room(Long id,
                String roomNumber,
                int persons,
                double dayCost,
                String picturePath) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.persons = persons;
        this.dayCost = dayCost;
        this.picturePath = picturePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getPersons() {
        return persons;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public double getDayCost() {
        return dayCost;
    }

    public void setDayCost(double dayCost) {
        this.dayCost = dayCost;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (persons != room.persons) return false;
        if (Double.compare(room.dayCost, dayCost) != 0) return false;
        if (id != null ? !id.equals(room.id) : room.id != null) return false;
        if (roomNumber != null ? !roomNumber.equals(room.roomNumber) : room.roomNumber != null) return false;
        return picturePath != null ? picturePath.equals(room.picturePath) : room.picturePath == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (roomNumber != null ? roomNumber.hashCode() : 0);
        result = 31 * result + persons;
        temp = Double.doubleToLongBits(dayCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (picturePath != null ? picturePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber='" + roomNumber + '\'' +
                ", persons=" + persons +
                ", dayCost=" + dayCost +
                ", picturePath='" + picturePath + '\'' +
                ", roomType=" + roomType +
                '}';
    }
}
