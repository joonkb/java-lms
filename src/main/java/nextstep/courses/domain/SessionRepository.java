package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Optional<Session> findById(Long id);

    int save(Session session);

    int enroll(Long sessionId, Long userId);

    List<NsUser> findEnrolledUsers(Long sessionId);
}
