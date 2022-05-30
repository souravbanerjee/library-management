package com.sourav.assignment.librarymanagement.service;

import com.sourav.assignment.librarymanagement.model.Book;
import com.sourav.assignment.librarymanagement.model.Library;
import com.sourav.assignment.librarymanagement.repository.BookRepository;
import com.sourav.assignment.librarymanagement.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryRepository libraryRepository;


    @Override
    public Library issueBook(String bookId, String userId) throws Exception {

        Library updatedLibrary = null;

        Book book = bookRepository.findById(bookId);
        if (book.getStock() > 0) {
            List<Library> libraries = libraryRepository.findByUserIdAndReturnDateIsNull(userId);
            switch (libraries.size()) {
                case 0:
                    updatedLibrary = issueBook(bookId, userId, book);
                    break;
                case 1:
                    if (!bookId.equals(libraries.get(0).getBookId())) {
                        updatedLibrary = issueBook(bookId, userId, book);
                    } else {
                        throw new Exception("This book is already issued by the user and not returned yet");
                    }
                    break;
                case 2:
                    throw new Exception("One user can not issue more than 2 books");
            }
        } else {
            throw new Exception("Book is out of stock");
        }


        return updatedLibrary;
    }

    private Library issueBook(String bookId, String userId, Book book) {
        Library library = new Library(userId, bookId, LocalDate.now().toString(), null);
        int stock = book.getStock() - 1;
        book.setStock(stock);
        return libraryRepository.save(library);
    }

    @Override
    public List<Library> returnBook(List<Library> list) throws Exception {
        return null;
    }

}
