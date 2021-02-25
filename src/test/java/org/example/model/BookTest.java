package org.example.model;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    final static Logger log = Logger.getLogger(BookTest.class);

    @Test
    void setTitle() {
        Book book = new Book();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 7; i++) {
            sb.append("12345dfjjf"); // 10 symbols * 7 times = 70 characters < 100
        }
        String rightStr = sb.toString();
        book.setTitle(rightStr);

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<Book>> violations1 = validator.validate(book);
//        for (ConstraintViolation<Book> violation : violations1) {
//            log.error(violation.getMessage());
//        }

        String tooLongStr = rightStr + rightStr; // 70 * 2 = 140 characters > 100
        book.setTitle(tooLongStr);
        Set<ConstraintViolation<Book>> violations2 = validator.validate(book);
//        for (ConstraintViolation<Book> violation : violations2) {
//            log.error(violation.getMessage());
//        }
        assertAll(
                () -> assertTrue(violations1.isEmpty()),
                () -> assertFalse(violations2.isEmpty())
        );
    }
}
