package service.custom;

import model.dto.BookDTO;
import model.dto.MemberDTO;
import service.SuperService;

import java.sql.SQLException;

public interface BookService extends SuperService {
    Boolean addBook(BookDTO book) throws SQLException;
    Integer generateBookId() throws SQLException;
    Boolean isBookRegistered(String isbn) throws SQLException;
    BookDTO searchByIsbn(String isbn) throws SQLException;
    Boolean update(BookDTO book) throws SQLException;

}
