package interfaces;

import java.util.List;

public interface Repository<T> {
  List<T> getAll();

  T findById(int id);

  T save(T entity);

  void delete(int id);
}
