package com.ticketflow.romeuzxg.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_checkin")
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingresso_id")
    private Ingresso ingresso;

    @CreationTimestamp
    @Column(name = "feito_em", nullable = false)
    private LocalDateTime feitoEm;

    @Column(nullable = false)
    private String portaria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }

    public LocalDateTime getFeitoEm() {
        return feitoEm;
    }

    public void setFeitoEm(LocalDateTime feitoEm) {
        this.feitoEm = feitoEm;
    }

    public String getPortaria() {
        return portaria;
    }

    public void setPortaria(String portaria) {
        this.portaria = portaria;
    }
}
