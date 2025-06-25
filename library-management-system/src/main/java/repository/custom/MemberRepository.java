package repository.custom;

import model.entity.MemberEntity;
import repository.CrudRepository;

import java.sql.SQLException;

public interface MemberRepository extends CrudRepository<MemberEntity, Integer> {
    Boolean isMemberRegistered(String nic) throws SQLException;
    Integer findBorrowedBooksCount(Integer id) throws SQLException;

}
