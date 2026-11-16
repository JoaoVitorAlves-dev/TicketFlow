    package com.ticketflow.romeuzxg.repository;

    import com.ticketflow.romeuzxg.domain.Assento;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
public interface AssentoRepository extends JpaRepository<Assento, Long> {
}
