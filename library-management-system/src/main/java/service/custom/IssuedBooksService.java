package service.custom;

import javafx.collections.ObservableList;
import model.dto.IssuedBookDTO;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface IssuedBooksService extends SuperService {
    Boolean isIssued(IssuedBookDTO issuedBook) throws SQLException;
    ObservableList<Integer> getIssuedBooksMembersId() throws SQLException;
    List<Integer> getIssuedBooksIds(Integer memberId) throws SQLException;
    ObservableList<String> getIssuedBooksTitles(Integer memberId) throws SQLException;
    List<IssuedBookDTO> getAll() throws SQLException;
    IssuedBookDTO getIssuedBook(Integer memberId, String bookTitle) throws SQLException;
}
