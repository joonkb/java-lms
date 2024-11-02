package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionType;
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
    @DisplayName("주어진 FreeSession이 정상적으로 저장되는지 확인한다.")
    void saveFreeSession() {
        LocalDateTime startDate = LocalDateTime.of(2024, 11, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);

        Session session = new FreeSession(1L, "TDD 자바 클린코드", SessionType.FREE, startDate, endDate);
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
    }

}
