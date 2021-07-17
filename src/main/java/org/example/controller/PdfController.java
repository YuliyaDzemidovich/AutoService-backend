package org.example.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/report")
public class PdfController {

    final static Logger log = LogManager.getLogger(PdfController.class);

    @GetMapping(path = "/{orderId}",  headers="Accept=*/*", produces = {MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<byte[]> getPdfReport(@PathVariable long orderId){
        Document document = new Document();
        byte[] pdfBytes = new byte[0];
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            generatePdf(document, orderId);
            document.close();
            pdfBytes = byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            log.warn("can't add pieces to PDF report file");
            e.printStackTrace();
        }

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=report.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    public void generatePdf(Document document, long orderId) throws DocumentException {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Report template for order # " + orderId, font);
        document.add(chunk);
    }
}
