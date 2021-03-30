package com.nerdydev.bookstore.web;

import com.nerdydev.bookstore.model.Book;
import com.nerdydev.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class BookStoreController {

    private BookRepository bookRepository;


    @Autowired
    public void setBookRepository(BookRepository bookRepository1) {
        this.bookRepository = bookRepository1;
    }


    @GetMapping(value = {"/","/list"})
    public String getBookForm(ModelMap model) {
        model.put("listBook", bookRepository.findAll());
        return "booklist";
    }

    @GetMapping("/new")
    public String showNewForm() {
        return "bookform";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam BigDecimal id, ModelMap modelMap) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        Book book = null;
        if (bookOpt.isPresent()) {
            book = bookOpt.get();
        }
        modelMap.put("book", book);
        return "bookform";
    }


    @PostMapping("/insert")
    public String insertBook(@RequestParam String title, @RequestParam String author, @RequestParam float price,
                             ModelMap model) {

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        BigDecimal amount = BigDecimal.valueOf(price);
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        newBook.setPrice(amount.doubleValue());
        bookRepository.save(newBook);
        model.put("listBook", bookRepository.findAll());
        return("booklist");
    }


    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateBook(Book book, ModelMap model) {
        bookRepository.save(book);
        model.put("listBook", bookRepository.findAll());
        return("booklist");
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam BigDecimal id, ModelMap modelMap) {
        bookRepository.deleteById(id);
        modelMap.put("listBook", bookRepository.findAll());
        return("booklist");
    }



    @GetMapping("error")
    public String getError() {
        return "error";
    }


}
