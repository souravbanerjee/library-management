package com.sourav.assignment.librarymanagement.service;

import com.sourav.assignment.librarymanagement.model.Book;
import com.sourav.assignment.librarymanagement.model.Library;
import com.sourav.assignment.librarymanagement.model.Users;
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
import java.util.Optional;

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
        Same book can not be issued twice unless returned
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

    /*
      Given, there are books in the library and user has no book issued
      When, I choose a book to add to my borrowed list
      Then, a new entry will be created in Library table with userId assocated to bookId
      And, the book is removed from the library
   */
    @Test
    public void issueBookWhenUserHasNoBookIssued_Test() throws Exception {
        String newBookId = "1122";
        String userId = "1234";
        //LocalDate issueDate = LocalDate.of(2022, 05, 24);

        List<Library> list = new ArrayList<>();
        when(libraryRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(list);

        Optional<Book> book = Optional.ofNullable(createBook("Java Programming", "H Sheildth", "TMC", "Programming", 4));

        when(bookRepository.findById(newBookId)).thenReturn(book.get());

        Library library = libraryService.issueBook(newBookId, userId);
        list.add(library);

        Book issuedBook = bookRepository.findById(newBookId);
        assertEquals(issuedBook.getStock() == 3, true);
        assertEquals(libraryRepository.findByUserIdAndReturnDateIsNull(userId).size() == 1, true);
    }

    /*
       Given, there is only one copy of a book in the library
       When, I choose a book to add to my borrowed list
       Then, the book is added to my borrowed list
       And, the book is removed from the library
    */
    @Test
    public void issueBookWhenOneBookLeftBookTableWillOutOfStock_Test() throws Exception {
        String existingBookId = "1111";
        String newBookId = "1122";
        String userId = "1234";
        LocalDate issueDate = LocalDate.of(2022, 05, 24);

        List<Library> listOfBooksUserIssued = new ArrayList<>();
        listOfBooksUserIssued.add(mockLibraryData(userId, existingBookId, issueDate));
        when(libraryRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(listOfBooksUserIssued);

        Optional<Book> book = Optional.ofNullable(createBook("Java Programming", "H Sheildth", "TMC", "Programming", 1));

        when(bookRepository.findById(newBookId)).thenReturn(book.get());

        Library library = libraryService.issueBook(newBookId, userId);
        listOfBooksUserIssued.add(library);

        Book bookDetails = bookRepository.findById(newBookId);
        assertEquals(bookDetails.getStock() == 0, true);
        assertEquals(libraryRepository.findByUserIdAndReturnDateIsNull(userId).size() == 2, true);
    }

    /*
        Given, there are books in the library
        When, I choose a book to add to my borrowed list
        Then, the book is added to my borrowed list
        And, the book is removed from the library
     */
    @Test
    public void issueBookWhenUserHasLessThanTwoBooksIssued_Test() throws Exception {
        String existingBookId = "1111";
        String newBookId = "1122";
        String userId = "1234";
        LocalDate issueDate = LocalDate.of(2022, 05, 24);

        List<Library> list = new ArrayList<>();
        list.add(mockLibraryData(userId, existingBookId, issueDate));
        when(libraryRepository.findByUserIdAndReturnDateIsNull(userId)).thenReturn(list);

        Optional<Book> book = Optional.ofNullable(createBook("Java Programming", "H Sheildth", "TMC", "Programming", 2));
        when(bookRepository.findById(newBookId)).thenReturn(book.get());

        Library library = libraryService.issueBook(newBookId, userId);
        list.add(library);

        Book issuedBook = bookRepository.findById(newBookId);
        assertEquals(issuedBook.getStock() == 1, true);
        assertEquals(libraryRepository.findByUserIdAndReturnDateIsNull(userId).size() == 2, true);
    }

    private Book createBook(String name, String author, String publisher, String category, Integer stock) {
        return new Book(name, category, author, publisher, stock);
    }

    private Users createUser(String mobileNo, String firstName, String lastName, String address, int issuedBooks) {
        return new Users(mobileNo, firstName, lastName, address);
    }

    private Library mockLibraryData(String userId, String bookId, LocalDate issueDate) {
        return new Library(userId, bookId, issueDate.toString(), null);
    }
}
