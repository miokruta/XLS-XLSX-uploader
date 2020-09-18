package service;

import domain.SpreadsheetFile;
import org.springframework.data.annotation.Immutable;
import org.springframework.stereotype.Service;
import repo.SpreadsheetFileRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Immutable
public class SpreadsheetFileService {
    private final SpreadsheetFileRepository repository;

    private SpreadsheetFileService() {
        throw new AssertionError();
    }

    public SpreadsheetFileService(SpreadsheetFileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Optional<SpreadsheetFile> save(SpreadsheetFile spreadsheetFile) {
        return Optional.of(repository.save(spreadsheetFile));
    }

    public List<SpreadsheetFile> listAll() {
        return repository.findAll();
    }

    public Optional<SpreadsheetFile> get(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
