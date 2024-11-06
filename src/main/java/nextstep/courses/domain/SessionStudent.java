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

    public Long getStudentId() {
        return studentId;
    }

    public Long getSessionId() {
        return sessionId;
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
