package nextstep.courses.domain;

public class SessionStudent {

    private Long studentId;
    private Long sessionId;
    private EnrollmentStatus status;

    public SessionStudent(Long studentId, Long sessionId, EnrollmentStatus status) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.status = status;
    }

    public SessionStudent(Long studentId, Long sessionId, String status) {
        this.studentId = studentId;
        this.sessionId = sessionId;
        this.status = EnrollmentStatus.valueOf(status);
    }

    public void updateEnrollmentStatus(EnrollmentStatus status) {
        this.status = status;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "SessionStudent{" +
                "studentId=" + studentId +
                ", sessionId=" + sessionId +
                ", status=" + status +
                '}';
    }
}
