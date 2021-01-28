package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.UserDao;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao = DaoProvider.getInstance().getUserDao();


    //    TODO: Add logger

    @Override
    public void createUser(User user) throws ServiceException {
        try {
            user.setCreationDate(LocalDate.now());
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userDao.createUser(user);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUser(long userId) throws ServiceException {
        try {
            return userDao.readUser(userId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUser(String email) throws ServiceException {
        try {
            return userDao.readUser(email);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            userDao.updateUser(user);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(long userId) throws ServiceException {
        try {
            userDao.deleteUser(userId);
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            return userDao.getAllUsers();
        } catch (DAOException e) {
            System.err.println(e);
            throw new ServiceException(e);
        }
    }
}
