package repositories;

import java.lang.reflect.InvocationTargetException;
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
            return hasSameIdAs(id, e);
          } catch (Exception ex) {
            identityNotAccessible();
            return false;
          }
        })
        .findFirst()
        .orElse(null);
  }

  @Override
  public T save(T entity) {
    storage.removeIf(e -> {
      try {
        return hasSameIdentityAs(entity, e);
      } catch (Exception ex) {
        identityNotAccessible();
        return false;
      }
    });

    storage.add(entity);
    return entity;
  }

  @Override
  public void delete(int id) {
    storage.removeIf(e -> {
      try {
        return hasSameIdAs(id, e);
      } catch (Exception ex) {
        identityNotAccessible();
        return false;
      }
    });
  }

  private boolean hasSameIdAs(int id, T e)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    return (int) e.getClass()
        .getMethod("getId")
        .invoke(e) == id;
  }

  private boolean hasSameIdentityAs(T entity, T e)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    return (int) e.getClass()
        .getMethod("getId")
        .invoke(e) == (int) entity.getClass()
            .getMethod("getId")
            .invoke(entity);
  }

  private RuntimeException identityNotAccessible() {
    return new RuntimeException("Entity must have getId() method");
  }
}
