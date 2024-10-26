package nextstep.courses.domain;

import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SessionTest {

    private static LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 1, 1);
    private static LocalDateTime endDate = startDate.plusMonths(2);

    @Test
    void 세션_생성() {
        Session session = new Session("TDD/클린코드", startDate, endDate);
        assertThat(session).isEqualTo(new Session("TDD/클린코드", startDate, endDate));
    }

    @Test
    void 수강신청_실패_모집중아님() {
        Session session = new Session("TDD/클린코드", startDate, endDate);
        assertThatThrownBy(() -> {
            session.enroll(NsUserTest.JAVAJIGI);
        });
    }

    @Test
    void 수강신청_실패_최대수강인원_초과() {
        Session session = new Session("TDD/클린코드", SessionType.PAID, 1000, 1, startDate, endDate);
        session.openEnrollment();
        session.enroll(NsUserTest.SANJIGI);
        assertThatThrownBy(() -> {
            session.enroll(NsUserTest.JAVAJIGI);
        }).hasMessage("최대 수강 인원을 초과하였습니다.");
    }

    @Test
    void 수강신청_성공() {
        Session session = new Session("TDD/클린코드", startDate, endDate);
        session.openEnrollment();
        session.enroll(NsUserTest.JAVAJIGI);
    }
}
