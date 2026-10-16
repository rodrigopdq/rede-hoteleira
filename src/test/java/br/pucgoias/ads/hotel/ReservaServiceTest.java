package br.pucgoias.ads.hotel;

import br.pucgoias.ads.hotel.dominio.Hospede;
import br.pucgoias.ads.hotel.dominio.Quarto;
import br.pucgoias.ads.hotel.dominio.QuartoStandard;
import br.pucgoias.ads.hotel.dominio.QuartoSuite;
import br.pucgoias.ads.hotel.dominio.Reserva;
import br.pucgoias.ads.hotel.excecao.PeriodoInvalidoException;
import br.pucgoias.ads.hotel.excecao.QuartoIndisponivelException;
import br.pucgoias.ads.hotel.servico.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(ReservaService.class)
class ReservaServiceTest {

    private static final LocalDate DIA_1 = LocalDate.of(2026, 10, 1);

    @Autowired
    private ReservaService service;

    @Autowired
    private TestEntityManager em;

    private Hospede ana;
    private Hospede bruno;
    private Quarto standard;
    private Quarto suite;

    @BeforeEach
    void prepararDados() {
        ana = em.persist(new Hospede("Ana Souza", "11111111111", "ana@email.com"));
        bruno = em.persist(new Hospede("Bruno Lima", "22222222222", "bruno@email.com"));
        standard = em.persist(new QuartoStandard("101", new BigDecimal("200.00")));
        suite = em.persist(new QuartoSuite("501", new BigDecimal("500.00"), new BigDecimal("150.00")));
        em.flush();
    }

    // ---------------------------------------------------------------
    // Casos 1 a 5: fornecidos no projeto inicial
    // ---------------------------------------------------------------

    @Test
    @DisplayName("1. R1 - check-out igual ao check-in lanca PeriodoInvalidoException")
    void periodoInvalido() {
        assertThatThrownBy(() -> service.reservar(ana.getId(), standard.getId(), DIA_1, DIA_1))
                .isInstanceOf(PeriodoInvalidoException.class);
    }

    @Test
    @DisplayName("2. R2 - reserva sobreposta no mesmo quarto lanca QuartoIndisponivelException")
    void conflitoDeReserva() {
        service.reservar(ana.getId(), standard.getId(), DIA_1, DIA_1.plusDays(3));

        assertThatThrownBy(() ->
                service.reservar(bruno.getId(), standard.getId(), DIA_1.plusDays(2), DIA_1.plusDays(5)))
                .isInstanceOf(QuartoIndisponivelException.class)
                .hasMessageContaining("101");
    }

    @Test
    @DisplayName("3. R2 - reserva cancelada nao bloqueia o periodo")
    void reservaCanceladaNaoBloqueia() {
        Reserva primeira = service.reservar(ana.getId(), standard.getId(), DIA_1, DIA_1.plusDays(3));
        service.cancelar(primeira.getId());

        Reserva segunda = service.reservar(bruno.getId(), standard.getId(), DIA_1, DIA_1.plusDays(3));

        assertThat(segunda.getId()).isNotNull();
    }

    @Test
    @DisplayName("4. R3 - quarto standard: diaria x noites")
    void valorQuartoStandard() {
        Reserva reserva = service.reservar(ana.getId(), standard.getId(), DIA_1, DIA_1.plusDays(3));

        assertThat(reserva.getValorTotal()).isEqualByComparingTo("600.00");
    }

    @Test
    @DisplayName("5. R3 - suite: diaria x noites + taxa de servico")
    void valorSuite() {
        Reserva reserva = service.reservar(ana.getId(), suite.getId(), DIA_1, DIA_1.plusDays(2));

        assertThat(reserva.getValorTotal()).isEqualByComparingTo("1150.00");
    }

    // ---------------------------------------------------------------
    // Casos 6 a 9: TODO (Etapa 5) - escritos pelo aluno
    //   6. R4 - cancelamento persistido por dirty checking
    //          (utilizar em.flush() e em.clear() antes de recarregar)
    //   7. R5 - listagem paginada e ordenada por check-in decrescente
    //   8. R6 - relatorio de ocupacao por projecao, apenas reservas ativas
    //   9. R7 - uma unica instrucao SQL (utilizar org.hibernate.stat.Statistics)
    // ---------------------------------------------------------------
}
