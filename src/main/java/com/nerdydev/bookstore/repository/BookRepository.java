package com.nerdydev.bookstore.repository;

import com.nerdydev.bookstore.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface BookRepository extends CrudRepository<Book, BigDecimal> {
}
