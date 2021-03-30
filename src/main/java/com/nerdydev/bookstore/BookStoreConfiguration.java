package com.nerdydev.bookstore;

import com.nerdydev.bookstore.service.BookService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class BookStoreConfiguration {



}
