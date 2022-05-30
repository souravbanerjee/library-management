package com.sourav.assignment.librarymanagement.service;


import com.sourav.assignment.librarymanagement.model.Library;

import java.util.List;

public interface LibraryService {

    Library issueBook(String bookId, String userId) throws Exception;

    List<Library> returnBook(List<Library> list) throws Exception;
}
