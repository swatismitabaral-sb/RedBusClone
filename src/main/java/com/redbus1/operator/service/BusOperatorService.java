package com.redbus1.operator.service;

import com.redbus1.operator.payload.BusOperatorDto;
import org.springframework.web.bind.annotation.RequestBody;

public interface BusOperatorService {
    BusOperatorDto scheduleBus (@RequestBody BusOperatorDto busOperatorDto);

}
