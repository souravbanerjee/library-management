package com.sourav.assignment.librarymanagement.repository;

import com.sourav.assignment.librarymanagement.model.Library;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LibraryRepository {

    public List<Library> findByUserIdAndReturnDateIsNull(String userId){

        return null;
    }

}
