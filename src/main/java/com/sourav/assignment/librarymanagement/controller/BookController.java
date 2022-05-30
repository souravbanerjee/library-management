package com.sourav.assignment.librarymanagement.controller;

import com.sourav.assignment.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("listAllBooks")
    public @ResponseBody ResponseEntity<Object> getAllBooks() throws Exception{
        return new ResponseEntity<Object>(bookService.getAllBooks(), HttpStatus.OK);
    }


}
