package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import enums.FileExtensions;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "spreadsheet_files", uniqueConstraints={@UniqueConstraint(columnNames={"file_id"})})

public class SpreadsheetFile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private long id;

    @Column(name = "name", length=20)
    private String name;

    @JsonIgnore
    @E
    @Column(name = "file_content")
    private byte[] content;

    @Enumerated(EnumType.STRING)
    @Type(type = "enums.FileExtensionTypePostgreSQL")
    @Column(name = "extension")
    private FileExtensions extension;

    protected SpreadsheetFile() {}

    public SpreadsheetFile(long id, String name, byte[] spreadsheetFileContent, FileExtensions extension) {
        this.id = id;
        this.name = name;
        this.content = spreadsheetFileContent;
        this.extension = extension;
    }

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public FileExtensions getExtension() {
        return extension;
    }

    public void setExtension(FileExtensions extension) {
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
                && Arrays.equals(this.content, dto.content)
                && this.extension == dto.extension;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.content, this.extension.name());
    }

    @Override
    public String toString() {
        return "SpreadsheetFileDataTransferObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", extension='" + extension.name() + '\'' +
                '}';
    }
}
