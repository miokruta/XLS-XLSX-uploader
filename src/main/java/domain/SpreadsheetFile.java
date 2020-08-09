package model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "spreadsheet_files", uniqueConstraints={@UniqueConstraint(columnNames={"id"})})

public class SpreadsheetFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private long id;

    @Column(name = "name", length=20)
    private String name;

    @Column(name = "file_content")
    private byte[] spreadsheetFileContent;

    @Column(name = "extension")
    private String extension;

    public SpreadsheetFile() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSpreadsheetFileContent() {
        return spreadsheetFileContent;
    }

    public void setSpreadsheetFileContent(byte[] spreadsheetFileContent) {
        this.spreadsheetFileContent = spreadsheetFileContent;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof SpreadsheetFile)){
            return false;
        }

        SpreadsheetFile dto = (SpreadsheetFile) obj;
        return Objects.equals(this.id, dto.getId())
                && Objects.equals(this.name, dto.getName())
                && Arrays.equals(this.spreadsheetFileContent, dto.getSpreadsheetFileContent())
                && Objects.equals(this.extension, dto.getExtension());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.spreadsheetFileContent, this.extension);
    }

    @Override
    public String toString() {
        return "SpreadsheetFileDataTransferObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}
