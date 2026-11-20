package com.ticketflow.romeuzxg.domain;

import com.ticketflow.romeuzxg.domain.Roles.StatusIngresso;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_ingressos")
public class Ingresso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sessao_id")
    private Sessao sessao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assento_id")
    private Assento assento;

    @Column(nullable = false, name = "codigo_qr")
    private UUID codigoQr;

    @Enumerated(EnumType.STRING)
    private StatusIngresso status;

    @PrePersist
    public void gerarQrCode() {
        if (this.codigoQr == null) {
            this.codigoQr = UUID.randomUUID();
        }
    }

    public Ingresso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    public Assento getAssento() {
        return assento;
    }

    public void setAssento(Assento assento) {
        this.assento = assento;
    }

    public UUID getCodigoQr() {
        return codigoQr;
    }

    public void setCodigoQr(UUID codigoQr) {
        this.codigoQr = codigoQr;
    }

    public StatusIngresso getStatus() {
        return status;
    }

    public void setStatus(StatusIngresso status) {
        this.status = status;
    }
}
