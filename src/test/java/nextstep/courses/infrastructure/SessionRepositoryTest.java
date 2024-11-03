package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.qna.NotFoundException;
import nextstep.users.domain.NsUser;
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

@JdbcTest
class SessionRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    private JdbcUserRepository userRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        userRepository = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("주어진 FreeSession을 저장히고 조회한다.")
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
    @DisplayName("주어진 PaidSession을 저장히고 조회한다.")
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
        int count = sessionRepository.enroll(session.getId(), user.getId());
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("주어진 Session을 수강하고 있는 학생들을 조회한다.")
    void enrollAndFindStudents() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(2L, "TDD 자바 클린코드", startDate, endDate);
        sessionRepository.save(session);

        NsUser user1 = userRepository.findByUserId("javajigi").orElseThrow();
        NsUser user2 = userRepository.findByUserId("sanjigi").orElseThrow();

        int result = 0;
        result += sessionRepository.enroll(session.getId(), user1.getId());
        result += sessionRepository.enroll(session.getId(), user2.getId());

        List<NsUser> enrolledUsers = sessionRepository.findEnrolledUsers(session.getId());
        assertThat(enrolledUsers).hasSize(result);
    }
}
