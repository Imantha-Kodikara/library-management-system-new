package repository;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository <T, ID> extends SuperRepository{
    Boolean add(T entity) throws SQLException;
    Boolean update(T entity) throws SQLException;
    Boolean deleteById(ID id);
    T searchById(ID id) throws SQLException;
    List <T> getAll();
    Integer getNextId() throws SQLException;
}
