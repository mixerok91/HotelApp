package by.stepanov.hotel.dao.impl.mysql.connectionpool;

import java.io.IOException;

public class ConnectionPoolException extends RuntimeException{

    public ConnectionPoolException(String message, Exception e){
        super(message,e);
    }

    public ConnectionPoolException(IOException message) {
        super(message);
    }
}
