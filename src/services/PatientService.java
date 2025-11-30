package services;

import java.util.List;

import model.Patient;
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
      throw new RuntimeException();
    }
    return patient;
  }

  public void save(Patient patient) {
    if (patient == null) {
      throw new NullPointerException("Patient cannot be null.");
    }
    var patients = getAll();
    for (var p : patients) {
      if (p.getEmail().equalsIgnoreCase(patient.getEmail())) {
        throw new RuntimeException();
      }
    }
    repository.save(patient);
  }

  public void delete(int id) {
    Patient patient = repository.findById(id);
    if (patient == null) {
      throw new NullPointerException("Patient cannot be null.");
    }
    repository.delete(patient.getId());
  }

}
