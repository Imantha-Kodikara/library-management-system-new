package service.custom;

import javafx.collections.ObservableList;
import model.dto.MemberDTO;
import service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface MemberService extends SuperService {
    Boolean addMember(MemberDTO member) throws SQLException;

    Boolean isMemberRegistered(String nic) throws SQLException;

    Integer generateMemberId() throws SQLException;

    MemberDTO searchById(Integer id) throws SQLException;

    Boolean update(MemberDTO member) throws SQLException;

    Boolean deleteById(Integer id) throws SQLException;

    List<MemberDTO> getAll() throws SQLException;

    ObservableList<Integer> getMembersId() throws SQLException;

    Integer findBorrowedBooksCount(Integer ID) throws SQLException;

    Integer getTotalMembers() throws SQLException;
}