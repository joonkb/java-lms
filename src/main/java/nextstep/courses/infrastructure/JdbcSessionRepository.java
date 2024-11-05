package nextstep.courses.infrastructure;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, title, session_type, session_status, price, max_enrollment, started_at, ended_at from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            Session session = SessionMapper.rowToSession(rs);
            return session;
        };
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int save(Session session) {
        if (session.isFreeSession()) {
            String sql = "insert into session (id, title, session_type, session_status, started_at, ended_at) values(?, ?, ?, ?, ?, ?)";
            return jdbcTemplate.update(sql, session.getId(), session.getTitle(), session.getType().toString(), session.getStatus().toString(), session.getStartDate(), session.getEndDate());
        }
        String sql = "insert into session (id, title, session_type, session_status, max_enrollment, started_at, ended_at) values(?, ?, ?, ?, ?, ?, ?)";
        PaidSession paidSession = (PaidSession) session;
        return jdbcTemplate.update(sql, paidSession.getId(), paidSession.getTitle(), paidSession.getType().toString(), paidSession.getStatus().toString(), paidSession.getMaxEnrollment(), paidSession.getStartDate(), paidSession.getEndDate());
    }
}
