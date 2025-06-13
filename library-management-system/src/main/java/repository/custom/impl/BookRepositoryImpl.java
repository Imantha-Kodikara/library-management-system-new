package repository.custom.impl;

import model.entity.BookEntity;
import repository.custom.BookRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    @Override
    public Boolean add(BookEntity entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean update(BookEntity entity) throws SQLException {
        return null;
    }

    @Override
    public Boolean deleteById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public BookEntity searchById(Integer integer) throws SQLException {
        return null;
    }

    @Override
    public List<BookEntity> getAll() {
        return List.of();
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
}
