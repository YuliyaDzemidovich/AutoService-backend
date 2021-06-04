package org.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/report")
public class PdfController {
    @GetMapping(path = "/{orderId}",  headers="Accept=*/*", produces = {MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<String> generatePdf(@PathVariable long orderId){
        return new ResponseEntity<>("some PDF report for order #" + orderId, HttpStatus.OK);
    }
}
