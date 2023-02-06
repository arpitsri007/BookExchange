package com.intuit.bookexchange.controller;

import com.intuit.bookexchange.VO.BookVO;
import com.intuit.bookexchange.VO.UserBookUpdateVO;
import com.intuit.bookexchange.VO.UserVO;
import com.intuit.bookexchange.exceptions.InvalidEmailException;
import com.intuit.bookexchange.model.Book;
import com.intuit.bookexchange.model.User;
import com.intuit.bookexchange.service.*;
import com.intuit.bookexchange.util.VerificationStatus;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ExchangeValidationService exchangeValidationService;

    // user uses this API to signup - user gets 10 reward points on signup
    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody UserVO request) {

        String email = request.getUserEmail();

        if (Strings.isEmpty(email))
            throw new InvalidEmailException("EmailId is required");

        Boolean isExistingEmail = exchangeValidationService.isValidEmail(request.getUserEmail());

        if (isExistingEmail) {
            throw new InvalidEmailException("Email already exists, provide another email-address");
        }

        User user = new User();
        user.setVerificationStatus(VerificationStatus.VERIFIED);
        user.setUserEmail(request.getUserEmail());
        user.setUserName(request.getUserName());
        user.setRewardPoint(10);

        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}
