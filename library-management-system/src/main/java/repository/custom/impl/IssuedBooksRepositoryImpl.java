package repository.custom.impl;

import db.DBConnection;
import model.entity.IssuedBookEntity;
import repository.DaoFactory;
import repository.custom.BookRepository;
import repository.custom.IssuedBooksRepository;
import util.RepositoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class IssuedBooksRepositoryImpl implements IssuedBooksRepository {
    @Override
    public Boolean add(IssuedBookEntity entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(IssuedBookEntity entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean deleteById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public IssuedBookEntity searchById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public List<IssuedBookEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Integer getNextId() throws SQLException {
        return 0;
    }

    public Boolean isIssued(IssuedBookEntity issuedBook) throws SQLException {
        BookRepository bookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.BOOK);

        String sql = "INSERT INTO issued_books (member_id, book_id, issued_date) VALUES (?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            PreparedStatement psTm = connection.prepareStatement(sql);
            psTm.setObject(1, issuedBook.getMemberId());
            psTm.setObject(2, issuedBook.getBookId());
            psTm.setObject(3, issuedBook.getIssuedDate());

            Boolean isIssuedBookAdded = psTm.executeUpdate() > 0;

            if(isIssuedBookAdded){
                Boolean isUpdated = bookRepository.updateBookAvailableCopies(issuedBook.getBookId());

                if(isUpdated){
                    return true;
                }
                return false;
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(true);
        }

    }
}
