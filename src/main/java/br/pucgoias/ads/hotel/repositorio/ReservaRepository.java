package br.pucgoias.ads.hotel.repositorio;

import br.pucgoias.ads.hotel.dominio.Reserva;
import br.pucgoias.ads.hotel.dominio.StatusReserva;
import br.pucgoias.ads.hotel.dto.OcupacaoQuarto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * TODO (Etapa 3): as assinaturas abaixo ja sao utilizadas pelo servico.
 * Cada metodo deve receber a anotacao adequada; enquanto isso nao ocorrer,
 * o contexto do Spring nao sera inicializado.
 */
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    /** R2: JPQL que verifica sobreposicao com reservas do status informado. */
    boolean existeConflito(Long quartoId, StatusReserva status, LocalDate checkIn, LocalDate checkOut);

    /** R5: consulta derivada paginada. */
    Page<Reserva> findByHospedeId(Long hospedeId, Pageable pageable);

    /** R6: projecao com expressao construtora (select new ... OcupacaoQuarto). */
    List<OcupacaoQuarto> relatorioOcupacao(StatusReserva status);

    /** R7: carregar hospede e quarto na mesma instrucao SQL. */
    List<Reserva> findByStatusOrderById(StatusReserva status);
}
