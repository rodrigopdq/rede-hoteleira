package br.pucgoias.ads.hotel.dominio;

import java.math.BigDecimal;

/**
 * TODO (Etapa 2):
 *  - mapear como entidade da hierarquia com valor discriminador "SUITE";
 *  - mapear taxaServico (coluna taxa_servico);
 *  - implementar calcularValor conforme a regra R3.
 */
public class QuartoSuite extends Quarto {

    private BigDecimal taxaServico;

    protected QuartoSuite() {
    }

    public QuartoSuite(String numero, BigDecimal valorDiaria, BigDecimal taxaServico) {
        super(numero, valorDiaria);
        this.taxaServico = taxaServico;
    }

    @Override
    public BigDecimal calcularValor(Periodo periodo) {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    public BigDecimal getTaxaServico() {
        return taxaServico;
    }
}
