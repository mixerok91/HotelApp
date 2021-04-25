package by.stepanov.hotel.dao.impl.connectionpool;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DBResourceManager {

    private static final Logger log = Logger.getLogger(DBResourceManager.class);

    private final static DBResourceManager instance = new DBResourceManager();

    private InputStream inputStream;
    private Properties properties;

    private DBResourceManager() {
    }

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public void setProperties(String propertiesFileName){
        inputStream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            log.error("IOException",e);
            throw new ConnectionPoolException(e);
        }
    }
}
