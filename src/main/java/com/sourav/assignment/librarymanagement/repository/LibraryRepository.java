package com.sourav.assignment.librarymanagement.repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sourav.assignment.librarymanagement.model.Library;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Repository
public class LibraryRepository {

    @Value("${libraryDatabase}")
    private String libraryDatabase;

    private List<Library> libraryData= null;

    @PostConstruct
    public void initLibrary(){
        Gson gson = new Gson();
        libraryData=(List<Library>)gson.fromJson(libraryDatabase,
                new TypeToken<List<Library>>(){}.getType());
    }

    public List<Library> findByUserIdAndReturnDateIsNull(String userId){

        return libraryData.stream().filter(library -> library.getUserId().equals(userId))
                .filter(library -> (null==library.getReturnDate() || library.getReturnDate().isEmpty()))
                .collect(Collectors.toList());
    }

    public Library save(Library library){
        library.setId(new Random().nextLong());
        libraryData.add(library);
        return library;
    }

    public List<Library> findByUserIdAndBookIdAndReturnDateIsNull(String userId,String bookId){

        return libraryData.stream().filter(library -> library.getUserId().equals(userId)).filter(library -> library.getBookId().equals(bookId))
                .filter(library -> (null==library.getReturnDate() || library.getReturnDate().isEmpty()))
                .collect(Collectors.toList());
    }

}
