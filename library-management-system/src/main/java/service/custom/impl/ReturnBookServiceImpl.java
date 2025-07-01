package service.custom.impl;

import model.dto.ReturnBookDTO;
import model.dto.ReturnBookEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.ReturnBookRepository;
import service.custom.BookService;
import service.custom.ReturnBookService;
import util.RepositoryType;

import java.sql.SQLException;

public class ReturnBookServiceImpl implements ReturnBookService {

    ReturnBookRepository returnBookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.RETURNBOOK);

    @Override
    public Boolean update(ReturnBookDTO returnBookDTO) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        ReturnBookEntity returnBookEntity = mapper.map(returnBookDTO, ReturnBookEntity.class);
        return returnBookRepository.update(returnBookEntity);
    }
}
