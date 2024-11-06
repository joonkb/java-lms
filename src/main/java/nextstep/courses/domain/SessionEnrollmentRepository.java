package nextstep.courses.domain;

import java.util.List;

public interface SessionEnrollmentRepository {

    int enrollStudent(Long sessionId, Long userId);

    int updateStudentEnrollmentStatus(SessionStudent student);

    SessionStudent findStudentById(SessionStudent student);

    List<SessionStudent> findStudentsBySessionId(Long sessionId);

    List<SessionStudent> findStudentsByEnrollmentStatus(Long sessionId, EnrollmentStatus status);
}
