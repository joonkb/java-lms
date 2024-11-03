package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.qna.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
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
    @DisplayName("FreeSession에 커버이미지를 등록한다")
    void saveCoverImage() {
//        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
//        LocalDateTime endDate = startDate.plusMonths(2);
//
//        Session session = new FreeSession(1L, "TDD 자바 클린코드", startDate, endDate);
//        sessionRepository.save(session);
//
//        CoverImage image = new CoverImage("자바짱이야", "pdf", 100, 300, 200);
//        session.uploadCoverImage(image);
    }
}
