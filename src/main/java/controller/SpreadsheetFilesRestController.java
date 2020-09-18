package controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import exception.EmptyMultipartFileException;
import exception.SpreadsheetFileNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.multipart.MultipartFile;
import repo.IDataTransferObjectRepository;
import model.SpreadsheetFileDataTransferObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.SpreadsheetFileMapper;
import utils.SpreadsheetFileModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpreadsheetFilesRestController {
    private IDataTransferObjectRepository repository;
    private SpreadsheetFileModelAssembler assembler;

    private SpreadsheetFilesRestController() {}

    public SpreadsheetFilesRestController(
            IDataTransferObjectRepository repository,
            SpreadsheetFileModelAssembler assembler)
    {
        this.repository = repository;
        this.assembler = assembler;
    }

    //AGGREGATE ROOT
    @GetMapping(value = "/spreadsheet-files")
    public CollectionModel<EntityModel<SpreadsheetFileDataTransferObject>> all() {
        List<EntityModel<SpreadsheetFileDataTransferObject>> spreadsheetFiles =
                repository.findAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(spreadsheetFiles,
                linkTo(methodOn(SpreadsheetFilesRestController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/spreadsheet-files")
    public ResponseEntity<?> importFile(@RequestParam("file") final MultipartFile file) {
        SpreadsheetFileDataTransferObject spreadsheetFileDataTransferObject =
                (SpreadsheetFileDataTransferObject) repository.save(SpreadsheetFileMapper.mapMultipartFile(file)
                                             .orElseThrow(() -> new EmptyMultipartFileException("File is empty")));

        EntityModel<SpreadsheetFileDataTransferObject> entityModel =
            assembler.toModel(spreadsheetFileDataTransferObject);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    //SINGLE FILE INFO
    @GetMapping("/spreadsheet-files/{id}")
    public EntityModel<SpreadsheetFileDataTransferObject> one(@PathVariable Long id) {
        SpreadsheetFileDataTransferObject spreadsheetFileDataTransferObject =
                (SpreadsheetFileDataTransferObject) repository.findById(id)
                        .orElseThrow(() -> new SpreadsheetFileNotFoundException(id));

        return assembler.toModel(spreadsheetFileDataTransferObject);
    }

    //
    @PutMapping("/spreadsheet-files/{id}")
    public ResponseEntity<?> changeFileName(@RequestParam String newName, @PathVariable Long id) {
        SpreadsheetFileDataTransferObject spreadsheetFileDataTransferObject =
                (SpreadsheetFileDataTransferObject) repository.findById(id)
                        .orElseThrow(() -> new SpreadsheetFileNotFoundException(id));

        EntityModel<SpreadsheetFileDataTransferObject> entityModel =
                assembler.toModel(new SpreadsheetFileDataTransferObject(
                        id,
                        newName,
                        spreadsheetFileDataTransferObject.fileContent(),
                        spreadsheetFileDataTransferObject.getExtension()
                ));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //
    @DeleteMapping("/spreadsheet-files/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    //SINGLE FILE DOWNLOAD
    @GetMapping("/spreadsheet-files/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        SpreadsheetFileDataTransferObject spreadsheetFileDataTransferObject =
                (SpreadsheetFileDataTransferObject) repository.findById(id)
                .orElseThrow(() -> new SpreadsheetFileNotFoundException(id));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.setContentDispositionFormData(
                "attachment",
                spreadsheetFileDataTransferObject.getName());

        return new ResponseEntity<>(
                spreadsheetFileDataTransferObject.fileContent(),
                headers,
                HttpStatus.OK
        );
    }
}