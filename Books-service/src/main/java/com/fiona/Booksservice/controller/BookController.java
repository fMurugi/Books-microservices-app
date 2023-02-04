package com.fiona.Booksservice.controller;

import com.fiona.Booksservice.dto.BookRequest;
import com.fiona.Booksservice.dto.BookResponse;
import com.fiona.Booksservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    BookService bookService;
    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBook(@RequestBody BookRequest bookRequest){
        bookService.createBook(bookRequest);
    }

    public List<BookResponse> getAllProducts(){
        return bookService.getAllProducts();
    }
}
