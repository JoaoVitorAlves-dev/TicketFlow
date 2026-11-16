package com.ticketflow.romeuzxg.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tb_organizador")
    private Organizador organizador;

    private String titulo;


}
