package by.stepanov.hotel.dao.impl.mysql;

import by.stepanov.hotel.dao.BillDao;
import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.impl.mysql.connectionpool.ConnectionPool;
import by.stepanov.hotel.dao.impl.mysql.connectionpool.ConnectionPoolException;
import by.stepanov.hotel.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillDaoImpl implements BillDao {

    private static final String CREATE_BILL_QUERY =
            "INSERT INTO bills (reservations_id, total_amount, is_paid_for) VALUES (?,?,?)";
    private static final String READ_BILL_BY_ID_QUERY =
            "SELECT bills.id, bills.total_amount, bills.is_paid_for, " +
                    "reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM bills " +
                    "JOIN reservations ON reservations.id = bills.reservations_id " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id " +
                    "WHERE bills.id = ?";
    private static final String UPDATE_BILL_QUERY =
            "UPDATE bills SET reservations_id=?, total_amount=?, is_paid_for=? " +
                    "WHERE (bills.id=?)";
    private static final String DELETE_BILL_BY_ID_QUERY = "DELETE FROM bills WHERE id = ?";
    private static final String READ_ALL_BILLS_QUERY =
            "SELECT bills.id, bills.total_amount, bills.is_paid_for, " +
                    "reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM bills " +
                    "JOIN reservations ON reservations.id = bills.reservations_id " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id";
    private static final String READ_ALL_BILL_BY_RESERVATION_ID_QUERY =
            "SELECT bills.id, bills.total_amount, bills.is_paid_for, " +
                    "reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM bills " +
                    "JOIN reservations ON reservations.id = bills.reservations_id " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id " +
                    "WHERE reservations.id = ?";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void createBill(Bill bill) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_BILL_QUERY);
            preparedStatement.setLong(1, bill.getReservation().getId());
            preparedStatement.setDouble(2, bill.getTotalAmount());
            preparedStatement.setBoolean(3, bill.isPaid());

            preparedStatement.executeUpdate();
//            TODO: add logger
        } catch (SQLException e) {
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Bill readBill(long billId) throws DAOException {

        Bill bill = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_BILL_BY_ID_QUERY);
            preparedStatement.setLong(1, billId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bill = readBillResultSet(resultSet);
            }
            return bill;
        } catch (SQLException e) {
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void updateBill(Bill bill) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_BILL_QUERY);

            preparedStatement.setLong(1, bill.getReservation().getId());
            preparedStatement.setDouble(2, bill.getTotalAmount());
            preparedStatement.setBoolean(3, bill.isPaid());
            preparedStatement.setLong(4, bill.getId());

            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void deleteBill(long billId) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_BILL_BY_ID_QUERY);

            preparedStatement.setLong(1, billId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Bill> getAllBills() throws DAOException {

        List<Bill> bills = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ALL_BILLS_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Bill bill = readBillResultSet(resultSet);
                bills.add(bill);
            }
            return bills;
        } catch (SQLException e) {
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public Bill readBillByReservationId(Long reservationId) throws DAOException {

        Bill bill = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ALL_BILL_BY_RESERVATION_ID_QUERY);
            preparedStatement.setLong(1, reservationId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                bill = readBillResultSet(resultSet);
            }

            return bill;
        } catch (SQLException e) {
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private Bill readBillResultSet(ResultSet resultSet) throws SQLException {

        Bill bill = new Bill();
        Reservation reservation = new Reservation();
        User user = new User();
        Room room = new Room();
        RoomType roomType = new RoomType();

        bill.setId(resultSet.getLong(1));
        bill.setTotalAmount(resultSet.getDouble(2));
        bill.setPaid(resultSet.getBoolean(3));

        reservation.setId(resultSet.getLong(4));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        reservation.setCreationTime(LocalDateTime.parse(resultSet.getString(5), dateTimeFormatter));
        reservation.setInDate(LocalDate.parse(resultSet.getString(6)));
        reservation.setOutDate(LocalDate.parse(resultSet.getString(7)));
        reservation.setBookStatus(BookStatus.valueOf(resultSet.getString(8)));
        reservation.setVisible(resultSet.getBoolean(9));

        user.setId(resultSet.getLong(10));
        user.setEmail(resultSet.getString(11));
        user.setPassword(resultSet.getString(12));
        user.setFirstName(resultSet.getString(13));
        user.setSurName(resultSet.getString(14));
        user.setRole(Role.valueOf(resultSet.getString(15)));

        room.setId(resultSet.getLong(16));
        room.setRoomNumber(resultSet.getString(17));
        room.setPersons(resultSet.getInt(18));
        room.setDayCost(resultSet.getDouble(19));
        room.setPicturePath(resultSet.getString(20));

        roomType.setId(resultSet.getLong(21));
        roomType.setTypeName(resultSet.getString(22));
        roomType.setDescriptionRus(resultSet.getString(23));
        roomType.setDescriptionEng(resultSet.getString(24));

        room.setRoomType(roomType);
        reservation.setUser(user);
        reservation.setRoom(room);
        bill.setReservation(reservation);

        return bill;
    }
}
