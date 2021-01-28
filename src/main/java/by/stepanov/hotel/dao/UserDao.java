package by.stepanov.hotel.dao;

import by.stepanov.hotel.entity.User;

import java.util.List;

public interface UserDao {

    void createUser(User user) throws DAOException;

    User readUser(long userId) throws DAOException;

    User readUser(String email) throws DAOException;

    void updateUser(User user) throws DAOException;

    void deleteUser(long userId) throws DAOException;

    List<User> getAllUsers() throws DAOException;
}
