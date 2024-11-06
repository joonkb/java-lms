package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.CoverImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<CoverImage> findById(Long id) {
        String sql = "select session_id, title, format, fileSize, width, height from cover_image where session_id = ?";
        RowMapper<CoverImage> rowMapper = (rs, rowNum) -> new CoverImage(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6));
        return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
    }

    @Override
    public int upload(Long sessionId, CoverImage image) {
        String sql = "insert into cover_image (session_id, title, format, fileSize, width, height) values(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, sessionId, image.getTitle(), image.getFormat(), image.getFileSize(), image.getWidth(), image.getHeight());
    }

    @Override
    public List<CoverImage> findImagesBySessionId(Long sessionId) {
        String sql = "select session_id, title, format, fileSize, width, height from cover_image where session_id = ?";
        RowMapper<CoverImage> rowMapper = (rs, rowNum) -> new CoverImage(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6));
        return jdbcTemplate.query(sql, rowMapper, sessionId);
    }
}
