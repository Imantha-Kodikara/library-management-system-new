package service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.MemberDTO;
import model.entity.MemberEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.MemberRepository;
import service.custom.MemberService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberServiceImpl implements MemberService {
    MemberRepository memberRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.MEMBER); //creating reference from member repository(De-coupling)
    @Override
    public Boolean addMember(MemberDTO member) throws SQLException {
        if(!isMemberRegistered(member.getNic())){
            ModelMapper mapper = new ModelMapper();
            MemberEntity memberEntity = mapper.map(member, MemberEntity.class);
            return memberRepository.add(memberEntity);
        }
        return false;
    }

    @Override
    public Boolean isMemberRegistered(String nic) throws SQLException {
        return memberRepository.isMemberRegistered(nic);
    }

    @Override
    public Integer generateMemberId() throws SQLException {
        return memberRepository.getNextId();
    }

    @Override
    public MemberDTO searchById(Integer id) throws SQLException {
        MemberEntity entity = memberRepository.searchById(id);
        if(entity==null){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        MemberDTO member = mapper.map(entity, MemberDTO.class);
        return member;
    }

    @Override
    public Boolean update(MemberDTO member) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        MemberEntity entity = mapper.map(member, MemberEntity.class);
        return memberRepository.update(entity);
    }

    @Override
    public Boolean deleteById(Integer id) throws SQLException {
       return memberRepository.deleteById(id);
    }

    @Override
    public List<MemberDTO> getAll() throws SQLException {
        List<MemberEntity> memberEntities = memberRepository.getAll();
        List<MemberDTO> memberDTOS = new ArrayList<>();
            memberEntities.forEach(entity -> {
                ModelMapper mapper = new ModelMapper();
                MemberDTO member =  mapper.map(entity, MemberDTO.class);

                memberDTOS.add(member);
            });
            return memberDTOS;
    }

    public ObservableList<Integer> getMembersId() throws SQLException {
        List<MemberDTO> memberDTOS = getAll(); //calling above method
        ObservableList<Integer> observableList = FXCollections.observableArrayList();

        for (MemberDTO memberDTO : memberDTOS){
            observableList.add(memberDTO.getId());
        }
        return observableList;
    }

    public Integer findBorrowedBooksCount(Integer id) throws SQLException {
        return memberRepository.findBorrowedBooksCount(id);
    }

    @Override
    public Integer getTotalMembers() throws SQLException {
        return memberRepository.getTotalMembers();
    }
}
