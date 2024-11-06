package nextstep.courses.infrastructure;

import nextstep.courses.domain.EnrollmentStatus;
import nextstep.courses.domain.SessionEnrollmentRepository;
import nextstep.courses.domain.SessionStudent;
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
    public SessionStudent findStudentById(SessionStudent student) {
        String sql = "select user_id, session_id, enrollment_status from session_enrollment where user_id = ? and session_id = ?";
        RowMapper<SessionStudent> rowMapper = (rs, rowNum) -> new SessionStudent(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3));
        return jdbcTemplate.queryForObject(sql, rowMapper, student.getStudentId(), student.getSessionId());
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

    @Override
    public List<SessionStudent> findStudentsByEnrollmentStatus(Long sessionId, EnrollmentStatus status) {
        String sql = "select user_id, session_id, enrollment_status from session_enrollment where session_id = ? and enrollment_status = ?";
        RowMapper<SessionStudent> rowMapper = (rs, rowNum) -> new SessionStudent(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3));
        return jdbcTemplate.query(sql, rowMapper, sessionId, status.toString());
    }

    @Override
    public int updateStudentEnrollmentStatus(SessionStudent student) {
        String sql = "update session_enrollment set enrollment_status = ? where user_id = ? and session_id = ?";
        EnrollmentStatus status = student.getStatus();
        return jdbcTemplate.update(sql, status.toString(), student.getStudentId(), student.getSessionId());
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
