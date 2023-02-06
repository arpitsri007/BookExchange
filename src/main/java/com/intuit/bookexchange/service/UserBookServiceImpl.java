package com.intuit.bookexchange.service;

import com.intuit.bookexchange.VO.BookSearchVO;
import com.intuit.bookexchange.entity.BookEntity;
import com.intuit.bookexchange.entity.UserBookMappingEntity;
import com.intuit.bookexchange.entity.UserEntity;
import com.intuit.bookexchange.exceptions.BookNotFoundException;
import com.intuit.bookexchange.exceptions.RecordNotFoundException;
import com.intuit.bookexchange.exceptions.TransactionSaveException;
import com.intuit.bookexchange.exceptions.UserNotFoundException;
import com.intuit.bookexchange.model.UserBookMap;
import com.intuit.bookexchange.repository.IBookRepository;
import com.intuit.bookexchange.repository.IUserBookRepository;
import com.intuit.bookexchange.repository.IUserRepository;
import com.intuit.bookexchange.util.ActivityType;
import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserBookServiceImpl implements IUserBookService {

    @Autowired
    private IUserBookRepository userBookRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public List<UserBookMap> searchRequestedBooks(BookSearchVO bookSearchRequest) {

        Integer bookId = bookSearchRequest.getBookId();

        List<UserBookMappingEntity> bookEntityList;

        if (bookId != null) {
            bookEntityList = userBookRepository.findByBookId(bookId);
            if (bookEntityList.isEmpty()) {
                throw new RuntimeException("No such book found");
            }
        } else {

            bookEntityList = userBookRepository.findAll();
        }

        List<UserBookMap> userBookMapList = new ArrayList<>();

        for(UserBookMappingEntity userBookMappingEntity : bookEntityList) {
            UserBookMap userBookMap = new UserBookMap();
            BeanUtils.copyProperties(userBookMappingEntity, userBookMap);
            userBookMapList.add(userBookMap);
        }

        return userBookMapList;
    }

    @Override
    public List<UserBookMap> searchUserBookIds(@NonNull Integer userId) {

        Optional<UserEntity> userEntityOptional = userRepository.findByUserId(userId);

        if(!userEntityOptional.isPresent()) {
            throw new UserNotFoundException("User is not found");
        }

        UserEntity userEntity = userEntityOptional.get();

        List<UserBookMappingEntity> bookEntityList;

        bookEntityList = userBookRepository.findByUserId(userEntity.getUserId());

        List<UserBookMap> userBookMapList = new ArrayList<>();

        for(UserBookMappingEntity userBookMappingEntity : bookEntityList) {
            UserBookMap userBookMap = new UserBookMap();
            BeanUtils.copyProperties(userBookMappingEntity, userBookMap);
            userBookMapList.add(userBookMap);
        }

        return userBookMapList;
    }

    @Override
    public Boolean updateBookOwnerShip(Integer borrowerUserId, Integer lenderUserId, Integer lenderReqBookId,
                                       Integer borrowerReqBookId, ActivityType activityType) {

        // get Borrower & lender UserEntity
        UserEntity borrowerUserEntity = getUserEntity(borrowerUserId);
        UserEntity lenderUserEntity = getUserEntity(lenderUserId);

        // get Borrower & lender BookEntity
        BookEntity lenderBookEntity = getBookEntity(borrowerReqBookId);
        UserBookMappingEntity lenderUserBookMappingEntity = userBookRepository.findByUserIdAndBookId(lenderUserEntity.getUserId(),
                lenderBookEntity.getBookId());

        if(lenderUserBookMappingEntity == null) {
            throw new RecordNotFoundException("UserBookMappingEntity is missing for lender");
        }

        UserBookMappingEntity borrowerUserBookMappingEntity;


        // if Activity is Exchange then update both mapping records else only 1
        if(activityType == ActivityType.EXCHANGE) {

            BookEntity borrowerBookEntity = getBookEntity(lenderReqBookId);

             borrowerUserBookMappingEntity = userBookRepository.findByUserIdAndBookId(borrowerUserEntity.getUserId(),
                    borrowerBookEntity.getBookId());

            if (borrowerUserBookMappingEntity == null) {
                throw new RuntimeException("UserBookMappingEntity is missing for  lender");
            }

            borrowerUserBookMappingEntity.setBookId(lenderBookEntity.getBookId());
            lenderUserBookMappingEntity.setBookId(borrowerBookEntity.getBookId());

            userBookRepository.save(borrowerUserBookMappingEntity);

        } else {

           lenderUserBookMappingEntity.setUserId(borrowerUserId);
           lenderUserBookMappingEntity.setIsExchangeable(false);
        }
        try {

            userBookRepository.save(lenderUserBookMappingEntity);
        } catch (Exception e) {
            throw new TransactionSaveException("Transaction could not be saved");
        }
        return true;
    }


    @Override
    public void updateBookExchangeStatus(Integer userId, Integer updateReqBookId, Boolean isExchangeable) {

        UserEntity userEntity = getUserEntity(userId);
        BookEntity bookEntity = getBookEntity(updateReqBookId);

        UserBookMappingEntity userBookMappingEntity = userBookRepository.findByUserIdAndBookId(userEntity.getUserId(), bookEntity.getBookId());
        userBookMappingEntity.setIsExchangeable(isExchangeable);
        userBookRepository.save(userBookMappingEntity);
    }

    @Override
    public UserBookMap addUserBook(Integer userId, Integer bookId) {

        UserBookMappingEntity userBookMappingEntity = new UserBookMappingEntity();

        //get userEntity
        Optional<UserEntity> userEntityOptional = userRepository.findByUserId(userId);

        UserEntity userEntity;

        if (userEntityOptional.isPresent()) {
            userEntity = userEntityOptional.get();
        } else {
            throw new UserNotFoundException("User is not found");
        }

        // get BookEntity
        Optional<BookEntity> bookEntityOptional = bookRepository.findByBookId(bookId);

        BookEntity bookEntity;

        if (!userEntityOptional.isPresent()) {
            throw new BookNotFoundException("Book is not Found");
        }
        bookEntity = bookEntityOptional.get();

        userBookMappingEntity.setUserId(userEntity.getUserId());
        userBookMappingEntity.setBookId(bookEntity.getBookId());
        userBookMappingEntity.setIsExchangeable(false);

        UserBookMappingEntity savedUserBookMappingEntity = userBookRepository.save(userBookMappingEntity);

        UserBookMap userBookMap = new UserBookMap();
        BeanUtils.copyProperties(savedUserBookMappingEntity, userBookMap);
        return userBookMap;
    }


    private UserEntity getUserEntity(Integer userId) {
        //get userEntity
        Optional<UserEntity> userEntityOptional = userRepository.findByUserId(userId);
        UserEntity userEntity;

        if (!userEntityOptional.isPresent()) {
            throw new UserNotFoundException("User is not Found");
        }
        userEntity = userEntityOptional.get();

        return userEntity;
    }

    private BookEntity getBookEntity(Integer bookId) {
        // get BookEntity
        Optional<BookEntity> bookEntityOptional = bookRepository.findByBookId(bookId);

        if (!bookEntityOptional.isPresent()) {
            throw new BookNotFoundException("Book is not found");
        }
        return bookEntityOptional.get();
    }

}
