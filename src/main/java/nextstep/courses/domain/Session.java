package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

import java.util.List;

public abstract class Session {

    private Long id;

    private String title;

    private SessionType type;

    private SessionStatus status = new SessionStatus();

    protected Long price;

    private Period period;

    public Session(Long id, String title, SessionType type, SessionStatus status, Long price, Period period) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.price = price;
        this.period = period;
    }

    public Session(Long id, String title, SessionType type, Long price, Period period) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.price = price;
        this.period = period;
    }

    public Session(String title, SessionType type, Long price, Period period) {
        this.title = title;
        this.type = type;
        this.price = price;
        this.period = period;
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

    public Period getPeriod() {
        return period;
    }

    public Long getPrice() {
        return price;
    }

    public void openEnrollment() {
        status.openSession();
    }

    public void startSession() {
        status.startSession();
    }

    public boolean isFreeSession() {
        return type == SessionType.FREE;
    }

    public abstract void enroll(List<SessionStudent> students, Payment payment);

    protected void validateRecruitingStatus() {
        if (!status.canEnroll()) {
            throw new CannotRegisterException("현재 모집중인 상태가 아닙니다.");
        }
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
