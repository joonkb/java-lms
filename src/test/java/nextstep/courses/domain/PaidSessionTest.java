package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.NsUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidSessionTest {

    private static LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 1, 1);
    private static LocalDateTime endDate = startDate.plusMonths(2);

    private SessionStudent student1 = new SessionStudent(NsUserTest.JAVAJIGI.getId(), 1L, EnrollmentStatus.APPROVED);
    private SessionStudent student2 = new SessionStudent(NsUserTest.SANJIGI.getId(), 1L, EnrollmentStatus.APPROVED);

    @Test
    @DisplayName("유료 강의는 강의 최대수강 인원을 초과할 수 없다.")
    void enrollTest01() {
        List<SessionStudent> students = List.of(student1, student2);
        Session session = new PaidSession("TDD/클린코드", 1000L, 2, startDate, endDate);
        session.openEnrollment();
        assertThatThrownBy(() -> {
            Payment payment2 = new Payment(1L, session.getId(), NsUserTest.JAVAJIGI.getId(), 1000L);
            session.enroll(students, payment2);
        }).hasMessage("최대 수강 인원을 초과하였습니다.");
    }

    @Test
    @DisplayName("유료 강의는 수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능하다.")
    void enrollTest02() {
        List<SessionStudent> students = List.of(student1);
        Session session = new PaidSession("TDD/클린코드", 1200L, 2, startDate, endDate);
        Payment payment = new Payment(1L, session.getId(), NsUserTest.JAVAJIGI.getId(), 1000L);
        session.openEnrollment();
        assertThatThrownBy(() -> {
            session.enroll(students, payment);
        }).hasMessage("결제한 금액과 수강료가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("수강신청에 성공한다.")
    void enrollTest03() {
        List<SessionStudent> students = List.of(student1);
        Session session = new PaidSession("TDD/클린코드", 1000L, 5, startDate, endDate);
        Payment payment = new Payment(1L, session.getId(), NsUserTest.SANJIGI.getId(), 1000L);
        session.openEnrollment();
        session.enroll(students, payment);
    }
}
