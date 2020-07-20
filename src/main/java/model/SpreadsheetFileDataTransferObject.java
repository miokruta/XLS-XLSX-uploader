package model;

public final class SpreadsheetFileDataTransferObject implements IDataTransferObject{
    private long id;
    private String name;
    private byte[] spreadsheetFileContent;
    private String extension;

    private SpreadsheetFileDataTransferObject() {}

    public SpreadsheetFileDataTransferObject(long id, String name, byte[] spreadsheetFileContent, String fileExtension) {
        this.id = id;
        this.name = name;
        this.spreadsheetFileContent = spreadsheetFileContent;
        this.extension = fileExtension;
    }

    @Override
    public long getId() {return this.id;}

    @Override
    public String getName() {
        return this.name;
    }

    public byte[] getSpreadsheetFileContent() {
        return this.spreadsheetFileContent;
    }

    public String getExtension() {
        return this.extension;
    }
}
