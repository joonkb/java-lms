package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;

public class FreeSession extends Session {

    public FreeSession(Long id, String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, SessionType.FREE, 0L, startDate, endDate);
    }

    public FreeSession(Long id, String title, SessionType type, SessionStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, type, status, 0L, startDate, endDate);
    }

    public FreeSession(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, startDate, endDate);
    }

    public FreeSession(String title, SessionStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, SessionType.FREE, status, startDate, endDate);
    }

    public FreeSession(Long id, String title, String status, LocalDateTime startedAt, LocalDateTime endedAt) {
        this(id, title, SessionType.FREE, SessionStatus.valueOf(status), startedAt, endedAt);
    }

    @Override
    public void enroll(NsUser user, Payment payment) {
        validateRecruitingStatus();
        addStudent(user);
    }
}
