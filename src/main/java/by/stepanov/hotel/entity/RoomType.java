package by.stepanov.hotel.entity;

import java.io.Serializable;
import java.util.Set;

public class RoomType implements Serializable {

    private Long id;
    private String typeName;
    private String descriptionRus;
    private String descriptionEng;
    private Set<Room> rooms;

    public RoomType() {
    }

    public RoomType(Long id,
                    String typeName,
                    String descriptionRus,
                    String descriptionEng) {
        this.id = id;
        this.typeName = typeName;
        this.descriptionRus = descriptionRus;
        this.descriptionEng = descriptionEng;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDescriptionRus() {
        return descriptionRus;
    }

    public void setDescriptionRus(String descriptionRus) {
        this.descriptionRus = descriptionRus;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomType roomType = (RoomType) o;

        if (id != null ? !id.equals(roomType.id) : roomType.id != null) return false;
        if (typeName != null ? !typeName.equals(roomType.typeName) : roomType.typeName != null) return false;
        if (descriptionRus != null ? !descriptionRus.equals(roomType.descriptionRus) : roomType.descriptionRus != null)
            return false;
        return descriptionEng != null ? descriptionEng.equals(roomType.descriptionEng) : roomType.descriptionEng == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (descriptionRus != null ? descriptionRus.hashCode() : 0);
        result = 31 * result + (descriptionEng != null ? descriptionEng.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoomType{" + "\n" +
                "id=" + id + "\n" +
                "typeName='" + typeName + '\'' + "\n" +
                "descriptionRus='" + descriptionRus + '\'' + "\n" +
                "descriptionEng='" + descriptionEng + '\'' + "\n" +
                '}';
    }
}
