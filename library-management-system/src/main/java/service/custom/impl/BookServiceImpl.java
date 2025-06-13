package service.custom.impl;

import model.dto.BookDTO;
import repository.DaoFactory;
import repository.custom.BookRepository;
import service.ServiceFactory;
import service.custom.BookService;
import util.RepositoryType;

import java.sql.SQLException;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.BOOK);
    @Override
    public Boolean addBook(BookDTO book) {
        return true;
    }

    @Override
    public Integer generateBookId() throws SQLException {
        return bookRepository.getNextId();
    }
}
