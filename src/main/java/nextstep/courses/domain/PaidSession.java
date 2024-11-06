package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.List;

public class PaidSession extends Session {

    private int maxEnrollment;

    public PaidSession(String title, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(1L, title, SessionType.PAID, price, new Period(startDate, endDate));
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(Long id, String title, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, SessionType.PAID, price, new Period(startDate, endDate));
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(Long id, String title, SessionStatus status, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, SessionType.PAID, status, price,new Period(startDate, endDate));
        this.maxEnrollment = maxEnrollment;
    }

    public PaidSession(Long id, String title, String progressStatus, String recruitmentStatus, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        this(id, title, covertStringToStatusEnum(progressStatus, recruitmentStatus), price, maxEnrollment, startDate, endDate);
    }

    private static SessionStatus covertStringToStatusEnum(String progressStatus, String recruitmentStatus) {
        return new SessionStatus(SessionProgressStatus.valueOf(progressStatus), SessionRecruitmentStatus.valueOf(recruitmentStatus));
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    @Override
    public void enroll(List<SessionStudent> students, Payment payment) {
        canEnroll(students, payment);
    }

    private void canEnroll(List<SessionStudent> students, Payment payment) {

        validateRecruitingStatus();

        if (!price.equals(payment.getAmount())) {
            throw new CannotRegisterException("결제한 금액과 수강료가 일치하지 않습니다.");
        }

        if (isOverMaxEnrollment(students)) {
            throw new CannotRegisterException("최대 수강 인원을 초과하였습니다.");
        }
    }

    private boolean isOverMaxEnrollment(List<SessionStudent> students) {
        return students.size() >= maxEnrollment;
    }
}
