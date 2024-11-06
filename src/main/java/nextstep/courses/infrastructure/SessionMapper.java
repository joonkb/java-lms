package nextstep.courses.infrastructure;

import nextstep.courses.UnsupportedTypeException;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper {

    private SessionMapper() {

    }

    public static Session rowToSession(ResultSet rs) throws SQLException {
        String sessionType = rs.getString("session_type");
        if ("FREE".equals(sessionType)) {
            return new FreeSession(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("progress_status"),
                    rs.getString("recruitment_status"),
                    rs.getTimestamp("started_at").toLocalDateTime(),
                    rs.getTimestamp("ended_at").toLocalDateTime()
            );
        }
        if ("PAID".equals(sessionType)) {
            return new PaidSession(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("progress_status"),
                    rs.getString("recruitment_status"),
                    rs.getLong("price"),
                    rs.getInt("max_enrollment"),
                    rs.getTimestamp("started_at").toLocalDateTime(),
                    rs.getTimestamp("ended_at").toLocalDateTime()
            );
        }
        throw new UnsupportedTypeException("유효하지 않은 강의 형식입니다.");
    }
}
