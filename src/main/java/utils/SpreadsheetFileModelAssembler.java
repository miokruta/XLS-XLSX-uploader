package utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import controller.SpreadsheetFilesRestController;
import domain.SpreadsheetFile;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * Part of Spring HATEOAS implementation.
 */
public class SpreadsheetFileModelAssembler implements RepresentationModelAssembler<SpreadsheetFile, EntityModel<SpreadsheetFile>> {

    @Override
    public EntityModel<SpreadsheetFile> toModel(SpreadsheetFile entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(SpreadsheetFilesRestController.class).getFile(entity.getId())).withSelfRel(),
                linkTo(methodOn(SpreadsheetFilesRestController.class).all()).withRel("spreadsheet-files")
                );
    }
}
