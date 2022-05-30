package com.sourav.assignment.librarymanagement.service;

import com.sourav.assignment.librarymanagement.model.Book;
import com.sourav.assignment.librarymanagement.model.Library;
import com.sourav.assignment.librarymanagement.repository.BookRepository;
import com.sourav.assignment.librarymanagement.repository.LibraryRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
public class LibraryServiceTest {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryRepository libraryRepository;

    /*
    If book is not present in Library, while issuing the same will throw "Book is out of stock!"
     */
    @Test
    public void cannotIssueBookWhenStockIsNotAvailable_Test() throws Exception {
        String bookId = "1233";
        String userId = "1122";
        Book book = createBook("Java Programming", "H Sheildth", "TMC", "Programming", 0);
        when(bookRepository.findById(bookId)).thenReturn(book);

        try {
            libraryService.issueBook(bookId, userId);
        } catch (Exception ex) {
            assertEquals("Book is out of stock", ex.getMessage());
        }
    }

    /*
     *Note: each user has a borrowing limit of 2 books.
     * If a user is already issued 2 books an error message "One user can not issue more than 2 books" will be thrown
     */
    @Test
    public void cannotIssueBookWhenUserHasTwoBooksIssuedAlready_Test() throws Exception {
        String bookId1 = "1111";
        String bookId2 = "1112";
        String newBookToBeIssued = "1113";
        String userId = "1122";
        LocalDate issueDate = LocalDate.of(2022, 05, 24);
        Book book = createBook("Java Programming", "H Sheildth", "TMC", "Programming", 4);
        when(bookRepository.findById(newBookToBeIssued)).thenReturn(book);
        List<Library> list = new ArrayList<>();
        list.add(mockLibraryData(userId, bookId1, issueDate));
        list.add(mockLibraryData(userId, bookId2, issueDate));

        when(libraryRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(list);

        try {
            libraryService.issueBook(newBookToBeIssued, userId);
        } catch (Exception ex) {
            assertEquals("One user can not issue more than 2 books", ex.getMessage());
        }
    }

    /*
        Same book can not be issued by the user twice unless returned
     */
    @Test
    public void cannotIssueBookWhenSameBookIsAlreadyIssuedAndNotReturned_Test() throws Exception {
        String bookId = "1111";
        String userId = "1122";
        LocalDate issueDate = LocalDate.of(2022, 05, 24);
        List<Library> list = new ArrayList<>();
        list.add(mockLibraryData(userId, bookId, issueDate));
        Book book = createBook("Java Programming", "H Sheildth", "TMC", "Programming", 4);
        when(bookRepository.findById(bookId)).thenReturn(book);
        when(libraryRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(list);

        try {
            libraryService.issueBook(bookId, userId);
        } catch (Exception ex) {
            assertEquals("This book is already issued by the user and not returned yet", ex.getMessage());
        }
    }

    private Book createBook(String name, String author, String publisher, String category, Integer stock) {
        return new Book(name, category, author, publisher, stock);
    }

    private Library mockLibraryData(String userId, String bookId, LocalDate issueDate) {
        return new Library(userId, bookId, issueDate.toString(), null);
    }
}
