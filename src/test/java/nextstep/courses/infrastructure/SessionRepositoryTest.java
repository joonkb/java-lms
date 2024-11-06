package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import nextstep.payments.domain.Payment;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class SessionRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    private SessionEnrollmentRepository sessionEnrollmentRepository;

    private JdbcUserRepository userRepository;

    private SessionStudent student1 = new SessionStudent(NsUserTest.JAVAJIGI.getId(), 1L, EnrollmentStatus.APPROVED);

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        userRepository = new JdbcUserRepository(jdbcTemplate);
        sessionEnrollmentRepository = new JdbcSessionEnrollmentRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("주어진 FreeSession을 저장하고 조회한다.")
    void saveFreeSessionAndFindById() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);

        Session savedSession = sessionRepository.findById(2L).orElseThrow(NotFoundException::new);
        assertThat(savedSession.getTitle()).isEqualTo(savedSession.getTitle());
        LOGGER.debug("Session: {}", savedSession);
    }

    @Test
    @DisplayName("주어진 PaidSession을 저장하고 조회한다.")
    void savePaidSessionAndFindById() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new PaidSession(3L, "TDD 자바 클린코드", 50000L, 60, startDate, endDate);
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);

        Session savedSession = sessionRepository.findById(3L).orElseThrow(NotFoundException::new);
        assertThat(savedSession.getTitle()).isEqualTo(savedSession.getTitle());
        LOGGER.debug("Session: {}", savedSession);
    }

    @Test
    @DisplayName("주어진 FreeSession에 javajigi가 수강신청을 수행한다.")
    void enrollStudent() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        sessionRepository.save(session);

        NsUser user = userRepository.findByUserId("javajigi").orElseThrow();
        int count = sessionEnrollmentRepository.enrollStudent(session.getId(), user.getId());
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("진행중이면서 모집중인 Session에 수강신청을 할 수 있다.")
    void enrollStudentTest01() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        session.startSession();
        session.openEnrollment();
        sessionRepository.save(session);

        List<SessionStudent> students = List.of(student1);
        session.enroll(students, new Payment(1L, session.getId(), student1.getStudentId(), 0L));
        int count = sessionEnrollmentRepository.enrollStudent(session.getId(), student1.getStudentId());
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("진행중이면서 비모집중인 Session에 수강신청을 할 수 없다.")
    void enrollStudentTest02() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        session.startSession();
        sessionRepository.save(session);

        List<SessionStudent> students = List.of(student1);
        assertThatThrownBy(() -> {
            session.enroll(students, new Payment(1L, session.getId(), student1.getStudentId(), 0L));
        });
    }
}
