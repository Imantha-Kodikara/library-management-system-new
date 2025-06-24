package service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.BookDTO;
import model.entity.BookEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.BookRepository;
import service.custom.BookService;
import util.RepositoryType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public BookDTO searchByIsbn(String isbn) throws SQLException {
        BookEntity entity = bookRepository.searchByIsbn(isbn);

        if(entity != null){
            ModelMapper mapper = new ModelMapper();
            BookDTO book = mapper.map(entity, BookDTO.class);
            return book;
        }else{
            return null;
        }

    }

    @Override
    public Boolean update(BookDTO book) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        BookEntity entity = mapper.map(book, BookEntity.class);
       return bookRepository.update(entity);
    }

    @Override
    public Boolean deleteById(Integer id) throws SQLException {
        return bookRepository.deleteById(id);
    }

    @Override
    public List<BookDTO> getAll() throws SQLException {
        List<BookEntity> bookEntities = bookRepository.getAll();
        List<BookDTO> bookDTOS = new ArrayList<>();

            bookEntities.forEach(bookEntity -> {
                ModelMapper mapper = new ModelMapper();
                BookDTO book = mapper.map(bookEntity, BookDTO.class);
                bookDTOS.add(book);
            });
            return bookDTOS;
    }

    public ObservableList<String> getAllBookTitles() throws SQLException {
        List<BookDTO> bookDTOS = getAll(); //calling above method
        ObservableList<String> bookTitles = FXCollections.observableArrayList(); // creating arraylist to store books titles

        for (BookDTO bookDTO : bookDTOS) {
            bookTitles.add(bookDTO.getTitle());
        }
        return bookTitles;
    }
}
