package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;

public interface SessionEnrollmentRepository {

    int enrollStudent(Long sessionId, Long userId);

    List<SessionStudent> findStudentsBySessionId(Long sessionId);

    List<SessionStudent> findStudentsByEnrollmentStatus(Long sessionId, EnrollmentStatus status);

    int updateStudentEnrollmentStatus(SessionStudent student, EnrollmentStatus status);
}
