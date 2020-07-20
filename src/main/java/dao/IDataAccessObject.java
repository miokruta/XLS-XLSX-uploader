package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public interface IDataAccessObject {
    default Connection connect(String URL, String USER, String PASSWORD) {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return conn;
    }

    default Statement getStatement(Connection connection) {
        Statement statement = null;

        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return statement;
    }

    default ResultSet getResultSetOnExecutePreparedStatement(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;

        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    default boolean executePreparedStatement(PreparedStatement preparedStatement) {
        try {
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    Connection getConnection();
}
