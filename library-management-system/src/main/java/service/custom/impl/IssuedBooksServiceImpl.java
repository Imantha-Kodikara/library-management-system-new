package service.custom.impl;

import model.dto.IssuedBookDTO;
import model.entity.IssuedBookEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.IssuedBooksRepository;
import service.custom.BookService;
import service.custom.IssuedBooksService;
import util.RepositoryType;

import java.sql.SQLException;


public class IssuedBooksServiceImpl implements IssuedBooksService {
    IssuedBooksRepository issuedBooksRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.ISSUEDBOOK);

    public Boolean isIssued(IssuedBookDTO issuedBook) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        IssuedBookEntity issuedBookEntity =  mapper.map(issuedBook, IssuedBookEntity.class);

        return issuedBooksRepository.isIssued(issuedBookEntity);
    }

}
