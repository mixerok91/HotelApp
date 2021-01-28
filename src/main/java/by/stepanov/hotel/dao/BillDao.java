package by.stepanov.hotel.dao;

import by.stepanov.hotel.entity.Bill;

import java.util.List;

public interface BillDao {

    void createBill(Bill bill) throws DAOException;

    Bill readBill(long billId) throws DAOException;

    void updateBill(Bill bill) throws DAOException;

    void deleteBill(long billId) throws DAOException;

    List<Bill> getAllBills() throws DAOException;

    Bill readBillByReservationId(Long reservationId) throws DAOException;
}
