package com.sourav.assignment.librarymanagement.service;

import com.sourav.assignment.librarymanagement.model.Book;
import com.sourav.assignment.librarymanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;


    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

}
