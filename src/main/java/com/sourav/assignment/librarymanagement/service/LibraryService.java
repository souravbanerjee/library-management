package com.sourav.assignment.librarymanagement.service;


import com.sourav.assignment.librarymanagement.model.Library;

public interface LibraryService {

    Library issueBook(String bookId, String userId) throws Exception;
}
