package com.redbus1.user.controller;

import com.redbus1.user.payload.BookingDetailsDto;
import com.redbus1.user.payload.PassengerDetails;
import com.redbus1.user.service.BookingService;
import com.redbus1.util.EmailService;
import com.redbus1.util.PdfGenerationService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;
    private EmailService emailService;

    private final PdfGenerationService pdfGenerationService;

    public BookingController(BookingService bookingService, EmailService emailService, PdfGenerationService pdfGenerationService) {
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.pdfGenerationService = pdfGenerationService;
    }

    //http://localhost:8082/api/bookings?busId=&ticketId=
//    @PostMapping
//    public ResponseEntity<BookingDetailsDto>bookBus(
//            @RequestParam("busId")String busId,
//            @RequestParam("ticketId")String ticketId,
//            @RequestBody PassengerDetails passengerDetails
//    ){
//
//        BookingDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);
//        if(booking!=null){
//            emailService.sendEmail(passengerDetails.getEmail(),
//                    "Thank You!Your Booking is confirm with us"+booking.getBookingId(),
//                    "Your booking is confirmed\n Name:"+passengerDetails.getFirstName()+" "+passengerDetails.getLastName());
//
//        }
//        return new ResponseEntity<>(booking, HttpStatus.OK);
//
//
//    }
    @PostMapping
    public ResponseEntity<BookingDetailsDto> bookBus(
            @RequestParam("busId") String busId,
            @RequestParam("ticketId") String ticketId,
            @RequestBody PassengerDetails passengerDetails
    ) throws MessagingException {
        BookingDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);
        if (booking != null) {
                  byte[]pdfBytes=pdfGenerationService.generatePdf(booking);
                  sendBookingConfirmationEmailWithAttachment(passengerDetails,booking,pdfBytes);
//
//            emailService.sendEmail(passengerDetails.getEmail(),
//                    "Thank You! Your Booking is confirmed with us " + booking.getBookingId(),
//                    "Your booking is confirmed\nName: " + passengerDetails.getFirstName() + " " + passengerDetails.getLastName());
//
//            pdfGenerationService.generatePdf(booking); // Generate PDF for the booking details
      }
         return new ResponseEntity<>(booking, HttpStatus.OK);



    }
    private void sendBookingConfirmationEmailWithAttachment(PassengerDetails passengerDetails, BookingDetailsDto booking, byte[] pdfBytes) throws MessagingException {

        String emailSubject = "Booking Confirmed.Booking Id:" + booking.getBookingId();
        String emailBody = String.format("Your Booking is Confirmed\nName: %s %s",
                passengerDetails.getFirstName(), passengerDetails.getLastName());

        emailService.sendEmailWithAttachment(
                passengerDetails.getEmail(), emailSubject, emailBody, pdfBytes, "BookingConfirmation.pdf");
    }



//    private void sendBookingConfirmationEmailWithAttatechment(PassengerDetails passengerDetails, BookingDetailsDto booking, byte[] pdfBytes) throws MessagingException {
//
//        String emailSubject="Booking Confirmed.Booking Id:"+booking.getBookingId();
//        String emailBody=String.format("your Booking is Confirmed\nName: %s %s",
//                passengerDetails.getFirstName(),passengerDetails.getLastName());
//
//        emailService.sendEmailWithAttachment(
//                passengerDetails.getEmail(), emailSubject,emailBody,pdfBytes,"Booking Confirmation");
//    }
}




