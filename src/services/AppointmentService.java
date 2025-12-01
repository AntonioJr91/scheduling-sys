package services;

import java.util.List;

import model.Appointment;
import model.domainService.AppointmentDomainService;
import repositories.AppointmentRepository;

public class AppointmentService extends BaseService<Appointment> {

  private AppointmentRepository repository;

  public AppointmentService(AppointmentRepository repository) {
    super(Appointment.class);
    this.repository = repository;
  }

  @Override
  protected List<Appointment> getFromRepositoryAll() {
    return repository.getAll();
  }

  @Override
  protected Appointment getFromRepositoryById(int id) {
    return repository.findById(id);
  }

  @Override
  protected void saveToRepository(Appointment entity) {
    AppointmentDomainService.validateNoTimeConflict(entity, getAll());
    repository.save(entity);
  }

  @Override
  protected void deleteFromRepository(int id) {
    repository.delete(id);
  }

}
