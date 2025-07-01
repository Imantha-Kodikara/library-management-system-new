package repository.custom.impl;

import com.sun.source.tree.BreakTree;
import model.entity.BookEntity;
import repository.CrudRepository;
import repository.custom.BookRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    @Override
    public Boolean add(BookEntity entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO books (book_title, author, isbn, category, no_of_copies) VALUES(?, ?, ?, ?, ?)",
                entity.getTitle(), entity.getAuthor(), entity.getIsbn(), entity.getCategory(), entity.getNoOfCopies()
                );
    }

    @Override
    public Boolean update(BookEntity entity) throws SQLException {
        return CrudUtil.execute("UPDATE books SET book_title = ?, author = ?,  category = ?, no_of_copies = ? WHERE isbn = ?",
                entity.getTitle(), entity.getAuthor(),  entity.getCategory(), entity.getNoOfCopies(), entity.getIsbn()
                );
    }

    @Override
    public Boolean deleteById(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM books WHERE id = ?", id);
    }

    @Override
    public BookEntity searchById(Integer id) throws SQLException {
       return null;
    }

    @Override
    public List<BookEntity> getAll() throws SQLException {
        List<BookEntity> bookEntities = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("SELECT * FROM books");

            while (resultSet.next()) {
                BookEntity bookEntity = new BookEntity(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6)
                );
                bookEntities.add(bookEntity);
            }
            return bookEntities;
    }

    @Override
    public Integer getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(id) FROM books"); //Refining  the highest(maximum) value in the id column from the members table
        if(!resultSet.next()){
            return 1;
        }else{
            int maxId = resultSet.getInt(1);
            return maxId + 1;
        }
    }

    @Override
    public Boolean isBookRegistered(String isbn) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM books WHERE isbn = ?", isbn); //If found already registered book isbn, return true
        return resultSet.next();
    }

    @Override
    public BookEntity searchByIsbn(String isbn) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM books WHERE isbn = ?", isbn);
        if(resultSet.next()){
            return new BookEntity(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getInt(6)
            );
        }
        return null;
    }

    @Override
    public Boolean reduceBookAvailableCopies(Integer bookId) throws SQLException {
        return CrudUtil.execute("UPDATE books SET no_of_copies = no_of_copies - 1 WHERE id = ? AND no_of_copies > 0",bookId);
    }

    @Override
    public Boolean IncreaseBookAvailableCopies(Integer bookId) throws SQLException {
        return CrudUtil.execute("UPDATE books SET no_of_copies = no_of_copies + 1 WHERE id = ? AND no_of_copies > 0",bookId);
    }
}
