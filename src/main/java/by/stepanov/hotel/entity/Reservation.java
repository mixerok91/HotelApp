package by.stepanov.hotel.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation implements Serializable {

    private Long id;
    private LocalDateTime creationTime;
    private LocalDate inDate;
    private LocalDate outDate;
    private BookStatus bookStatus;
    boolean isVisible;
    private User user;
    private Room room;
    private Bill bill;

    public Reservation() {
    }

    public Reservation(Long id,
                       LocalDateTime creationTime,
                       LocalDate inDate,
                       LocalDate outDate,
                       BookStatus bookStatus,
                       boolean isVisible) {
        this.id = id;
        this.creationTime = creationTime;
        this.inDate = inDate;
        this.outDate = outDate;
        this.bookStatus = bookStatus;
        this.isVisible = isVisible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (isVisible != that.isVisible) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (creationTime != null ? !creationTime.equals(that.creationTime) : that.creationTime != null) return false;
        if (inDate != null ? !inDate.equals(that.inDate) : that.inDate != null) return false;
        if (outDate != null ? !outDate.equals(that.outDate) : that.outDate != null) return false;
        if (bookStatus != that.bookStatus) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        return bill != null ? bill.equals(that.bill) : that.bill == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (inDate != null ? inDate.hashCode() : 0);
        result = 31 * result + (outDate != null ? outDate.hashCode() : 0);
        result = 31 * result + (bookStatus != null ? bookStatus.hashCode() : 0);
        result = 31 * result + (isVisible ? 1 : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (bill != null ? bill.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", creationTime=" + creationTime +
                ", inDate=" + inDate +
                ", outDate=" + outDate +
                ", bookStatus=" + bookStatus +
                ", isVisible=" + isVisible +
                ", user=" + user +
                ", room=" + room +
                '}';
    }
}
