package com.nerdydev.bookstore.service;

import com.nerdydev.bookstore.model.Book;
import com.nerdydev.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository1) {
        this.bookRepository = bookRepository1;
    }

    @Cacheable(value="books")
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(BigDecimal id) {
        return bookRepository.findById(id);
    }

    public void save(Book newBook) {
        bookRepository.save(newBook);
    }

    public void deleteById(BigDecimal id) {
        bookRepository.deleteById(id);
    }
}
