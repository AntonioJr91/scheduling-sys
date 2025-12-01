package services;

import java.util.List;

import model.exception.EntityNotFoundException;

public abstract class BaseService<T> {
  protected final Class<T> entityClass;

  public BaseService(Class<T> entityClass) {
    this.entityClass = entityClass;
  }

  protected abstract List<T> getFromRepositoryAll();

  protected abstract T getFromRepositoryById(int id);

  protected abstract void saveToRepository(T entity);

  protected abstract void deleteFromRepository(int id);

  public List<T> getAll() {
    return getFromRepositoryAll();
  }

  public T findById(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid ID. ");
    }

    T entity = getFromRepositoryById(id);

    if (entity == null) {
      throw new EntityNotFoundException(String.format("%s not found.", entityClass.getSimpleName()));
    }
    return entity;
  }

  public void save(T entity) {
    if (entity == null) {
      throw new IllegalArgumentException(String.format("%s cannot be null.", entityClass.getSimpleName()));
    }

    saveToRepository(entity);
  }

  public void delete(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Invalid ID. ");
    }

    findById(id);
    deleteFromRepository(id);
  }
}
