package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
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
            String sessionType = rs.getString("session_type");
            if ("FREE".equals(sessionType)) {
                return new FreeSession(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("session_status"),
                        rs.getTimestamp("started_at").toLocalDateTime(),
                        rs.getTimestamp("ended_at").toLocalDateTime()
                );
            } else if ("PAID".equals(sessionType)) {
                return new PaidSession(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("session_status"),
                        rs.getLong("price"),
                        rs.getInt("max_enrollment"),
                        rs.getTimestamp("started_at").toLocalDateTime(),
                        rs.getTimestamp("ended_at").toLocalDateTime()
                );
            }
            throw null;
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

    @Override
    public int enroll(Long sessionId, Long userId) {
        String sql = "insert into session_enrollment (user_id, session_id, registered_at) values(?, ?, ?)";
        return jdbcTemplate.update(sql, userId, sessionId, LocalDateTime.now());
    }

    @Override
    public List<NsUser> findEnrolledUsers(Long sessionId) {
        String sql = "select t1.id, t1.user_id, t1.password, t1.name, t1.email, t1.created_at, t1.updated_at from ns_user t1 join session_enrollment t2 on t1.id = t2.user_id where t2.session_id = ?";
        RowMapper<NsUser> rowMapper = (rs, rowNum) -> new NsUser(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                toLocalDateTime(rs.getTimestamp(6)),
                toLocalDateTime(rs.getTimestamp(7)));
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
