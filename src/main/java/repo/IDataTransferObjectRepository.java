package repo;

import model.IDataTransferObject;
import model.SpreadsheetFileDataTransferObject;

import java.util.List;

public interface IDataTransferObjectRepository {
    boolean add(IDataTransferObject dto);
    boolean delete(String name);
    IDataTransferObject findByName(String name);
    IDataTransferObject findById(long id);
    List<SpreadsheetFileDataTransferObject> findAll();
}
