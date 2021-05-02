package by.stepanov.hotel.dao.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.RoomTypeDao;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPool;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolException;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolProvider;
import by.stepanov.hotel.entity.RoomType;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeDaoImpl implements RoomTypeDao {

    private static final String CREATE_ROOM_TYPE_QUERY =
            "INSERT INTO room_type (type, description_rus, description_eng) VALUES (?,?,?)";
    private static final String READ_ROOM_TYPE_BY_ID_QUERY = "SELECT * FROM room_type WHERE id = ?";
    private static final String UPDATE_ROOM_TYPE_QUERY = "UPDATE room_type SET type=?, description_rus=?, description_eng=? " +
            "WHERE (room_type.id=?)";
    private static final String DELETE_ROOM_TYPE_QUERY = "DELETE FROM room_type WHERE id=?";
    private static final String READ_ALL_ROOM_TYPES_QUERY = "SELECT * FROM room_type ORDER BY id";

    private final ConnectionPool connectionPool = ConnectionPoolProvider.getConnectionPool();

    private static final Logger log = Logger.getLogger(RoomTypeDaoImpl.class);

    @Override
    public void createRoomType(RoomType roomType) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_ROOM_TYPE_QUERY);

            preparedStatement.setString(1, roomType.getTypeName());
            preparedStatement.setString(2, roomType.getDescriptionRus());
            preparedStatement.setString(3, roomType.getDescriptionEng());
            preparedStatement.executeUpdate();

            log.info("Room type '" + roomType + "' created");
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
    public RoomType readRoomType(long roomTypeId) throws DAOException {

        RoomType roomType = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ROOM_TYPE_BY_ID_QUERY);
            preparedStatement.setLong(1, roomTypeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                roomType = readRoomTypeFromResultSet(resultSet);
            }
            log.info("Room type: '" + roomType + "' read");
            return roomType;
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
    public void updateRoomType(RoomType roomType) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ROOM_TYPE_QUERY);

            preparedStatement.setString(1, roomType.getTypeName());
            preparedStatement.setString(2, roomType.getDescriptionRus());
            preparedStatement.setString(3, roomType.getDescriptionEng());
            preparedStatement.setLong(4, roomType.getId());

            preparedStatement.execute();

            log.info("Room type: '" + roomType + "' updated");
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
    public void deleteRoomType(long roomTypeId) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_ROOM_TYPE_QUERY);

            preparedStatement.setLong(1, roomTypeId);
            preparedStatement.execute();

            log.info("Room type with id: '" + roomTypeId + "' deleted");
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
    public List<RoomType> getAllRoomTypes() throws DAOException {

        List<RoomType> roomTypes = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ALL_ROOM_TYPES_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                RoomType roomType = readRoomTypeFromResultSet(resultSet);
                roomTypes.add(roomType);
            }

            log.info("All room types read");

            return roomTypes;
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

    private RoomType readRoomTypeFromResultSet(ResultSet resultSet) throws SQLException {

        RoomType roomType = new RoomType();

        roomType.setId(resultSet.getLong(1));
        roomType.setTypeName(resultSet.getString(2));
        roomType.setDescriptionRus(resultSet.getString(3));
        roomType.setDescriptionEng(resultSet.getString(4));

        return roomType;
    }
}
