package repository.custom;

import model.entity.IssuedBookEntity;
import repository.CrudRepository;
import repository.SuperRepository;

import java.sql.SQLException;

public interface IssuedBooksRepository extends CrudRepository<IssuedBookEntity, Integer> {
    Boolean isIssued(IssuedBookEntity issuedBook) throws SQLException;

}
