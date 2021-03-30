package com.nerdydev.bookstore.web;

import com.nerdydev.bookstore.model.Book;
import com.nerdydev.bookstore.service.BookReportService;
import com.nerdydev.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.Optional;

@Controller
public class BookStoreController {

    private BookService bookService;
    private BookReportService bookReportService;

    @Autowired
    public void setBookService(BookService bookService1) {
        this.bookService = bookService1;
    }

    @Autowired
    public void setBookReportService(BookReportService bookReportService1) {
        this.bookReportService = bookReportService1;
    }

    @GetMapping(value = {"/","/list"})
    public String getBookForm(ModelMap model) {
        model.put("listBook", bookService.findAll());
        return "booklist";
    }

    @GetMapping("/new")
    public String showNewForm() {
        return "bookform";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam BigDecimal id, ModelMap modelMap) {
        Optional<Book> bookOpt = bookService.findById(id);
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
        bookService.save(newBook);
        model.put("listBook", bookService.findAll());
        return("booklist");
    }


    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String updateBook(Book book, ModelMap model) {
        bookService.save(book);
        model.put("listBook", bookService.findAll());
        return("booklist");
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam BigDecimal id, ModelMap modelMap) {
        bookService.deleteById(id);
        modelMap.put("listBook", bookService.findAll());
        return("booklist");
    }


    @GetMapping("/report")
    public String generateReport() {
        File reportFile = bookReportService.generateReport();
        return("booklist");
    }


    @GetMapping("error")
    public String getError() {
        return "error";
    }


}
