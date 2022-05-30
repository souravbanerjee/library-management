package com.sourav.assignment.librarymanagement.controller;

import com.sourav.assignment.librarymanagement.model.ErrorResponse;
import com.sourav.assignment.librarymanagement.model.Library;
import com.sourav.assignment.librarymanagement.repository.LibraryRepository;
import com.sourav.assignment.librarymanagement.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private LibraryRepository libraryRepository;

    @PutMapping("issueBook")
    public @ResponseBody
    ResponseEntity<?> issueBook(@RequestBody Library library) throws Exception {
        try {
            return new ResponseEntity(libraryService.issueBook(library.getBookId(), library.getUserId()), HttpStatus.CREATED);
        } catch (Exception ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setError(ex.getMessage());
            errorResponse.setCode("FORBIDDEN");
            return new ResponseEntity(errorResponse, HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("returnBook")
    public @ResponseBody
    ResponseEntity<?> returnBook(@RequestBody List<Library> libraryList) throws Exception {
        try{
            return new ResponseEntity(libraryService.returnBook(libraryList), HttpStatus.OK);
        }catch (Exception ex){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setError(ex.getMessage());
            errorResponse.setCode("FORBIDDEN");
            return new ResponseEntity(errorResponse, HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("getLibraryDetails")
    public @ResponseBody
    ResponseEntity<?> getLibraryDetails() throws Exception {
        return new ResponseEntity(libraryRepository.findAll(), HttpStatus.OK);
    }

}
