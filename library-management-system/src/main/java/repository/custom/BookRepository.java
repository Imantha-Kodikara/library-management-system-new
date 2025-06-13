package repository.custom;

import model.entity.BookEntity;
import repository.CrudRepository;

import java.sql.SQLException;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    Boolean isBookRegistered(String isbn) throws SQLException;
}
