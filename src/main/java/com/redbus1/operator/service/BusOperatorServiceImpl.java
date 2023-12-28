package com.redbus1.operator.service;

import com.redbus1.operator.entity.BusOperator;
import com.redbus1.operator.entity.TicketCost;
import com.redbus1.operator.payload.BusOperatorDto;
import com.redbus1.operator.repository.BusOperatorRepository;
import com.redbus1.operator.repository.TicketCostRepository;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusOperatorServiceImpl implements BusOperatorService{

    private  ModelMapper mapper;
    private TicketCostRepository ticketCostRepository;
    private BusOperatorRepository busOperatorRepository;

    @Autowired
    public  BusOperatorServiceImpl(BusOperatorRepository busOperatorRepository,TicketCostRepository ticketCostRepository
            ,ModelMapper mapper){
        this.busOperatorRepository=busOperatorRepository;
        this.ticketCostRepository=ticketCostRepository;
        this.mapper=mapper;
    }
    @Override
    public BusOperatorDto scheduleBus(BusOperatorDto busOperatorDto) {
        BusOperator busOperator=mapToEntity(busOperatorDto);

        TicketCost ticketCost=new TicketCost();
        ticketCost.setTicketId(busOperatorDto.getTicketCost().getTicketId());
        ticketCost.setCost(busOperatorDto.getTicketCost().getCost());
        ticketCost.setCode(busOperatorDto.getTicketCost().getCode());
        ticketCost.setDiscountAmount(busOperatorDto.getTicketCost().getDiscountAmount());
        busOperator.setTicketCost(ticketCost);

        String busId= UUID.randomUUID().toString();
        busOperator.setBusId(busId);
//        busOperator.setArrivalCity(busOperatorDto.getArrivalCity());
//        busOperator.setDepartureCity(busOperatorDto.getDepartureCity());
//        busOperator.setNumberSeats(busOperator.getNumberSeats());
//        busOperator.setDepartureTime(busOperator.getDepartureTime());
//        busOperator.setArrivalTime(busOperator.getArrivalTime());
//        busOperator.setSupportStaff(busOperator.getSupportStaff());
//        busOperator.setBusOperatorCompanyName(busOperator.getBusOperatorCompanyName());
//        busOperator.setAmmenities(busOperator.getAmmenities());
//        busOperator.setTotalTravelTime(busOperator.getTotalTravelTime());
//        busOperator.setBusType(busOperator.getBusType());
//        busOperator.setDriverName(busOperator.getDriverName());



        BusOperator savedBusSchedule=busOperatorRepository.save(busOperator);
        return mapToDto(savedBusSchedule);

    }
   BusOperator mapToEntity(BusOperatorDto busOperatorDto) {
        BusOperator busOperator=mapper.map(busOperatorDto,BusOperator.class);
        return  busOperator;
       }

   BusOperatorDto mapToDto(BusOperator busOperator) {
        BusOperatorDto busOperatorDto=mapper.map(busOperator,BusOperatorDto.class);
        return  busOperatorDto;

    }

}

