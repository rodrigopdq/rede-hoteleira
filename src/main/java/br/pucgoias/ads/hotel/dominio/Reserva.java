package br.pucgoias.ads.hotel.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * TODO (Etapa 2): completar o mapeamento.
 *  - hospede e quarto: muitos-para-um, carregamento LAZY, obrigatorios;
 *  - periodo: objeto incorporado;
 *  - status: enumeracao gravada como texto;
 *  - valorTotal: coluna valor_total, precisao 12, escala 2;
 *  - criadaEm: preenchida por callback antes do INSERT, nao atualizavel;
 *  - versao: controle de concorrencia otimista.
 */
@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Hospede hospede;

    private Quarto quarto;

    private Periodo periodo;

    private StatusReserva status;

    private BigDecimal valorTotal;

    private LocalDateTime criadaEm;

    protected Reserva() {
    }

    public Reserva(Hospede hospede, Quarto quarto, Periodo periodo) {
        this.hospede = hospede;
        this.quarto = quarto;
        this.periodo = periodo;
        this.status = StatusReserva.ATIVA;
        this.valorTotal = quarto.calcularValor(periodo);
    }

    // TODO: metodo de callback que registra a data e hora de criacao

    /** Regra R4: alterar apenas o estado do objeto. */
    public void cancelar() {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    public Long getId() {
        return id;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }
}
