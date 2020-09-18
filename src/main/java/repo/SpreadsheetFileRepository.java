package repo;

import domain.SpreadsheetFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpreadsheetFileRepository extends JpaRepository<SpreadsheetFile, Long> {
}
