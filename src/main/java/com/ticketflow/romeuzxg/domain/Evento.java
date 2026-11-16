package com.ticketflow.romeuzxg.domain;

import com.ticketflow.romeuzxg.domain.Roles.CategoriaEvento;
import com.ticketflow.romeuzxg.domain.Roles.ClassificacaoIndicativa;
import com.ticketflow.romeuzxg.domain.Roles.StatusEvento;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_eventos")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizador_id")
    private Organizador organizador;

    @Column(nullable = false)
    private String titulo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaEvento categoriaEvento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusEvento statusEvento;

    @Column(nullable = false)
    private ClassificacaoIndicativa classificacao;

    @Column(nullable = false)
    private Boolean ativo = true;

    public Evento() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public CategoriaEvento getCategoriaEvento() {
        return categoriaEvento;
    }

    public void setCategoriaEvento(CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    public StatusEvento getStatusEvento() {
        return statusEvento;
    }

    public void setStatusEvento(StatusEvento statusEvento) {
        this.statusEvento = statusEvento;
    }

    public ClassificacaoIndicativa getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(ClassificacaoIndicativa classificacao) {
        this.classificacao = classificacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
