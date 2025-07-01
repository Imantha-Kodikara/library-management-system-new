package repository.custom;

import javafx.collections.ObservableList;
import model.entity.IssuedBookEntity;
import repository.CrudRepository;
import repository.SuperRepository;

import java.sql.SQLException;
import java.util.List;

public interface IssuedBooksRepository extends CrudRepository<IssuedBookEntity, Integer> {
    Boolean isIssued(IssuedBookEntity issuedBook) throws SQLException;
    ObservableList<Integer> getIssuedBooksMembersId() throws SQLException;
    List<Integer> getIssuedBooksIds(Integer memberId) throws SQLException;

}
