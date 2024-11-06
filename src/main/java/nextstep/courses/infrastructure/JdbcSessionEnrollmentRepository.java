package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionEnrollmentRepository;
import nextstep.courses.domain.SessionStudent;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionEnrollmentRepository")
public class JdbcSessionEnrollmentRepository implements SessionEnrollmentRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionEnrollmentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int enrollStudent(Long sessionId, Long userId) {
        String sql = "insert into session_enrollment (user_id, session_id) values(?, ?)";
        return jdbcTemplate.update(sql, userId, sessionId);
    }

    @Override
    public List<SessionStudent> findStudentsBySessionId(Long sessionId) {
        String sql = "select user_id, session_id, enrollment_status from session_enrollment where session_id = ?";
        RowMapper<SessionStudent> rowMapper = (rs, rowNum) -> new SessionStudent(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3));
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
