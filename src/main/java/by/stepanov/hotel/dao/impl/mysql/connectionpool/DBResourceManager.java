package by.stepanov.hotel.dao.impl.mysql.connectionpool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private final static DBResourceManager instance = new DBResourceManager();

    //private ResourceBundle bundle = ResourceBundle.getBundle("by.stepanov.hotel.dao.impl.mysql.connectionpool.database");


    public static DBResourceManager getInstance(){
        return instance;
    }
//TODO: file with properties
    public String getValue(String key){
        return "";
    }
}
