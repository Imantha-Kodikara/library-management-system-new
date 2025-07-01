package repository;

import repository.custom.IssuedBooksRepository;
import repository.custom.ReturnBookRepository;
import repository.custom.impl.BookRepositoryImpl;
import repository.custom.impl.IssuedBooksRepositoryImpl;
import repository.custom.impl.MemberRepositoryImpl;
import repository.custom.impl.ReturnBookRepositoryImpl;
import service.custom.impl.BookServiceImpl;
import service.custom.impl.MemberServiceImpl;
import util.RepositoryType;

public class DaoFactory {
    private static DaoFactory instance;

    private DaoFactory(){
    }

    public static DaoFactory getInstance() {
        return instance == null ? instance = new DaoFactory() : instance;
    }

    public <T extends SuperRepository> T getRepositoryType(RepositoryType type){
        switch (type){
            case MEMBER: return (T) new MemberRepositoryImpl();
            case BOOK: return (T) new BookRepositoryImpl();
            case ISSUEDBOOK: return (T) new IssuedBooksRepositoryImpl();
            case RETURNBOOK: return (T) new ReturnBookRepositoryImpl();
        }
        return null;
    }
}
