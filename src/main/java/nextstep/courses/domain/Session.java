package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
import nextstep.users.domain.NsUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Session {

    private Long id;

    private String title;

    private SessionType type;

    private SessionStatus status = SessionStatus.PREPARING;

    private CoverImage image;

    protected Long price;

    private List<NsUser> students = new ArrayList<>();

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Session(Long id, String title, SessionType type, SessionStatus status, Long price, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(Long id, String title, SessionType type, Long price, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(String title, SessionType type, Long price, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.type = type;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public SessionType getType() {
        return type;
    }

    public SessionStatus getStatus() {
        return status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void openEnrollment() {
        this.status = SessionStatus.RECRUITING;
    }

    public void uploadCoverImage(CoverImage image) {
        this.image = image;
    }

    public boolean isFreeSession() {
        return type == SessionType.FREE;
    }

    public abstract void enroll(List<NsUser> students, Payment payment);

    protected void validateRecruitingStatus() {
        if (!SessionStatus.canEnroll(status)) {
            throw new CannotRegisterException("현재 모집중인 상태가 아닙니다.");
        }
    }

    public Long getPrice() {
        return price;
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

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
