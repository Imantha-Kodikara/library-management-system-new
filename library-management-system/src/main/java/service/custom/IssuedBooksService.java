package service.custom;

import model.dto.IssuedBookDTO;
import service.SuperService;

import java.sql.SQLException;

public interface IssuedBooksService extends SuperService {
    Boolean isIssued(IssuedBookDTO issuedBook) throws SQLException;
}
