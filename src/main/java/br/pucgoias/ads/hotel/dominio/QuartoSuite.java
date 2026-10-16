package br.pucgoias.ads.hotel.dominio;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SUITE")
public class QuartoSuite extends Quarto {

    @Column(name = "taxa_servico", precision = 10, scale = 2)
    private BigDecimal taxaServico;

    protected QuartoSuite() {
    }

    public QuartoSuite(String numero, BigDecimal valorDiaria, BigDecimal taxaServico) {
        super(numero, valorDiaria);
        this.taxaServico = taxaServico;
    }

    @Override
    public BigDecimal calcularValor(Periodo periodo) {
        return valorDasDiarias(periodo).add(taxaServico);
    }

    public BigDecimal getTaxaServico() {
        return taxaServico;
    }
}