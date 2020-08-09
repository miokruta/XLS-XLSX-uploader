package utils;

import exception.UnsupportedSpreadsheetFileFormatException;
import model.SpreadsheetFileDataTransferObject;
import org.apache.poi.UnsupportedFileFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public static Optional<SpreadsheetFileDataTransferObject> mapMultipartFile(MultipartFile file) throws UnsupportedFileFormatException {
        try {
            String[] parts = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 1);
            if (!parts[1].equals("xls") && !parts[1].equals("xlsx")) {
                throw new UnsupportedSpreadsheetFileFormatException(parts[1]);
            }

            return Optional.of(new SpreadsheetFileDataTransferObject(
                    -1,
                    parts[0],
                    file.getBytes(),
                    parts[1]
            ));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
