package services;

import java.util.List;

import model.Patient;
import model.exception.EntityAlreadyExistsException;
import model.exception.EntityNotFoundException;
import repositories.PatientRepository;

public class PatientService {

  private PatientRepository repository;

  public PatientService(PatientRepository repository) {
    this.repository = repository;
  }

  public List<Patient> getAll() {
    return repository.getAll();
  }

  public Patient findById(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid ID. ");
    }

    Patient patient = repository.findById(id);

    if (patient == null) {
      throw new EntityNotFoundException("Patient not found.");
    }
    return patient;
  }

  public void save(Patient patient) {
    if (patient == null) {
      throw new IllegalArgumentException("Patient cannot be null.");
    }

    var patients = getAll();

    for (var p : patients) {
      if (p.getEmail().equalsIgnoreCase(patient.getEmail())) {
        throw new EntityAlreadyExistsException("Email already registered.");
      }
    }
    repository.save(patient);
  }

  public void delete(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid ID. ");
    }

    Patient patient = repository.findById(id);

    if (patient == null) {
      throw new EntityNotFoundException("Patient not found.");
    }
    repository.delete(patient.getId());
  }
}
