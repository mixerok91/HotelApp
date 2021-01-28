package by.stepanov.hotel.dao.impl.mysql;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.UserDao;
import by.stepanov.hotel.dao.impl.mysql.connectionpool.ConnectionPool;
import by.stepanov.hotel.dao.impl.mysql.connectionpool.ConnectionPoolException;
import by.stepanov.hotel.entity.Role;
import by.stepanov.hotel.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users (email, password, firstname, surname, role, creation_date) VALUES (?,?,?,?,?,?)";
    private static final String READ_USER_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String READ_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email=?, password=?, firstname=?, surname=?, role=?, " +
            "creation_date=?, last_in_date=? WHERE (users.id=?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id=?";
//    TODO: Adding queries for deleting all data, depended from user
    private static final String READ_ALL_USERS_QUERY = "SELECT * FROM users ORDER BY id";

    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void createUser(User user) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_USER_QUERY);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getSurName());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.setString(6, String.valueOf(user.getCreationDate()));
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
    public User readUser(long userId) throws DAOException {

        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_USER_BY_ID_QUERY);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                user = readUserFromResultSet(resultSet);
            }
            return user;
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
    public User readUser(String email) throws DAOException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_USER_BY_EMAIL_QUERY);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                user = readUserFromResultSet(resultSet);
            }
            return user;
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
    public void updateUser(User user) throws DAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getSurName());
            preparedStatement.setString(5, user.getRole().name());
            preparedStatement.setDate(6, Date.valueOf(user.getCreationDate()));
            preparedStatement.setDate(7, Date.valueOf(user.getLastInDate()));
            preparedStatement.setLong(8, user.getId());

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
    public void deleteUser(long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);

            preparedStatement.setLong(1, userId);
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
    public List<User> getAllUsers() throws DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(READ_ALL_USERS_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                User user = readUserFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e){
            System.err.println(e);
            throw new DAOException("SQL error", e);
        } catch (ConnectionPoolException e){
            throw new DAOException("Connection pool error", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    private User readUserFromResultSet(ResultSet resultSet) throws SQLException {

        User user = new User();

        user.setId(resultSet.getLong(1));
        user.setEmail(resultSet.getString(2));
        user.setPassword(resultSet.getString(3));
        user.setFirstName(resultSet.getString(4));
        user.setSurName(resultSet.getString(5));
        user.setRole(Role.valueOf(resultSet.getString(6)));
        user.setCreationDate(LocalDate.parse(resultSet.getString(7)));
        if (resultSet.getString(8) != null){
            user.setLastInDate(LocalDate.parse(resultSet.getString(8)));
        } else {
            user.setLastInDate(null);
        }
        return user;
    }
}
