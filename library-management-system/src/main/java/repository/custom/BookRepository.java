package repository.custom;

import model.entity.BookEntity;
import repository.CrudRepository;

import java.sql.SQLException;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {
    Boolean isBookRegistered(String isbn) throws SQLException;
    BookEntity searchByIsbn(String isbn) throws SQLException;
    Boolean reduceBookAvailableCopies(Integer bookId) throws SQLException;
    Boolean IncreaseBookAvailableCopies(Integer bookId) throws SQLException;
    Integer getTotalBooks() throws SQLException;
}
