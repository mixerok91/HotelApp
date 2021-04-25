package by.stepanov.hotel.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public class User implements Serializable {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String surName;
    private Role role;
    private LocalDate creationDate;
    private LocalDate lastInDate;
    private Set<Reservation> reservations;

    public User() {
    }

    public User(String email,
                String password,
                String firstName,
                String surName,
                Role role,
                LocalDate creationDate,
                LocalDate lastInDate) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.surName = surName;
        this.role = role;
        this.creationDate = creationDate;
        this.lastInDate = lastInDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastInDate() {
        return lastInDate;
    }

    public void setLastInDate(LocalDate lastInDate) {
        this.lastInDate = lastInDate;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (surName != null ? !surName.equals(user.surName) : user.surName != null) return false;
        if (role != user.role) return false;
        if (creationDate != null ? !creationDate.equals(user.creationDate) : user.creationDate != null) return false;
        return lastInDate != null ? lastInDate.equals(user.lastInDate) : user.lastInDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surName != null ? surName.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastInDate != null ? lastInDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", role=" + role +
                ", creationDate=" + creationDate +
                ", lastInDate=" + lastInDate +
                '}';
    }
}