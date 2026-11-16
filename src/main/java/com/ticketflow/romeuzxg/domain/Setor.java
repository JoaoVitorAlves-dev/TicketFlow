package com.ticketflow.romeuzxg.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_setor")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "local_id")
    private Local local;

    @Column(nullable = false)
    private String nome;

    @Column(name = "preco_base", nullable = false)
    private BigDecimal precoBase;

    public Setor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }
}
