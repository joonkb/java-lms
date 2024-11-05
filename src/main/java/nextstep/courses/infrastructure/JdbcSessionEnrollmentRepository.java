package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionEnrollmentRepository;
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
        String sql = "insert into session_enrollment (user_id, session_id, registered_at) values(?, ?, ?)";
        return jdbcTemplate.update(sql, userId, sessionId, LocalDateTime.now());
    }

    @Override
    public List<NsUser> findStudentsBySessionId(Long sessionId) {
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
