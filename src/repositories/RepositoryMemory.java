package repositories;

import java.util.ArrayList;
import java.util.List;

import interfaces.HasId;
import interfaces.Repository;

public class RepositoryMemory<T extends HasId> implements Repository<T> {

  protected final List<T> storage = new ArrayList<>();

  @Override
  public List<T> getAll() {
    return new ArrayList<>(storage);
  }

  @Override
  public T findById(int id) {
    return storage.stream()
        .filter(e -> e.getId() == id).findFirst().orElse(null);
  }

  @Override
  public T save(T entity) {
    storage.removeIf(e -> e.getId() == entity.getId());
    storage.add(entity);
    return entity;
  }

  @Override
  public void delete(int id) {
    storage.removeIf(e -> e.getId() == id);
  }
}
