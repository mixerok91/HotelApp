package by.stepanov.hotel.service;

import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.Reservation;

import java.util.List;

public interface BillService {

    void createBill(Bill bill) throws ServiceException;

    Bill readBill(long billId) throws ServiceException;

    void updateBill(Bill bill) throws ServiceException;

    void deleteBill(long billId) throws ServiceException;

    List<Bill> getAllBills() throws ServiceException;

    Bill readBillByReservationId(Long id) throws ServiceException;

    void setBillToReservations(List<Reservation> userReservations) throws ServiceException;
}
