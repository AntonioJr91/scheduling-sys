package services;

import java.util.List;

import model.Patient;
import repositories.PatientRepository;

public class PatientService extends BaseService<Patient> {

  private PatientRepository repository;

  public PatientService(PatientRepository repository) {
    super(Patient.class);
    this.repository = repository;
  }

  protected List<Patient> getFromRepositoryAll() {
    return repository.getAll();
  }

  protected Patient getFromRepositoryById(int id) {
    return repository.findById(id);
  }

  protected void saveToRepository(Patient patient) {
    repository.save(patient);
  }

  protected void deleteFromRepository(int id) {
    repository.delete(id);
  }
}