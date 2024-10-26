package nextstep.courses.domain;

import nextstep.payments.domain.Payment;
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

    private Long price;

    private int maxEnrollment;

    private CoverImage image;

    private List<NsUser> students = new ArrayList<>();

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Session(Long id, String title, SessionType type, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.price = price;
        this.maxEnrollment = maxEnrollment;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(Long id, String title, Long price, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Session(String title, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, 0L, startDate, endDate);
    }

    public Session(String title, Long price, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, price, startDate, endDate);
    }

    public Session(String title, SessionType type, Long price, int maxEnrollment, LocalDateTime startDate, LocalDateTime endDate) {
        this(1L, title, type, price, maxEnrollment, startDate, endDate);
    }

    public Long getId() {
        return id;
    }

    public Long getPrice() {
        return price;
    }

    public void openEnrollment() {
        this.status = SessionStatus.RECRUITING;
    }

    public void enroll(NsUser user, Payment payment) {
        canEnroll(payment);
        students.add(user);
    }

    private void canEnroll(Payment payment) {

        if (!this.price.equals(payment.getAmount())) {
            throw new CannotRegisterException("결제한 금액과 수강료가 일치하지 않습니다.");
        }

        if (!SessionStatus.canEnroll(status)) {
            throw new CannotRegisterException("현재 모집중인 상태가 아닙니다.");
        }

        if (isOverMaxEnrollment()) {
            throw new CannotRegisterException("최대 수강 인원을 초과하였습니다.");
        }
    }

    private boolean isOverMaxEnrollment() {
        return type == SessionType.PAID && students.size() >= maxEnrollment;
    }

    public void uploadCoverImage(CoverImage image) {
        this.image = image;
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
