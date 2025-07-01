package repository.custom.impl;

import db.DBConnection;
import model.dto.ReturnBookEntity;
import repository.DaoFactory;
import repository.custom.BookRepository;
import repository.custom.ReturnBookRepository;
import util.RepositoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReturnBookRepositoryImpl implements ReturnBookRepository {
    @Override
    public Boolean add(ReturnBookEntity entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(ReturnBookEntity entity) throws SQLException {
        BookRepository bookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.BOOK);

        String sql = "UPDATE issued_books SET returned_date = ?, fine = ?, status = ? WHERE issued_id = ?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement psTm = connection.prepareStatement(sql);
            psTm.setObject(1, entity.getReturnedDate());
            psTm.setObject(2, entity.getFine());
            psTm.setObject(3, entity.getStatus());
            psTm.setObject(4, entity.getIssuedId());

            Boolean isUpdateIssuedTable = psTm.executeUpdate() > 0;

            if(isUpdateIssuedTable){
                Boolean isIncreasedBooksCopies = bookRepository.IncreaseBookAvailableCopies(entity.getBookId());
                if(isIncreasedBooksCopies){
                    return true;
                }
                return false;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Boolean deleteById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public ReturnBookEntity searchById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public List<ReturnBookEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Integer getNextId() throws SQLException {
        return 0;
    }
}
