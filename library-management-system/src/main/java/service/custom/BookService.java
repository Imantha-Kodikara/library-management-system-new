package service.custom;

import model.dto.BookDTO;
import service.SuperService;

import java.sql.SQLException;

public interface BookService extends SuperService {
    Boolean addBook(BookDTO book);
    Integer generateBookId() throws SQLException;
}
