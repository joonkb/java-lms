package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class PaidSession extends Session {

    private int maxEnrollment;

    public PaidSession(String title, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(1L, title, SessionType.PAID, price, startDate, endDate);
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(String title, SessionStatus status, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(1L, title, SessionType.PAID, status, price, startDate, endDate);
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(Long id, String title, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, SessionType.PAID, price, startDate, endDate);
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(Long id, String title, SessionStatus status, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, SessionType.PAID, status, price, startDate, endDate);
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(Long id, String title, String status, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, title, SessionStatus.valueOf(status), price, maxEnrollment, startDate, endDate);
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    @Override
    public void enroll(NsUser user, Payment payment) {
        canEnroll(payment);
        addStudent(user);
    }

    private void canEnroll(Payment payment) {

        validateRecruitingStatus();

        if (!price.equals(payment.getAmount())) {
            throw new CannotRegisterException("결제한 금액과 수강료가 일치하지 않습니다.");
        }

        if (isOverMaxEnrollment()) {
            throw new CannotRegisterException("최대 수강 인원을 초과하였습니다.");
        }
    }

    private boolean isOverMaxEnrollment() {
        return getCurrentStudentCount() >= maxEnrollment;
    }
}
