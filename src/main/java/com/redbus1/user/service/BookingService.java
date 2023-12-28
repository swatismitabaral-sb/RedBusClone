package com.redbus1.user.service;

import com.redbus1.operator.entity.BusOperator;
import com.redbus1.operator.entity.TicketCost;
import com.redbus1.operator.repository.BusOperatorRepository;
import com.redbus1.operator.repository.TicketCostRepository;
import com.redbus1.user.entity.Booking;
import com.redbus1.user.payload.BookingDetailsDto;
import com.redbus1.user.payload.PassengerDetails;
import com.redbus1.user.repository.BookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
public class BookingService {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    private BusOperatorRepository busOperatorRepository;
    private TicketCostRepository ticketCostRepository;
    private BookingRepository bookingRepository;
    public BookingService(BusOperatorRepository busOperatorRepository, TicketCostRepository ticketCostRepository, BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }

    public BookingDetailsDto createBooking(
            String busId,
            String ticketId,
            PassengerDetails passengerDetails
    ){

        BusOperator bus = busOperatorRepository.findById(busId).get();
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).get();

        String paymentIntent = createPaymentIntent( (int) ticketCost.getCost() );
        if(paymentIntent!=null) {


            Booking booking = new Booking();
            String bookingId = UUID.randomUUID().toString();
            booking.setBookingId( bookingId );
            booking.setBusId( busId );
            booking.setTicketId( Long.parseLong( ticketId ) );
            booking.setFromCity( bus.getArrivalCity() );
            booking.setToCity( bus.getDepartureCity() );
            booking.setBusCompany( bus.getBusOperatorCompanyName() );
            booking.setPrice( ticketCost.getCost() );
            booking.setFirstName( passengerDetails.getFirstName() );
            booking.setLastName( passengerDetails.getLastName() );
            booking.setEmail( passengerDetails.getEmail() );
            booking.setMobile( passengerDetails.getMobile() );

            Booking ticketCreatedDetails = bookingRepository.save( booking );

            BookingDetailsDto dto = new BookingDetailsDto();

            dto.setBookingId( ticketCreatedDetails.getBookingId() );
            dto.setFirstName( ticketCreatedDetails.getFirstName() );
            dto.setLastName( ticketCreatedDetails.getLastName() );
            dto.setPrice( ticketCreatedDetails.getPrice() );
            dto.setEmail( ticketCreatedDetails.getEmail() );
            dto.setMobile( ticketCreatedDetails.getMobile() );
            dto.setToCity( ticketCreatedDetails.getToCity() );
            dto.setFromCity( ticketCreatedDetails.getFromCity() );
            dto.setBusCompany( ticketCreatedDetails.getBusCompany() );
            dto.setMessage("Your Booking is Confirmed");

            return dto;
        }else{
            System.out.println("Error!");
        }
    return  null;
    }

//https://dashboard.stripe.com/test/payments-to check myPayments

    public String createPaymentIntent( Integer amount) {
        Stripe.apiKey = stripeSecretKey;
        try {
            PaymentIntent intent = PaymentIntent.create(
                    PaymentIntentCreateParams.builder()
                            .setCurrency("usd") // Set the currency as needed
                            .setAmount((long) amount * 100) // Convert amount to cents
                            .build()
            );
            return generateResponse(intent.getClientSecret());

        } catch (StripeException e) {
            return generateResponse("Error creating paymentIntent: " + e.getMessage());
        }
    }

    private String generateResponse(String clientSecret) {
        return "{\"clientSecret\": \"" + clientSecret + "\"}";
    }
}




