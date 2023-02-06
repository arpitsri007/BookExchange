package com.intuit.bookexchange.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.intuit.bookexchange.util.VerificationStatus;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    private String userEmail;
    private String userName;
    private VerificationStatus verificationStatus;
    private Integer rewardPoint;
    private List<Book> sellableBookList;

}
