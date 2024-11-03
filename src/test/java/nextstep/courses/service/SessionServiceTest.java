package nextstep.courses.service;

import nextstep.courses.domain.CannotRegisterException;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.payments.domain.Payment;
import nextstep.payments.service.PaymentService;
import nextstep.qna.CannotDeleteException;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private SessionService sessionService;

    Session session;
    Payment payment;

    @BeforeEach
    void setUp() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);
        session = new FreeSession(1L, "스프링 웹개발", startDate, endDate);
        payment = new Payment(1L, session.getId(), NsUserTest.SANJIGI.getId(), session.getPrice());
    }

    @Test
    @DisplayName("강의 수강신청 성공")
    void enrollSession1() {
        when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        when(paymentService.payment(session, NsUserTest.SANJIGI)).thenReturn(payment);

        session.openEnrollment();
        sessionService.enrollSession(NsUserTest.SANJIGI, session.getId());
    }

    @Test
    @DisplayName("모집중인 상태가 아닌경우 수강신청에 실패한다.")
    void enrollSession2() {
        when(sessionRepository.findById(session.getId())).thenReturn(Optional.of(session));
        when(paymentService.payment(session, NsUserTest.SANJIGI)).thenReturn(payment);

        assertThatThrownBy(() -> {
            sessionService.enrollSession(NsUserTest.SANJIGI, session.getId());
        }).isInstanceOf(CannotRegisterException.class);
    }
}