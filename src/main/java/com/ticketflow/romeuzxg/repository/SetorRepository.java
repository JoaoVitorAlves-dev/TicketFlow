    package com.ticketflow.romeuzxg.repository;

    import com.ticketflow.romeuzxg.domain.Setor;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
public interface SetorRepository extends JpaRepository<Setor, Long> {
}
