package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private Long id;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, startDate, endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(startDate, session.startDate) && Objects.equals(endDate, session.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate);
    }
}
