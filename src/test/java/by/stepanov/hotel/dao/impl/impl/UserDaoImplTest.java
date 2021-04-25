package by.stepanov.hotel.dao.impl.impl;

import by.stepanov.hotel.dao.DAOException;
import by.stepanov.hotel.dao.DaoProvider;
import by.stepanov.hotel.dao.UserDao;
import by.stepanov.hotel.dao.impl.connectionpool.ConnectionPoolProvider;
import by.stepanov.hotel.entity.Role;
import by.stepanov.hotel.entity.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoImplTest {

    private static final String DATABASE_PROPERTY_FILE_NAME = "database_test.properties";

    private final UserDao userDao = DaoProvider.getInstance().getUserDao();

    private List<Long> userIds = new ArrayList<>();

    @BeforeClass
    public static void connectionPoolInitialization() {
        ConnectionPoolProvider.getConnectionPool().initPoolData(DATABASE_PROPERTY_FILE_NAME);
    }

    @AfterClass
    public static void connectionPoolDispose() throws DAOException {
        ConnectionPoolProvider.getConnectionPool().dispose();
    }

    @Test
    public void createUser() throws DAOException {
        User user = new User();
        user.setEmail("user@user.com");
        user.setPassword("User123");
        user.setFirstName("User");
        user.setSurName("Userovich");
        user.setRole(Role.USER);
        user.setCreationDate(LocalDate.now());

        userDao.createUser(user);
    }

    @Test
    public void createAdmin() throws DAOException {
        User user = new User();
        user.setEmail("admin@admin.com");
        user.setPassword("Admin123");
        user.setFirstName("Admin");
        user.setSurName("Adminovich");
        user.setRole(Role.ADMIN);
        user.setCreationDate(LocalDate.now());

        userDao.createUser(user);
    }

    @Test
    public void getAllUsers() throws DAOException {
        userDao.getAllUsers().stream().forEach(user -> userIds.add(user.getId()));
        assertEquals(2, userIds.size());
    }

    @Test
    public void testReadUser() throws DAOException {
        assertNotNull(userDao.readUser("admin@admin.com"));
    }

    @Test
    public void readUserByEmailAssertNull() throws DAOException {
        assertNull(userDao.readUser("user@admin.com"));
    }

    @Test
    public void updateUser() throws DAOException {
        User user = userDao.readUser("user@user.com");
        user.setFirstName("Jack");
        user.setLastInDate(LocalDate.now());
        userDao.updateUser(user);
    }

}