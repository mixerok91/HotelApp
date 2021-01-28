package by.stepanov.hotel.dao.impl.mysql;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.RoomDao;
import by.stepanov.hotel.dao.impl.mysql.connectionpool.ConnectionPool;
import by.stepanov.hotel.dao.impl.mysql.connectionpool.ConnectionPoolException;
import by.stepanov.hotel.entity.Room;
import by.stepanov.hotel.entity.RoomType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    private static final String CREATE_ROOM_QUERY =
            "INSERT INTO rooms (room_number, persons, cost_per_day, room_type_id, picture_path) VALUES (?,?,?,?,?)";
    private static final String READ_ROOM_BY_ID_QUERY =
            "SELECT rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, rooms.room_type_id, room_type.type, " +
                    "room_type.description_rus, room_type.description_eng " +
            "FROM rooms JOIN room_type ON room_type.id = rooms.room_type_id " +
            "WHERE rooms.id = ?";
    private static final String UPDATE_ROOM_QUERY =
            "UPDATE rooms SET room_number=?, persons=?, cost_per_day=?, picture_path=?, room_type_id=? " +
            "WHERE (rooms.id=?)";
    private static final String DELETE_ROOM_QUERY = "DELETE FROM rooms WHERE id=?";
    private static final String READ_ALL_ROOMS_QUERY =
            "SELECT rooms.id, rooms.room_number, rooms.persons, rooms.cost_per_day, rooms.picture_path, rooms.room_type_id, room_type.type, " +
                    "room_type.description_rus, room_type.description_eng " +
            "FROM rooms JOIN room_type ON room_type.id = rooms.room_type_id";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void createRoom(Room room) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_ROOM_QUERY);

            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getPersons());
            preparedStatement.setDouble(3, room.getDayCost());
            preparedStatement.setLong(4, room.getRoomType().getId());
            preparedStatement.setString(5, room.getPicturePath());

            preparedStatement.executeUpdate();
//            TODO: add logger
        } catch (SQLException e){
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public Room readRoom(long roomId) throws DAOException {
        Room room = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ROOM_BY_ID_QUERY);
            preparedStatement.setLong(1, roomId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                room = readRoomResultSet(resultSet);
            }
            return room;
        } catch (SQLException e){
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void updateRoom(Room room) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ROOM_QUERY);

            preparedStatement.setString(1, room.getRoomNumber());
            preparedStatement.setInt(2, room.getPersons());
            preparedStatement.setDouble(3, room.getDayCost());
            preparedStatement.setString(4, room.getPicturePath());
            preparedStatement.setLong(5, room.getRoomType().getId());
            preparedStatement.setLong(6, room.getId());

            preparedStatement.execute();
        } catch (SQLException e){
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void deleteRoom(long roomId) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_ROOM_QUERY);

            preparedStatement.setLong(1, roomId);
            preparedStatement.execute();
        } catch (SQLException e){
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Room> getAllRooms() throws DAOException {
        List<Room> roomTypes = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ALL_ROOMS_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Room room = readRoomResultSet(resultSet);
                roomTypes.add(room);
            }
            return roomTypes;
        } catch (SQLException e){
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private Room readRoomResultSet(ResultSet resultSet) throws SQLException {

        Room room = new Room();
        RoomType roomType = new RoomType();

        room.setId(resultSet.getLong(1));
        room.setRoomNumber(resultSet.getString(2));
        room.setPersons(resultSet.getInt(3));
        room.setDayCost(resultSet.getDouble(4));
        room.setPicturePath(resultSet.getString(5));

        roomType.setId(resultSet.getLong(6));
        roomType.setTypeName(resultSet.getString(7));
        roomType.setDescriptionRus(resultSet.getString(8));
        roomType.setDescriptionEng(resultSet.getString(9));

        room.setRoomType(roomType);

        return room;
    }
}