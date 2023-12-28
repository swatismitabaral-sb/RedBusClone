package com.redbus1.user.service;

import com.redbus1.operator.entity.BusOperator;
import com.redbus1.operator.repository.BusOperatorRepository;
import com.redbus1.user.payload.BusListDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchBusesService {

    private BusOperatorRepository busOperatorRepository;

    public SearchBusesService(BusOperatorRepository busOperatorRepository) {
        this.busOperatorRepository = busOperatorRepository;
    }


    public List<BusListDto> searchBusBy(String departureCity, String arrivalCity, Date departureDate){

        List<BusOperator> busesAvailable = busOperatorRepository.searchByCitiesAndDate(departureCity, arrivalCity, departureDate);
        List<BusListDto> dtos = busesAvailable.stream().map(bus -> mapToDto(bus)).collect(Collectors.toList());

        return  dtos;
    }
    BusListDto mapToDto(BusOperator busOperator){
        BusListDto busListDto=new BusListDto();

        busListDto.setBusId(busOperator.getBusId());
        busListDto.setBusNumber(busOperator.getBusNumber());
        busListDto.setBusType(busOperator.getBusType());
        busListDto.setDepartureDate(busOperator.getDepartureDate());
        busListDto.setArrivalDate(busOperator.getArrivalDate());
        busListDto.setDepartureTime(busOperator.getDepartureTime());
        busListDto.setArrivalTime(busOperator.getArrivalTime());
        busListDto.setTotalTravelTime(busOperator.getTotalTravelTime());
        busListDto.setNumberSeats(busOperator.getNumberSeats());
        busListDto.setAmmenities(busOperator.getAmmenities());
        busListDto.setDepartureCity(busOperator.getDepartureCity());
        busListDto.setArrivalCity(busOperator.getArrivalCity());
        return busListDto;


    }

}

