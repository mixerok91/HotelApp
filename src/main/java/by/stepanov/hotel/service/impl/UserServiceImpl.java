package by.stepanov.hotel.service.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.UserDao;
import by.stepanov.hotel.entity.User;
import by.stepanov.hotel.service.ServiceException;
import by.stepanov.hotel.service.UserService;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao = DaoProvider.getInstance().getUserDao();

    @Override
    public void createUser(User user) throws ServiceException {
        try {
            user.setCreationDate(LocalDate.now());
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            userDao.createUser(user);

            log.info("User type with email: '" + user.getEmail() + "' created");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUser(long userId) throws ServiceException {
        try {
            log.info("User type with id: '" + userId + "' was read");
            return userDao.readUser(userId);
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public User readUser(String email) throws ServiceException {
        try {
            log.info("User type with email: '" + email + "' was read");
            return userDao.readUser(email);
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            userDao.updateUser(user);
            log.info("User type with id: '" + user.getId() + "' updated");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUser(long userId) throws ServiceException {
        try {
            userDao.deleteUser(userId);
            log.info("User type with id: '" + userId + "' deleted");
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
            log.info("All users was read");
            return userDao.getAllUsers();
        } catch (DAOException e) {
            log.error("DAO exception",e);
            throw new ServiceException(e);
        }
    }
}
