package services;

import java.util.List;

import model.Patient;
import repositories.PatientRepository;

public class PatientService extends BaseService<Patient> implements EmailValidatableService<Patient> {

  private PatientRepository repository;

  public PatientService(PatientRepository repository) {
    super(Patient.class);
    this.repository = repository;
  }

  @Override
  protected List<Patient> getFromRepositoryAll() {
    return repository.getAll();
  }
  
  @Override
  protected Patient getFromRepositoryById(int id) {
    return repository.findById(id);
  }
  
  @Override
  protected void saveToRepository(Patient patient) {
    validateEmail(getAll(), patient);
    repository.save(patient);
  }
  
  @Override
  protected void deleteFromRepository(int id) {
    repository.delete(id);
  }
}