package tests.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Patient;
import repositories.PatientRepository;

class PatientRepositoryTest {

  private PatientRepository repo;
  private Patient p1;
  private Patient p2;

  @BeforeEach
  void setUp() {
    repo = new PatientRepository();
    p1 = new Patient("John Doe", "john@example.com", LocalDate.of(1990, 5, 10));
    p2 = new Patient("Jane Doe", "jane@example.com", LocalDate.of(1992, 7, 12));
  }

  @Test
  @DisplayName("Should save and retrieve patient by ID")
  void shouldSaveAndRetrieve() {
    repo.save(p1);
    Patient result = repo.findById(p1.getId());

    assertNotNull(result);
    assertEquals(p1.getId(), result.getId());
    assertEquals(p1.getEmail(), result.getEmail());
  }

  @Test
  @DisplayName("Should return null when patient not found")
  void shouldReturnNullWhenNotFound() {
    assertNull(repo.findById(999));
  }

  @Test
  @DisplayName("Should update patient instead of duplicating")
  void shouldUpdateExistingPatient() {
    repo.save(p1);
    p1.updateEmail("updated@example.com");
    repo.save(p1);

    assertEquals(1, repo.getAll().size());
    assertEquals("updated@example.com",
        repo.findById(p1.getId()).getEmail());
  }

  @Test
  @DisplayName("getAll should return a copy of internal list")
  void shouldReturnCopyInGetAll() {
    repo.save(p1);
    var listCopy = repo.getAll();
    listCopy.clear();

    assertEquals(1, repo.getAll().size());
  }

  @Test
  @DisplayName("Should delete a patient by ID")
  void shouldDeleteById() {
    repo.save(p1);
    repo.save(p2);

    repo.delete(p1.getId());

    assertNull(repo.findById(p1.getId()));
    assertEquals(1, repo.getAll().size());
  }
}
