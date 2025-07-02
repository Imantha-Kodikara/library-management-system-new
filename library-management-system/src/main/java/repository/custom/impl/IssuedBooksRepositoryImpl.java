package repository.custom.impl;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.entity.IssuedBookEntity;
import repository.DaoFactory;
import repository.custom.BookRepository;
import repository.custom.IssuedBooksRepository;
import util.CrudUtil;
import util.RepositoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
        List<IssuedBookEntity> issuedBookEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM issued_books");

        while (resultSet.next()) {
            Date issuedDateSql = resultSet.getDate(4);
            LocalDate issuedDate = issuedDateSql != null ? ((java.sql.Date) issuedDateSql).toLocalDate() : null; //The fix is to check for null before converting with .toLocalDate().

            Date returnedDateSql = resultSet.getDate(5);
            LocalDate returnedDate = returnedDateSql != null ? ((java.sql.Date) returnedDateSql).toLocalDate() : null; //The fix is to check for null before converting with .toLocalDate().

            issuedBookEntities.add(new IssuedBookEntity(
                    resultSet.getInt(1),
                    resultSet.getInt(2),
                    resultSet.getInt(3),
                    issuedDate,
                    returnedDate,
                    resultSet.getDouble(6),
                    resultSet.getString(7)
            ));
        }
        return issuedBookEntities;
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
                Boolean isUpdated = bookRepository.reduceBookAvailableCopies(issuedBook.getBookId());

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

    public ObservableList<Integer>getIssuedBooksMembersId() throws SQLException {
        ObservableList<Integer> issuedMembersId = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.execute("SELECT DISTINCT member_id FROM issued_books WHERE status = 'Issued'"); //SELECT DISTINCT ensures each member_id appears only once, even if multiple books are issued.
        while (resultSet.next()){
            Integer memberId = resultSet.getInt(1);
            issuedMembersId.add(memberId);
        }

        return issuedMembersId;
    }

    public List<Integer> getIssuedBooksIds(Integer memberId) throws SQLException {
        List<Integer> issuedBooksIds = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("SELECT book_id FROM issued_books WHERE member_id = ? AND status = ?", memberId, "Issued");
        while (resultSet.next()){
            Integer bookId = resultSet.getInt(1);
            issuedBooksIds.add(bookId);
        }
        return issuedBooksIds;
    }

    @Override
    public Integer getTotalIssuedBooks() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT COUNT(*) FROM issued_books WHERE status = 'issued'");
        if (resultSet.next()){
            return resultSet.getInt(1);
        }else{
            return 0;
        }
    }

    @Override
    public Integer getTotalFine() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT SUM(fine) FROM issued_books");
        if(resultSet.next()){
            return resultSet.getInt(1);
        }else{
            return 0;
        }
    }
}
