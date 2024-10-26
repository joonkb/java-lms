package nextstep.courses.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {

    private Long id;

    private String title;

    private SessionType type;

    private SessionStatus status = SessionStatus.PREPARING;

    private int price;

    private int maxEnrollment;

    private List<NsUser> students = new ArrayList<>();

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Session(Long id, String title, SessionType type, int price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.price = price;
        this.maxEnrollment = maxEnrollment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(Long id, String title, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, startDate, endDate);
    }

    public Session(String title, SessionType type, int price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, type, price, maxEnrollment, startDate, endDate);
    }

    public void openEnrollment() {
        this.status = SessionStatus.RECRUITING;
    }

    public void enroll(NsUser user) {
        canEnroll();
        students.add(user);
    }

    private void canEnroll() {
        if (status != SessionStatus.RECRUITING) {
            throw new IllegalArgumentException("현재 모집중인 상태가 아닙니다.");
        }

        if (type == SessionType.PAID && students.size() >= maxEnrollment) {
            throw new IllegalArgumentException("최대 수강 인원을 초과하였습니다.");
        }
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
