package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;

public interface SessionEnrollmentRepository {

    int enrollStudent(Long sessionId, Long userId);

    List<NsUser> findStudentsBySessionId(Long sessionId);
}
