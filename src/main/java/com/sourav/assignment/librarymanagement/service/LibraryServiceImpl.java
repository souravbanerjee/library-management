package com.sourav.assignment.librarymanagement.service;

import com.sourav.assignment.librarymanagement.model.Book;
import com.sourav.assignment.librarymanagement.model.Library;
import com.sourav.assignment.librarymanagement.repository.BookRepository;
import com.sourav.assignment.librarymanagement.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                case 1:
                    int stock = book.getStock()-1;
                    book.setStock(stock);
                    break;
                case 2:
                    throw new Exception("One user can not issue more than 2 books");
            }
        } else {
            throw new Exception("Book is out of stock");
        }


        return updatedLibrary;
    }

}
