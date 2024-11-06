package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, title, session_type, progress_status, recruitment_status, price, max_enrollment, started_at, ended_at from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> {
            Session session = SessionMapper.rowToSession(rs);
            return session;
        };
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int save(Session session) {
        if (session.isFreeSession()) {
            String sql = "insert into session (id, title, session_type, progress_status, recruitment_status, started_at, ended_at) values(?, ?, ?, ?, ?, ?, ?)";
            Period period = session.getPeriod();
            SessionStatus status = session.getStatus();
            return jdbcTemplate.update(sql, session.getId(), session.getTitle(), session.getType().toString(), status.getProgressStatus().toString(), status.getRecruitmentStatus().toString(), period.getStartDate(), period.getEndDate());
        }
        String sql = "insert into session (id, title, session_type, progress_status, recruitment_status, max_enrollment, started_at, ended_at) values(?, ?, ?, ?, ?, ?, ?,?)";
        PaidSession paidSession = (PaidSession) session;
        Period period = paidSession.getPeriod();
        SessionStatus status = paidSession.getStatus();
        return jdbcTemplate.update(sql, paidSession.getId(), paidSession.getTitle(), paidSession.getType().toString(), status.getProgressStatus().toString(), status.getRecruitmentStatus().toString(), paidSession.getMaxEnrollment(), period.getStartDate(), period.getEndDate());
    }
}
