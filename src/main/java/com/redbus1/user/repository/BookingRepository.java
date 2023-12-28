package com.redbus1.user.repository;

import com.redbus1.user.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking,String> {


}
