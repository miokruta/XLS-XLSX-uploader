package repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpreadsheetFileRepository extends JpaRepository<SpreadsheetFileDataTransferObject, Long> {

}
