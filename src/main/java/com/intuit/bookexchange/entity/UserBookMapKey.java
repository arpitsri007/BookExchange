package com.intuit.bookexchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Data
public class UserBookMapKey implements Serializable {

//    private static final long serialVersionUID = 1L;
//
//    @Column(name = "bookId")
//    private Integer bookId;
//
//    @Column(name = "userId")
//    private Integer userId;
//
//    public UserBookMapKey() {
//
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof UserBookMapKey) {
//            UserBookMapKey pk = (UserBookMapKey)obj;
//            if (!pk.getBookId().equals(this.getBookId())) {
//                return false;
//            }
//            if (!pk.getUserId().equals(this.getUserId())) {
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(this.getUserId(), this.getBookId());
//    }
}
