package com.nerdydev.bookstore.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name="book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
    @Column(name = "book_id", nullable = false)
    private BigDecimal bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "price", nullable = false)
    private double price;


    public BigDecimal getBookId() {
        return bookId;
    }

    public void setBookId(BigDecimal bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
