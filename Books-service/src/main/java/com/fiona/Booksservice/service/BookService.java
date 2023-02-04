package com.fiona.Booksservice.service;

import com.fiona.Booksservice.Model.Book;
import com.fiona.Booksservice.dto.BookRequest;
import com.fiona.Booksservice.dto.BookResponse;
import com.fiona.Booksservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // at compile time it will create constructor
@Slf4j
public class BookService {
    private final BookRepository bookRepository;


    public void createBook(BookRequest bookRequest){
        Book book = Book.builder()
                .name(bookRequest.getName())
                .description(bookRequest.getDescription())
                .price(bookRequest.getPrice())
                .build();
    bookRepository.save(book);
    log.info("Product{} is saved",book.getId());


    }

    public List<BookResponse> getAllProducts(){
    List<Book> books = bookRepository.findAll();
    return books.stream().map(book -> mapToProductResponse(book)).collect(Collectors.toList());
    }

    private BookResponse mapToProductResponse(Book book){
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .price(book.getPrice())
                .build();
    }
}
