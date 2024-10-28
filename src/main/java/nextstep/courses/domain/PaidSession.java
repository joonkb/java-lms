package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class PaidSession extends Session {

    private Long price;
    private int maxEnrollment;

    public PaidSession(String title, SessionType type, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        super(1L, title, type, startDate, endDate);
        this.price = price;
        this.maxEnrollment = maxEnrollment;
    }

    public Long getPrice() {
        return price;
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
