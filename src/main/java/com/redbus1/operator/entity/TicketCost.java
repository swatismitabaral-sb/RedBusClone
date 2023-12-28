package com.redbus1.operator.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Entity
@Table(name="ticket_cost")


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="ticket_id",unique = true)
    private long ticketId;
//
//    @OneToOne(mappedBy = "ticketCost")
//    @Column(name="bus_id")
//    private BusOperator busOperator;
   // private String busId;

    @OneToOne(mappedBy = "ticketCost", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BusOperator busOperator;

    private double cost;

    private String code;

    @Column(name = "discount_amount",unique = true)
    private double discountAmount;



}

