package com.sourav.assignment.librarymanagement.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourav.assignment.librarymanagement.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;

@Repository
public class BookRepository {

    @Value("${bookDatabase}")
    private String books;

    private List<Book> bookList = null;

    @PostConstruct
    public void initBooks() {
        Gson gson = new Gson();
        bookList = (List<Book>) gson.fromJson(books,
                new TypeToken<List<Book>>() {
                }.getType());
    }

    public Book findById(String id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findFirst().get();
    }

    public List<Book> findAll() {
        return bookList;
    }

}
