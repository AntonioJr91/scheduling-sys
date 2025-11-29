package repositories;

import java.util.ArrayList;
import java.util.List;

import interfaces.Repository;

public class RepositoryMemory<T> implements Repository<T> {

  protected final List<T> storage = new ArrayList<>();

  @Override
  public List<T> getAll() {
    return new ArrayList<>(storage);
  }

  @Override
  public T findById(int id) {
    return storage.stream()
        .filter(e -> {
          try {
            return (int) e.getClass()
                .getMethod("getId")
                .invoke(e) == id;
          } catch (Exception ex) {
            throw new RuntimeException("Entity must have getId() method");
          }
        })
        .findFirst()
        .orElse(null);
  }

  @Override
  public T save(T entity) {
    storage.removeIf(e -> {
      try {
        return (int) e.getClass()
            .getMethod("getId")
            .invoke(e) == (int) entity.getClass()
                .getMethod("getId")
                .invoke(entity);
      } catch (Exception ex) {
        throw new RuntimeException("Entity must have getId() method");
      }
    });

    storage.add(entity);
    return entity;
  }

  @Override
  public void delete(int id) {
    storage.removeIf(e -> {
      try {
        return (int) e.getClass()
            .getMethod("getId")
            .invoke(e) == id;
      } catch (Exception ex) {
        throw new RuntimeException("Entity must have getId() method");
      }
    });
  }

}
