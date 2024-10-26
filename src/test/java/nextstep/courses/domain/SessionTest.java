package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    @Test
    void 세션_생성() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 1, 1);
        LocalDateTime endDate = startDate.plusMonths(2);
        Session session = new Session(startDate, endDate);
        assertThat(session).isEqualTo(new Session(startDate, endDate));
    }
}
