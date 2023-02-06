package com.intuit.bookexchange.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Data
public class UserBookRatingKey implements Serializable {
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "bookId")
    private Integer bookId;

    public UserBookRatingKey() {

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserBookRatingKey) {
            UserBookRatingKey pk = (UserBookRatingKey)obj;
            if (!pk.getBookId().equals(this.getBookId())) {
                return false;
            }
            if (!pk.getUserId().equals(this.getUserId())) {
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId(), this.getBookId());
    }
}
