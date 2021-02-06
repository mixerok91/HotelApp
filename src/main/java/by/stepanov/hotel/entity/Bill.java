package by.stepanov.hotel.entity;

public class Bill {
    private Long id;
    private double totalAmount;
    private boolean isPaid;
    private Reservation reservation;

    public Bill() {
    }

    public Bill(Long id,
                double totalAmount,
                boolean isPaid,
                Reservation reservation) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.isPaid = isPaid;
        this.reservation = reservation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bill bill = (Bill) o;

        if (Double.compare(bill.totalAmount, totalAmount) != 0) return false;
        if (isPaid != bill.isPaid) return false;
        if (id != null ? !id.equals(bill.id) : bill.id != null) return false;
        return reservation != null ? reservation.equals(bill.reservation) : bill.reservation == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isPaid ? 1 : 0);
        result = 31 * result + (reservation != null ? reservation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", isPaid=" + isPaid +
                ", reservation=" + reservation +
                '}';
    }
}
