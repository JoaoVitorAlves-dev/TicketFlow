    package com.ticketflow.romeuzxg.repository;

    import com.ticketflow.romeuzxg.domain.Local;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
}
