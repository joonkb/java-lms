package nextstep.courses.domain;

public class SessionStatus {

    private SessionProgressStatus progressStatus;
    private SessionRecruitmentStatus recruitmentStatus;

    public SessionStatus(SessionProgressStatus progressStatus, SessionRecruitmentStatus recruitmentStatus) {
        this.progressStatus = progressStatus;
        this.recruitmentStatus = recruitmentStatus;
    }

    public SessionStatus() {
        this(SessionProgressStatus.PREPARING, SessionRecruitmentStatus.CLOSE);
    }

    public SessionProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public SessionRecruitmentStatus getRecruitmentStatus() {
        return recruitmentStatus;
    }

    public void openSession() {
        this.recruitmentStatus = SessionRecruitmentStatus.OPEN;
    }

    public void startSession() {
        progressStatus = SessionProgressStatus.PROGRESS;
    }

    public boolean canEnroll() {
        return recruitmentStatus == SessionRecruitmentStatus.OPEN;
    }
}
