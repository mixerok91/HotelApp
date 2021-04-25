package by.stepanov.hotel.dao.impl.connectionpool;

public final class ConnectionPoolProvider {

    private static ConnectionPool connectionPool = new ConnectionPool();

    private ConnectionPoolProvider(){}

    public static ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
