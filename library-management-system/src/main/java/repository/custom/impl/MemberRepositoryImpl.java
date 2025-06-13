package repository.custom.impl;

import model.entity.MemberEntity;
import repository.custom.MemberRepository;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepository {
    @Override
    public Boolean add(MemberEntity entity) throws SQLException {
        return CrudUtil.execute("INSERT INTO members (first_name, last_name, address, email, contact_number, nic, membership_date) VALUES(?, ?, ?, ?, ?, ?, ?)",
                entity.getFirstName(), entity.getLastName(), entity.getAddress(), entity.getEmail(), entity.getContactNumber(),
                entity.getNic(), entity.getMembershipDate()
                );
    }

    @Override
    public Boolean update(MemberEntity entity) throws SQLException {
       return CrudUtil.execute("UPDATE members SET first_name = ?, last_name = ?, address = ?, email = ?, contact_number = ?, nic = ?, membership_date = ? WHERE id = ?",
                entity.getFirstName(), entity.getLastName(), entity.getAddress(), entity.getEmail(), entity.getContactNumber(), entity.getNic(), entity.getMembershipDate(), entity.getId()
                );
    }

    @Override
    public Boolean deleteById(Integer id) throws SQLException {
        return CrudUtil.execute("DELETE FROM members WHERE id = ?", id);
    }

    @Override
    public MemberEntity searchById(Integer id) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM members WHERE id = ?", id);
        if(resultSet.next()){
            return new MemberEntity(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getDate(8).toLocalDate()
            );
        }
        return null;
    }

    @Override
    public List<MemberEntity> getAll() {
        return null;
    }

    @Override
    public Integer getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(id) FROM members"); //Refining  the highest(maximum) value in the membership_id column from the members table
        if(resultSet.next()){
            int maxId = resultSet.getInt(1);
            return maxId + 1;
        }else{
            return 1;
        }
    }

    @Override
    public Boolean isMemberRegistered(String nic) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM members WHERE nic = ?", nic); //If found already registered member NIC, return true
        return resultSet.next();
    }
}
