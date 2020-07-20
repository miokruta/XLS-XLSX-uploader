package repo;

import dao.IDataAccessObject;
import dao.JDBCPostgreDataAccessObject;
import model.IDataTransferObject;
import model.SpreadsheetFileDataTransferObject;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import utils.SpreadsheetFileMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public final class SpreadsheetFileRepository implements IDataTransferObjectRepository {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private IDataAccessObject dao;
    private HashMap<String, String> queries;

    private SpreadsheetFileRepository() {}

    public SpreadsheetFileRepository(JDBCPostgreDataAccessObject dao, HashMap<String, String> queries) {
        this.dao = dao;
        this.queries = queries;
    }

    @Override
    public boolean add(IDataTransferObject dto) {
        SpreadsheetFileDataTransferObject spreadsheetFileDTO = (SpreadsheetFileDataTransferObject) dto;

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(queries.get("INSERT"))){
            preparedStatement.setString(1, spreadsheetFileDTO.getName());
            preparedStatement.setBytes(2, spreadsheetFileDTO.getSpreadsheetFileContent());
            preparedStatement.setString(3, spreadsheetFileDTO.getExtension());
            return dao.executePreparedStatement(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(String fileName) {
        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(queries.get("DELETE"))){
            preparedStatement.setString(1, fileName);
            return dao.executePreparedStatement(preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public SpreadsheetFileDataTransferObject findByName(String fileName) {
        SpreadsheetFileDataTransferObject dto = null;

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(queries.get("FIND_BY_NAME"))) {
            preparedStatement.setString(1, fileName);
            ResultSet resultSet = dao.getResultSetOnExecutePreparedStatement(preparedStatement);
            dto = SpreadsheetFileMapper.mapRow(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dto;
    }

    @Override
    public SpreadsheetFileDataTransferObject findById(long id) {
        SpreadsheetFileDataTransferObject dto = null;

        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(queries.get("FIND_BY_ID"))) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = dao.getResultSetOnExecutePreparedStatement(preparedStatement);
            dto = SpreadsheetFileMapper.mapRow(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dto;
    }

    public List<SpreadsheetFileDataTransferObject> findAll() {
        try (PreparedStatement preparedStatement = dao.getConnection().prepareStatement(queries.get("FIND_ALL"))) {
            ResultSet resultSet = dao.getResultSetOnExecutePreparedStatement(preparedStatement);
            return SpreadsheetFileMapper.mapRows(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }
}


