package service.custom;

import model.dto.ReturnBookDTO;
import service.SuperService;

import java.sql.SQLException;

public interface ReturnBookService extends SuperService {
    Boolean update(ReturnBookDTO returnBookDTO) throws SQLException;
}
