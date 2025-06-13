package service.custom.impl;

import model.dto.BookDTO;
import model.entity.BookEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.BookRepository;
import service.custom.BookService;
import util.RepositoryType;

import java.sql.SQLException;

public class BookServiceImpl implements BookService {
    BookRepository bookRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.BOOK);
    @Override
    public Boolean addBook(BookDTO book) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        BookEntity entity = mapper.map(book, BookEntity.class);
        return bookRepository.add(entity);
    }

    @Override
    public Integer generateBookId() throws SQLException {
        return bookRepository.getNextId();
    }

    @Override
    public Boolean isBookRegistered(String isbn) throws SQLException {
        return bookRepository.isBookRegistered(isbn);
    }
}
