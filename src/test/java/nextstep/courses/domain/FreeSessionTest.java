package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FreeSessionTest {

    private static LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 1, 1);
    private static LocalDateTime endDate = startDate.plusMonths(2);

    @Test
    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다.")
    void enrollTest01() {
        Session session = new FreeSession("TDD/클린코드", startDate, endDate);
        Payment payment = new Payment(1L, session.getId(), NsUserTest.JAVAJIGI.getId(), 0L);
        SessionStudent student = new SessionStudent(NsUserTest.JAVAJIGI.getId(), 1L, EnrollmentStatus.APPROVED);
        List<SessionStudent> students = List.of(student);
        assertThatThrownBy(() -> {
            session.enroll(students, payment);
        }).hasMessage("현재 모집중인 상태가 아닙니다.");
    }

    @Test
    @DisplayName("")
    void 수강신청_성공() {
        Session session = new FreeSession("TDD/클린코드", startDate, endDate);
        Payment payment = new Payment(1L, session.getId(), NsUserTest.SANJIGI.getId(), 0L);
        SessionStudent student = new SessionStudent(NsUserTest.JAVAJIGI.getId(), 1L, EnrollmentStatus.APPROVED);
        List<SessionStudent> students = List.of(student);
        session.openEnrollment();
        session.enroll(students, payment);
    }
}
