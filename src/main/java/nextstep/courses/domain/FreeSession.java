package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    public FreeSession(Long id, String title, SessionType type, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, type, startDate, endDate);
    }

    public FreeSession(String title, SessionType type, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, type, startDate, endDate);
    }

    @Override
    public void enroll(NsUser user, Payment payment) {
        validateRecruitingStatus();
        addStudent(user);
    }
}
