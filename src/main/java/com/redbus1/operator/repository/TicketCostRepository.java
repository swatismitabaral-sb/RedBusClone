package com.redbus1.operator.repository;

import com.redbus1.operator.entity.TicketCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCostRepository extends JpaRepository<TicketCost ,String> {
}
