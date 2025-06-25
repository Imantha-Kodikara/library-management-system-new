package service.custom;

import javafx.collections.ObservableList;
import model.dto.BookDTO;
import model.dto.MemberDTO;
import service.SuperService;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface BookService extends SuperService {
    Boolean addBook(BookDTO book) throws SQLException;
    Integer generateBookId() throws SQLException;
    Boolean isBookRegistered(String isbn) throws SQLException;
    BookDTO searchByIsbn(String isbn) throws SQLException;
    Boolean update(BookDTO book) throws SQLException;
    Boolean deleteById(Integer id) throws SQLException;
    List<BookDTO> getAll() throws SQLException;
    ObservableList<String> getAllBookTitles() throws SQLException;
    BookDTO findBookByTitle(String bookTitle) throws SQLException;


}
