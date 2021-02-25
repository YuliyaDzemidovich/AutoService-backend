package org.example.controller;

import org.apache.log4j.Logger;
import org.example.Main;
import org.example.dao.BookDao;
import org.example.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class BookCoverController {
    @Autowired
    private BookDao bookDao;

    private final static String SERVER_FILE_STORAGE_URL = "C:/Dropbox/Java/server/BookStore";

    final static Logger log = Logger.getLogger(Main.class);

    /**
     * Returns absolute path to book cover image if file is found at the requested path. <br>
     * Absolute path consists of {@value #SERVER_FILE_STORAGE_URL} where application's folder
     * is located on server machine, and relative path of particular book cover which is
     * received from the database.
     * @param id Book id as String as part of URL
     * @return absolute path to cover image of the requested book
     * @see org.example.model.Book
     */
    @RequestMapping(path = "/covers/{id}", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public ResponseEntity<Resource> getImageAsResource(@PathVariable String id){
        Resource resource;
        try {
            log.info("ID is: " + Integer.valueOf(id).toString());
            String coverUrl = bookDao.getCoverUrlById(Integer.valueOf(id));
            log.info("Absolute path is: " + SERVER_FILE_STORAGE_URL + coverUrl);
            resource = new FileSystemResource(SERVER_FILE_STORAGE_URL + coverUrl);
        } catch (NumberFormatException e) {
            resource = null;
        }
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
