package service.custom;

import model.dto.MemberDTO;
import service.SuperService;

import java.sql.SQLException;

public interface MemberService extends SuperService {
    Boolean addMember(MemberDTO member) throws SQLException;

    Boolean isMemberRegistered(String nic) throws SQLException;

    Integer generateMemberId() throws SQLException;

    MemberDTO searchById(Integer id) throws SQLException;

    Boolean update(MemberDTO member) throws SQLException;
}