package com.sourav.assignment.librarymanagement.service;

import com.sourav.assignment.librarymanagement.model.Book;
import com.sourav.assignment.librarymanagement.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    List<Book> books = null;
    @Before
    public void init(){
        books = new ArrayList<>();
        books.add(createBook("Java Programming","H Sheildth","TMC","Programming",10));
        books.add(createBook("C Programming","J Kanitkar","TMC","Programming",10));
        books.add(createBook("Ramayana","Devdutt Pattanaik","Paperback","Mythology",10));
        books.add(createBook("Ramayana","Devdutt Pattanaik","Paperback","Mythology",10));
    }

    /*
    Given, there are no books in the library
    When, I view the books in the library
    Then, I see an empty library
     */
    @Test
    public void getAllBooksWhenNoBooksAreAvailable_Test(){
        List<Book> emptyBook = new ArrayList<>();
        Mockito.when(bookRepository.findAll()).thenReturn(emptyBook);
        List<Book> actualBooks = bookService.getAllBooks();
        assertEquals(actualBooks.size(),0);
    }


    /*
    Given, there are books in the library
    When, I view the books in the library
    Then, I see the list of books in the library
     */
    @Test
    public void getAllBooksWhenBooksAreAvailable_Test(){
        when(bookRepository.findAll()).thenReturn(books);
        List<Book> actualBooks = bookService.getAllBooks();
        assertEquals(actualBooks,books);
    }


    private Book createBook(String name,String author, String publisher, String category, Integer stock) {
        return new Book(name, category, author, publisher, stock);
    }
}
