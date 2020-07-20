package dao;

import java.sql.Connection;
import java.util.HashMap;

public final class JDBCPostgreDataAccessObject implements IDataAccessObject{
    private Connection connection;

    private JDBCPostgreDataAccessObject() {}

    public JDBCPostgreDataAccessObject(HashMap<String, String> properties) {
        this.connection = connect(
                properties.get("URL"),
                properties.get("LOGIN"),
                properties.get("PASSWORD")
        );
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
