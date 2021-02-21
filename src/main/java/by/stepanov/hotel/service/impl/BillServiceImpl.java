package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.BillDao;
import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.entity.Bill;
import by.stepanov.hotel.entity.Reservation;
import by.stepanov.hotel.service.BillService;
import by.stepanov.hotel.service.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class BillServiceImpl implements BillService {

    private static final Logger log = Logger.getLogger(BillServiceImpl.class);

    private BillDao billDao = DaoProvider.getInstance().getBillDao();

    @Override
    public void createBill(Bill bill) throws ServiceException {
        try {
            billDao.createBill(bill);
            log.info("Bill with id: '" + bill.getId() + "' created");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bill readBill(long billId) throws ServiceException {
        try {
            log.info("Bill with id: '" + billId + "' read");
            return billDao.readBill(billId);
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBill(Bill bill) throws ServiceException {
        try {
            billDao.updateBill(bill);
            log.info("Bill with id: '" + bill.getId() + "' updated");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBill(long billId) throws ServiceException {
        try {
            billDao.deleteBill(billId);
            log.info("Bill with id: '" + billId + "' deleted");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Bill> getAllBills() throws ServiceException {
        try {
            log.info("All bills read");
            return billDao.getAllBills();
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Bill readBillByReservationId(Long reservationId) throws ServiceException {
        try {
            log.info("Bill by reservation id: '" + reservationId +"' read");
            return billDao.readBillByReservationId(reservationId);
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void setBillToReservations(List<Reservation> reservations) throws ServiceException {
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                reservation.setBill(readBillByReservationId(reservation.getId()));
                log.info("Bill set to reservation with id: '" + reservation.getId()+"'");
            }
        }
    }
}
