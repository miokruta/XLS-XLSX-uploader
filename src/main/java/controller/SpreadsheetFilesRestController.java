package controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import domain.SpreadsheetFile;
import exception.SpreadsheetFileNotFoundException;
import org.apache.poi.EmptyFileException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.SpreadsheetFileService;
import utils.SpreadsheetFileMapper;
import utils.SpreadsheetFileModelAssembler;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpreadsheetFilesRestController {
    private SpreadsheetFileService service;
    private SpreadsheetFileModelAssembler assembler;

    private SpreadsheetFilesRestController() {}

    public SpreadsheetFilesRestController(
            SpreadsheetFileService service,
            SpreadsheetFileModelAssembler assembler)
    {
        this.service = service;
        this.assembler = assembler;
    }

    //AGGREGATE ROOT
    @GetMapping(value = "/spreadsheet-files")
    public CollectionModel<EntityModel<SpreadsheetFile>> all() {
        List<EntityModel<SpreadsheetFile>> spreadsheetFiles =
                service.listAll().stream()
                        .map(assembler::toModel)
                        .collect(Collectors.toList());

        return CollectionModel.of(spreadsheetFiles,
                linkTo(methodOn(SpreadsheetFilesRestController.class).all()).withSelfRel());
    }

    @PostMapping(value = "/spreadsheet-files")
    public ResponseEntity<?> importFile(@RequestParam("file") final MultipartFile file) {
        SpreadsheetFile spreadsheetFile = service.save(SpreadsheetFileMapper.mapMultipartFile(file))
                .orElseThrow(() -> new EmptyFileException());

        EntityModel<SpreadsheetFile> entityModel =
            assembler.toModel(spreadsheetFile);

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    //SINGLE FILE INFO
    @GetMapping("/spreadsheet-files/{id}")
    public EntityModel<SpreadsheetFile> one(@PathVariable Long id) {
        SpreadsheetFile spreadsheetFile = service.get(id)
                        .orElseThrow(() -> new SpreadsheetFileNotFoundException(id));

        return assembler.toModel(spreadsheetFile);
    }

    //
    @PutMapping("/spreadsheet-files/{id}")
    public ResponseEntity<?> changeFileName(@RequestParam String newName, @PathVariable Long id) {
        SpreadsheetFile spreadsheetFile = service.get(id)
                        .orElseThrow(() -> new SpreadsheetFileNotFoundException(id));

        EntityModel<SpreadsheetFile> entityModel =
                assembler.toModel(new SpreadsheetFile(
                        id,
                        newName,
                        spreadsheetFile.getContent(),
                        spreadsheetFile.getExtension()
                ));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //
    @DeleteMapping("/spreadsheet-files/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    //SINGLE FILE DOWNLOAD
    @GetMapping("/spreadsheet-files/download/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        SpreadsheetFile spreadsheetFileDataTransferObject = service.get(id)
                .orElseThrow(() -> new SpreadsheetFileNotFoundException(id));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/vnd.ms-excel"));
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        headers.setContentDispositionFormData(
                "attachment",
                spreadsheetFileDataTransferObject.getName());

        return new ResponseEntity<>(
                spreadsheetFileDataTransferObject.getContent(),
                headers,
                HttpStatus.OK
        );
    }
}