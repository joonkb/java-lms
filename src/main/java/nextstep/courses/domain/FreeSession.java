package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.time.LocalDateTime;
import java.util.List;

public class FreeSession extends Session {

    public FreeSession(Long id, String title, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, SessionType.FREE, 0L, new Period(startDate, endDate));
    }

    public FreeSession(Long id, String title, SessionType type, SessionStatus status, LocalDateTime startDate, LocalDateTime endDate) {
        super(id, title, type, status, 0L, new Period(startDate, endDate));
    }

    public FreeSession(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, startDate, endDate);
    }

    public FreeSession(Long id, String title, String progressStatus, String recruitmentStatus, LocalDateTime startedAt, LocalDateTime endedAt) {
        this(id, title, SessionType.FREE, covertStringToStatusEnum(progressStatus, recruitmentStatus), startedAt, endedAt);
    }

    private static SessionStatus covertStringToStatusEnum(String progressStatus, String recruitmentStatus) {
        return new SessionStatus(SessionProgressStatus.valueOf(progressStatus), SessionRecruitmentStatus.valueOf(recruitmentStatus));
    }

    @Override
    public void enroll(List<SessionStudent> students, Payment payment) {
        validateRecruitingStatus();
    }
}
