package br.pucgoias.ads.hotel.dominio;

import java.time.LocalDate;

/**
 * Objeto de valor que representa o periodo de uma reserva.
 *
 * TODO (Etapa 2):
 *  - anotar a classe como incorporavel (@Embeddable);
 *  - mapear as colunas check_in e check_out como obrigatorias;
 *  - validar no construtor a regra R1 (PeriodoInvalidoException);
 *  - implementar noites() e sobrepoe(Periodo);
 *  - implementar equals e hashCode com base nas duas datas.
 */
public class Periodo {

    private LocalDate checkIn;

    private LocalDate checkOut;

    /** Construtor exigido pela especificacao JPA. */
    protected Periodo() {
    }

    public Periodo(LocalDate checkIn, LocalDate checkOut) {
        // TODO: validar a regra R1 antes de atribuir
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public long noites() {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    public boolean sobrepoe(Periodo outro) {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }
}
