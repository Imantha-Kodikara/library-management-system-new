package repository.custom;

import model.entity.BookEntity;
import repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {
}
