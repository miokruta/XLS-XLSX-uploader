package utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import controller.SpreadsheetFilesRestController;
import model.SpreadsheetFileDataTransferObject;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class SpreadsheetFileModelAssembler implements RepresentationModelAssembler<SpreadsheetFileDataTransferObject, EntityModel<SpreadsheetFileDataTransferObject>> {

    @Override
    public EntityModel<SpreadsheetFileDataTransferObject> toModel(SpreadsheetFileDataTransferObject entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SpreadsheetFilesRestController.class).getFile(entity.getId())).withSelfRel(),
                linkTo(methodOn(SpreadsheetFilesRestController.class).all()).withRel("spreadsheet-files")
                );
    }
}
