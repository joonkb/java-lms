package nextstep.courses.infrastructure;

import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;

import java.util.Optional;

public class JdbcSessionRepository implements SessionRepository {
    @Override
    public Optional<Session> findById(Long id) {
        return Optional.empty();
    }
}
