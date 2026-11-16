package com.ticketflow.romeuzxg.repository;

import com.ticketflow.romeuzxg.domain.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {
}
