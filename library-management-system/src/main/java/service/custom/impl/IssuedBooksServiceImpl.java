package service.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.BookDTO;
import model.dto.IssuedBookDTO;
import model.entity.IssuedBookEntity;
import org.modelmapper.ModelMapper;
import repository.DaoFactory;
import repository.custom.IssuedBooksRepository;
import service.ServiceFactory;
import service.custom.BookService;
import service.custom.IssuedBooksService;
import util.RepositoryType;
import util.ServiceType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class IssuedBooksServiceImpl implements IssuedBooksService {
    IssuedBooksRepository issuedBooksRepository = DaoFactory.getInstance().getRepositoryType(RepositoryType.ISSUEDBOOK);
    BookService bookService = ServiceFactory.getInstance().getServiceType(ServiceType.BOOK);

    public Boolean isIssued(IssuedBookDTO issuedBook) throws SQLException {
        ModelMapper mapper = new ModelMapper();
        IssuedBookEntity issuedBookEntity =  mapper.map(issuedBook, IssuedBookEntity.class);

        return issuedBooksRepository.isIssued(issuedBookEntity);
    }

    public ObservableList<Integer> getIssuedBooksMembersId() throws SQLException {
        return issuedBooksRepository.getIssuedBooksMembersId();
    }

    public ObservableList<String> getIssuedBooksTitles(Integer memberId) throws SQLException { //To return issued books titles related with member id
        List<Integer> issuedBooksIds = getIssuedBooksIds(memberId);
        List<BookDTO> books = bookService.getAll();
        ObservableList<String> issuedBooksTitles = FXCollections.observableArrayList();

        for(BookDTO book : books){
            if(issuedBooksIds.contains(book.getId())){
                issuedBooksTitles.add(book.getTitle());
            }
        }
        return issuedBooksTitles;
    }

    public List<Integer> getIssuedBooksIds(Integer memberId) throws SQLException { //to get books ids that exact member borrowed
        List<Integer> issuedBooksIds = issuedBooksRepository.getIssuedBooksIds(memberId);
        return issuedBooksIds;
    }

    @Override
    public List<IssuedBookDTO> getAll() throws SQLException {
        List<IssuedBookEntity> issuedBookEntities = issuedBooksRepository.getAll();
        List<IssuedBookDTO> issuedBookDTOS = new ArrayList<>();

        issuedBookEntities.forEach(issuedBook -> {
            ModelMapper mapper = new ModelMapper();
            IssuedBookDTO issuedBookDTO = mapper.map(issuedBook, IssuedBookDTO.class);
            issuedBookDTOS.add(issuedBookDTO);
        });

        return issuedBookDTOS;
    }

    public IssuedBookDTO getIssuedBook(Integer memberId, String bookTitle) throws SQLException {
        List<IssuedBookDTO> issuedBookDTOS = getAll();
        List<BookDTO> getAllBooks = bookService.getAll();

        //Find the bookId matching the given Title;
        Integer bookId = null;
        for(BookDTO book : getAllBooks){
            if(book.getTitle().equals(bookTitle)){
                bookId = book.getId();
                break;
            }
        }

        //Find issued book with matching memberId and bookId
        for(IssuedBookDTO issuedBookDTO : issuedBookDTOS){
            if(issuedBookDTO.getMemberId().equals(memberId) && issuedBookDTO.getBookId().equals(bookId)){
                return issuedBookDTO;
            }
        }
        return null;
    }
}
