package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.BillDao;
import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.service.BillService;
import by.stepanov.hotel.service.ServiceException;

import java.util.List;

public class BillServiceImpl implements BillService {

    private BillDao billDao = DaoProvider.getInstance().getBillDao();

    @Override
    public void createBill(Bill bill) throws ServiceException {
        try {
            billDao.createBill(bill);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bill readBill(long billId) throws ServiceException {
        try {
            return billDao.readBill(billId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBill(Bill bill) throws ServiceException {
        try {
            billDao.updateBill(bill);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBill(long billId) throws ServiceException {
        try {
            billDao.deleteBill(billId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bill> getAllBills() throws ServiceException {
        try {
            return billDao.getAllBills();
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bill readBillByReservationId(Long reservationId) throws ServiceException {
        try {
            return billDao.readBillByReservationId(reservationId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }
}
