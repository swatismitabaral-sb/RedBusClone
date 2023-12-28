package com.redbus1.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.redbus1.user.payload.BookingDetailsDto;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class PdfGenerationService {

    public byte[] generatePdf(BookingDetailsDto bookingDetails) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(writer)) {

            try (Document document = new Document(pdfDocument)) {
                // Adding content to the PDF
                document.add(new Paragraph("Booking Details"));
                document.add(new Paragraph("Booking ID: " + bookingDetails.getBookingId()));
                document.add(new Paragraph("Bus Company: " + bookingDetails.getBusCompany()));
                document.add(new Paragraph("From City: " + bookingDetails.getFromCity()));
                document.add(new Paragraph("To City: " + bookingDetails.getToCity()));
                document.add(new Paragraph("Name: " + bookingDetails.getFirstName() + " " + bookingDetails.getLastName()));
                document.add(new Paragraph("Email: " + bookingDetails.getEmail()));
                document.add(new Paragraph("Mobile: " + bookingDetails.getMobile()));
                document.add(new Paragraph("Price: " + bookingDetails.getPrice()));
            }

            return outputStream.toByteArray();
        } catch (java.io.IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return null;
    }
}
