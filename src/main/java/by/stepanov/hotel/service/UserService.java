package by.stepanov.hotel.service;

import by.stepanov.hotel.entity.User;

import java.util.List;

public interface UserService {
    void createUser(User user) throws ServiceException;

    User readUser(long userId) throws ServiceException;

    User readUser(String email) throws ServiceException;

    void updateUser(User user) throws ServiceException;

    void deleteUser(long userId) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;
}
