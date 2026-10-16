package br.pucgoias.ads.hotel.servico;

import br.pucgoias.ads.hotel.dominio.Hospede;
import br.pucgoias.ads.hotel.dominio.Periodo;
import br.pucgoias.ads.hotel.dominio.Quarto;
import br.pucgoias.ads.hotel.dominio.Reserva;
import br.pucgoias.ads.hotel.dominio.StatusReserva;
import br.pucgoias.ads.hotel.dto.OcupacaoQuarto;
import br.pucgoias.ads.hotel.excecao.QuartoIndisponivelException;
import br.pucgoias.ads.hotel.excecao.RecursoNaoEncontradoException;
import br.pucgoias.ads.hotel.repositorio.HospedeRepository;
import br.pucgoias.ads.hotel.repositorio.QuartoRepository;
import br.pucgoias.ads.hotel.repositorio.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservaService {

    private final HospedeRepository hospedes;
    private final QuartoRepository quartos;
    private final ReservaRepository reservas;

    public ReservaService(HospedeRepository hospedes, QuartoRepository quartos, ReservaRepository reservas) {
        this.hospedes = hospedes;
        this.quartos = quartos;
        this.reservas = reservas;
    }

    /** R1, R2 e R3: operacao fornecida como referencia. */
    @Transactional
    public Reserva reservar(Long hospedeId, Long quartoId, LocalDate checkIn, LocalDate checkOut) {
        Hospede hospede = hospedes.findById(hospedeId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Hospede", hospedeId));
        Quarto quarto = quartos.findById(quartoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Quarto", quartoId));

        Periodo periodo = new Periodo(checkIn, checkOut);

        if (reservas.existeConflito(quartoId, StatusReserva.ATIVA, checkIn, checkOut)) {
            throw new QuartoIndisponivelException(quarto.getNumero());
        }
        return reservas.save(new Reserva(hospede, quarto, periodo));
    }

    /** R4: TODO - localizar a reserva e cancela-la sem chamar save. */
    @Transactional
    public void cancelar(Long reservaId) {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /** R5: TODO - pagina solicitada, ordenada por periodo.checkIn decrescente. */
    public Page<Reserva> listarPorHospede(Long hospedeId, int pagina, int tamanho) {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /** R6: TODO - somente reservas ATIVAS. */
    public List<OcupacaoQuarto> relatorioOcupacao() {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    /** R7: TODO - reservas ATIVAS com hospede e quarto carregados. */
    public List<Reserva> listarAtivasComDetalhes() {
        throw new UnsupportedOperationException("TODO: implementar");
    }

    @Transactional
    public Quarto reajustarDiaria(Long quartoId, BigDecimal novoValor) {
        Quarto quarto = quartos.findById(quartoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Quarto", quartoId));
        quarto.reajustarDiaria(novoValor);
        return quarto;
    }
}
