package org.example.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.dao.BookDao;
import org.example.dao.OrderDao;
import org.example.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class MainController {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private VehicleDao vehicleDao;

    @RequestMapping(path = "/hello", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> hello(){
        return new ResponseEntity<>("Hello spring", HttpStatus.OK);
    }

    @GetMapping(path = "/books", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getAllBooks() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(bookDao.getBooks());
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(path = "/orders", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getAllOrders() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(orderDao.getAllOrders());
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping(path = "/vehicles", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> getAllVehicles() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(vehicleDao.getAllVehicles());
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @RequestMapping(path = "/*", produces = {MediaType.TEXT_PLAIN_VALUE})
    @ResponseBody
    public ResponseEntity<String> notFound(){
        return new ResponseEntity<>("404 Page not found, but spring is here", HttpStatus.OK);
    }

}
