package utils;

import model.SpreadsheetFileDataTransferObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SpreadsheetFileMapper {

    private SpreadsheetFileMapper() {}

    public static SpreadsheetFileDataTransferObject mapRow(ResultSet resultSet) {
        SpreadsheetFileDataTransferObject dto = null;

        try {
            resultSet.next();
            dto = new SpreadsheetFileDataTransferObject(
                    resultSet.getLong("file_id"),
                    resultSet.getString("name"),
                    resultSet.getBytes("file_content"),
                    resultSet.getString("extension")
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dto;
    }

    public static List<SpreadsheetFileDataTransferObject> mapRows(ResultSet resultSet) {
        List<SpreadsheetFileDataTransferObject> listOfDTO = new ArrayList<>();

        try {
            while (!resultSet.isLast()) {
                listOfDTO.add(mapRow(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return listOfDTO;
    }
}
