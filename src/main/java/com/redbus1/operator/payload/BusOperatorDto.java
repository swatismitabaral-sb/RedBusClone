package com.redbus1.operator.payload;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.redbus1.operator.entity.TicketCost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperatorDto {

    private String busId;
    private String busNumber;
    private String busOperatorCompanyName;
    private String driverName;
    private String supportStaff;
    private int numberSeats;
    private String departureCity;
    private String arrivalCity;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime arrivalTime;
    @JsonFormat(pattern = "dd/MM/YYYY")
    private Date departureDate;
    @JsonFormat(pattern = "dd/MM/YYYY")
    private Date arrivalDate;
    private double totalTravelTime;
    private String busType;
    private String ammenities;


    private TicketCost ticketCost;




}
