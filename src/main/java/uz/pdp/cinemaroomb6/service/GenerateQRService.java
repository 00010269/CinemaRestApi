package uz.pdp.cinemaroomb6.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import uz.pdp.cinemaroomb6.projection.PdfTicketProjection;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


@Service
public class GenerateQRService {

    public void createQRCode(String data) throws WriterException, IOException {

//data that we want to store in the QR code
//        String data= "THE HABIT OF PERSISTENCE IS THE HABIT OF VICTORY.";
//path where we want to get QR Code
        String path="E:\\Programming\\Unicorn\\Cinema ROOM Rest Service\\Cinema-room-b6\\src\\main\\resources\\static\\qrCode.png";
//Encoding charset to be used
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
//generates QR code with Low level(L) error correction capability
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//invoking the user-defined method that creates the QR code
        generateQRcode(data, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
//prints if the QR code is generated
        System.out.println("QR Code created successfully.");
    }


//    public static String createQRCodeItext(String data, String qrCodeName) throws DocumentException, FileNotFoundException {
//        //Step - 1 :Create Document object that will hold the code
//        Document qr_code_Example = new Document(new Rectangle(360, 852));
//        // Step-2: Create PdfWriter object for the document
//        String path="E:\\Programming\\Unicorn\\Cinema ROOM Rest Service\\Cinema-room-b6\\src\\main\\resources\\static\\" + qrCodeName + ".pdf";
//        PdfWriter writer = PdfWriter.getInstance(qr_code_Example, new FileOutputStream(path));
//        // Step -3: Open document for editing
//        qr_code_Example.open();
//        //Step-4: Create New paragraph for QR Summary
////        qr_code_Example.add(new Paragraph("Wow, we created a QR Code in PDF document using iText Java"));
//        //Step-5: Create QR Code by using BarcodeQRCode Class
//        BarcodeQRCode my_code = new BarcodeQRCode(data, 200, 400, null);
//        //Step-6: Get Image corresponding to the input string
//        Image qr_image = my_code.getImage();
//        //Step-7: Stamp the QR image into the PDF document
//        qr_code_Example.add(qr_image);
//
//
////        // create table
////
////        Paragraph paragraph = new Paragraph("This is ticket for Cinema");
////        qr_code_Example.add((Element) paragraph);
////
////        float[] columns = {30f, 30f, 300f};
////        Table table = new Table(columns);
////        table.addCell("Id");
////        table.addCell("userId");
////        table.addCell("title");
////
////        table.addCell(qrCodeName);
////        table.addCell(qr_image.toString());
////        table.addCell(my_code.toString());
////
////        qr_code_Example.add((Element) table);
//
//
//
//
//        //Step-8: Close the PDF document
//        qr_code_Example.close();
//        return path;
//    }


    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
//the BitMatrix class represents the 2D matrix of bits
//MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToPath(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path).toPath());
    }



    public void generatePdf(PdfTicketProjection projection) throws FileNotFoundException, MalformedURLException {
        String pdfPath = "E:\\Programming\\Unicorn\\Cinema ROOM Rest Service\\Cinema-room-b6\\src\\main\\resources\\NewTicket.pdf";
        PdfWriter pdfWriter = new PdfWriter(pdfPath);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();
        com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDocument);

        ImageData data = ImageDataFactory.create("E:\\Programming\\Unicorn\\Cinema ROOM Rest Service\\Cinema-room-b6\\src\\main\\resources\\static\\qrCode.png");
        Image image = new Image(data);
        image.setWidth(150);
        image.setHeight(150);

        float[] parentColumnWith = {300F, 300F};
        Table parentTable = new Table(parentColumnWith);

        float[] childColumnWith = {75F, 75F};
        Table childTable = new Table(childColumnWith);

        Paragraph movieName = new Paragraph("Movie -> ");
        Paragraph hall = new Paragraph("Hall -> ");
        Paragraph row = new Paragraph("Row -> ");
        Paragraph seat = new Paragraph("Seat -> ");
        Paragraph date = new Paragraph("Date -> ");

        childTable.addCell(new Cell().add(movieName));
        childTable.addCell(new Cell().add(new Paragraph(projection.getMovieTitle())));

        childTable.addCell(new Cell().add(hall));
        childTable.addCell(new Cell().add(new Paragraph(projection.getHallName())));

        childTable.addCell(new Cell().add(row));
        childTable.addCell(new Cell().add(new Paragraph(projection.getRowNumber().toString())));

        childTable.addCell(new Cell().add(seat));
        childTable.addCell(new Cell().add(new Paragraph(projection.getSeatNumber().toString())));

        childTable.addCell(new Cell().add(date));
        childTable.addCell(new Cell().add(new Paragraph(projection.getSessionDate().toString())));
        childTable.setFontSize(18);

        parentTable.addCell(new Cell().add(childTable));
        parentTable.addCell(new Cell().add(image));

        for (IElement child : childTable.getChildren()) {
            ((Cell)child).setBorder(Border.NO_BORDER);
        }
        for (IElement child : parentTable.getChildren()) {
            ((Cell) child).setBorder(Border.NO_BORDER);
        }

        document.add(parentTable);
        document.close();

    }


}
