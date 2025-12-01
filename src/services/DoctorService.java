package services;

import java.util.List;

import model.Doctor;
import repositories.DoctorRepository;

public class DoctorService extends BaseService<Doctor> implements EmailValidatableService<Doctor> {

  private DoctorRepository repository;

  public DoctorService(DoctorRepository repository) {
    super(Doctor.class);
    this.repository = repository;
  }

  @Override
  protected List<Doctor> getFromRepositoryAll() {
    return repository.getAll();
  }

  @Override
  protected Doctor getFromRepositoryById(int id) {
    return repository.findById(id);
  }

  @Override
  protected void saveToRepository(Doctor entity) {
    validateEmail(getAll(), entity);
    repository.save(entity);
  }

  @Override
  protected void deleteFromRepository(int id) {
    repository.delete(id);
  }
}
