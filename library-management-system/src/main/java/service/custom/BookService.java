package service.custom;

import model.dto.BookDTO;
import service.SuperService;

import java.sql.SQLException;

public interface BookService extends SuperService {
    Boolean addBook(BookDTO book) throws SQLException;
    Integer generateBookId() throws SQLException;

    Boolean isBookRegistered(String isbn) throws SQLException;
}
