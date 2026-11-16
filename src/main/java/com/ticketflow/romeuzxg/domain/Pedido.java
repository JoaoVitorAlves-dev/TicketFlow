package com.ticketflow.romeuzxg.domain;

import com.ticketflow.romeuzxg.domain.Roles.StatusPedido;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = false)
    private StatusPedido statusPedido;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(name = "cupom_codigo", nullable = false)
    private String cupomCodigo;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @Column(nullable = false)
    private Long version;

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCupomCodigo() {
        return cupomCodigo;
    }

    public void setCupomCodigo(String cupomCodigo) {
        this.cupomCodigo = cupomCodigo;
    }

    public LocalDateTime getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(LocalDateTime expiraEm) {
        this.expiraEm = expiraEm;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
