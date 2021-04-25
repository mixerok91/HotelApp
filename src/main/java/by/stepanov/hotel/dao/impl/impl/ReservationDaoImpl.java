package by.stepanov.hotel.dao.impl.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.ReservationDao;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPool;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolException;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolProvider;
import by.stepanov.hotel.entity.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {

    private static final String CREATE_RESERVATION_QUERY =
            "INSERT INTO reservations (users_id, rooms_id, creation_time, in_date, out_date, book_status, is_visible) VALUES (?,?,?,?,?,?,?)";
    private static final String READ_RESERVATION_BY_ID_QUERY =
            "SELECT reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM reservations " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id " +
                    "WHERE reservations.id = ?";
    private static final String UPDATE_RESERVATION_QUERY =
            "UPDATE reservations SET users_id=?, rooms_id=?, creation_time=?, in_date=?, out_date=?, book_status=?, is_visible=? " +
                    "WHERE (reservations.id=?)";
    private static final String DELETE_RESERVATION_QUERY = "DELETE FROM reservations WHERE id = ?";
    private static final String READ_ALL_RESERVATIONS_QUERY =
            "SELECT reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM reservations " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id";
    private static final String REED_ALL_RESERVATION_BY_OUT_DATE_QUERY =
            "SELECT reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM reservations " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id " +
                    "WHERE reservations.out_date >= ?";
    private static final String REED_ALL_RESERVATION_BY_USER_ID_QUERY =
            "SELECT reservations.id, reservations.creation_time, reservations.in_date, reservations.out_date, reservations.book_status, reservations.is_visible, " +
                    "reservations.users_id, users.email, users.password, users.firstname, users.surname, users.role, " +
                    "rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, " +
                    "room_type.id, room_type.type, room_type.description_rus, room_type.description_eng " +
                    "FROM reservations " +
                    "JOIN users ON users.id = reservations.users_id " +
                    "JOIN rooms ON rooms.id = reservations.rooms_id " +
                    "JOIN room_type ON room_type.id = rooms.room_type_id " +
                    "WHERE users.id= ?";
    private static final String GET_LAST_INSERT_ID =
            "SELECT LAST_INSERT_ID() FROM reservations";

    private final ConnectionPool connectionPool = ConnectionPoolProvider.getConnectionPool();

    private static final Logger log = Logger.getLogger(ReservationDaoImpl.class);

    @Override
    public Reservation createReservation(Reservation reservation) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long lastId = null;
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY);

            preparedStatement.setLong(1, reservation.getUser().getId());
            preparedStatement.setLong(2,reservation.getRoom().getId());
            preparedStatement.setString(3, String.valueOf(reservation.getCreationTime()));
            preparedStatement.setString(4, String.valueOf(reservation.getInDate()));
            preparedStatement.setString(5, String.valueOf(reservation.getOutDate()));
            preparedStatement.setString(6,reservation.getBookStatus().name());
            preparedStatement.setBoolean(7, reservation.isVisible());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            preparedStatement = connection.prepareStatement(GET_LAST_INSERT_ID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                lastId = resultSet.getLong(1);
            }

            reservation.setId(lastId);

            log.info("Reservation: '" + reservation + "' created");

            return reservation;
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public Reservation readReservation(long reservationId) throws DAOException {

        Reservation reservation = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_RESERVATION_BY_ID_QUERY);
            preparedStatement.setLong(1, reservationId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                reservation = readReservationResultSet(resultSet);
            }

            log.info("Reservation: '" + reservation + "' read");
            return reservation;
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void updateReservation(Reservation reservation) throws DAOException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_QUERY);

            preparedStatement.setLong(1, reservation.getUser().getId());
            preparedStatement.setLong(2, reservation.getRoom().getId());
            preparedStatement.setString(3, String.valueOf(reservation.getCreationTime()));
            preparedStatement.setString(4, String.valueOf(reservation.getInDate()));
            preparedStatement.setString(5, String.valueOf(reservation.getOutDate()));
            preparedStatement.setString(6, reservation.getBookStatus().name());
            preparedStatement.setBoolean(7, reservation.isVisible());
            preparedStatement.setLong(8, reservation.getId());

            preparedStatement.execute();

            log.info("Reservation: '" + reservation + "' updated");
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void deleteReservation(long reservationId) throws DAOException{

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
            preparedStatement.setLong(1, reservationId);
            preparedStatement.execute();

            connection.commit();

            log.info("Reservation with id: '" + reservationId + "' deleted");
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Reservation> getAllReservations() throws DAOException {

        List<Reservation> reservations = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ALL_RESERVATIONS_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Reservation reservation = readReservationResultSet(resultSet);
                reservations.add(reservation);
            }

            log.info("All reservations read");

            return reservations;
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Reservation> getReservationsByAfterDate(String date) throws DAOException {

        List<Reservation> reservations = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REED_ALL_RESERVATION_BY_OUT_DATE_QUERY);
            preparedStatement.setString(1, date);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Reservation reservation = readReservationResultSet(resultSet);
                reservations.add(reservation);
            }

            log.info("All reservations after date: '" + date + "' read");

            return reservations;
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public List<Reservation> getReservationsByUser(User user) throws DAOException {

        List<Reservation> reservations = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(REED_ALL_RESERVATION_BY_USER_ID_QUERY);
            preparedStatement.setLong(1, user.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Reservation reservation = readReservationResultSet(resultSet);
                reservations.add(reservation);
            }

            log.info("All '" + user.getEmail() +"'s' reservations read");

            return reservations;
        } catch (SQLException e){
            log.error("SQL exception",e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            log.error("Connection pool exception", e);
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private Reservation readReservationResultSet(ResultSet resultSet) throws SQLException {

        Reservation reservation = new Reservation();
        User user = new User();
        Room room = new Room();
        RoomType roomType = new RoomType();

        reservation.setId(resultSet.getLong(1));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        reservation.setCreationTime(LocalDateTime.parse(resultSet.getString(2), dateTimeFormatter));
        reservation.setInDate(LocalDate.parse(resultSet.getString(3)));
        reservation.setOutDate(LocalDate.parse(resultSet.getString(4)));
        reservation.setBookStatus(BookStatus.valueOf(resultSet.getString(5)));
        reservation.setVisible(resultSet.getBoolean(6));

        user.setId(resultSet.getLong(7));
        user.setEmail(resultSet.getString(8));
        user.setPassword(resultSet.getString(9));
        user.setFirstName(resultSet.getString(10));
        user.setSurName(resultSet.getString(11));
        user.setRole(Role.valueOf(resultSet.getString(12)));

        room.setId(resultSet.getLong(13));
        room.setRoomNumber(resultSet.getString(14));
        room.setPersons(resultSet.getInt(15));
        room.setDayCost(resultSet.getDouble(16));
        room.setPicturePath(resultSet.getString(17));

        roomType.setId(resultSet.getLong(18));
        roomType.setTypeName(resultSet.getString(19));
        roomType.setDescriptionRus(resultSet.getString(20));
        roomType.setDescriptionEng(resultSet.getString(21));

        room.setRoomType(roomType);
        reservation.setUser(user);
        reservation.setRoom(room);

        return reservation;
    }
}
