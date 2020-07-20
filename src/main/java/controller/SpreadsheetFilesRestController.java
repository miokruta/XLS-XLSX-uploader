package controller;

import org.springframework.web.multipart.MultipartFile;
import repo.IDataTransferObjectRepository;
import model.SpreadsheetFileDataTransferObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.memory.FileMapInfo;
import utils.PostFileHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SpreadsheetFilesRestController {
    private IDataTransferObjectRepository dataTransferObjectRepository;

    private SpreadsheetFilesRestController() {}

    public SpreadsheetFilesRestController(IDataTransferObjectRepository dataTransferObjectRepository) {
        this.dataTransferObjectRepository = dataTransferObjectRepository;
    }

    @RequestMapping(value = "/api/spreadsheet-files/get", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getFile(@RequestParam(name = "id") long id) {
        SpreadsheetFileDataTransferObject spreadsheetFileDataTransferObject = (SpreadsheetFileDataTransferObject) dataTransferObjectRepository.findById(id);
        FileMapInfo fileMapInfo = new FileMapInfo();
        //if(spreadsheetFileDataTransferObject != null) {
            //headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
            headers.setContentDispositionFormData(
                    spreadsheetFileDataTransferObject.getName(),
                    spreadsheetFileDataTransferObject.getName());
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return new ResponseEntity<>(
                    spreadsheetFileDataTransferObject.getSpreadsheetFileContent(),
                    headers,
                    HttpStatus.OK);
        //}
    }

    @RequestMapping(value = "/api/spreadsheet-files", method = RequestMethod.GET, produces = "application/json")
    public Map<Long,String> getListOfFile() {
        //json output
        Map<Long,String> list2d = new HashMap<>();
        List<SpreadsheetFileDataTransferObject> list = dataTransferObjectRepository.findAll();

        for (SpreadsheetFileDataTransferObject dto: list) {
            list2d.put(dto.getId(), dto.getName()+dto.getExtension());
        }

        return list2d;
    }

    @RequestMapping(value = "/api/spreadsheet-files/import", method = RequestMethod.POST, produces = "application/json")
    public String handleImport(@RequestParam("file") final MultipartFile file) {
        if(!file.isEmpty()) {
            PostFileHandler postFileHandler = new PostFileHandler(file);
            dataTransferObjectRepository.add(
                        new SpreadsheetFileDataTransferObject(
                                -1,
                                postFileHandler.getFileName(),
                                postFileHandler.getFileContent(),
                                postFileHandler.getExtension()

                        )
                );
            return "imported";
        }

        return "error";
    }
}
