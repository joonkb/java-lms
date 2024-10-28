package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING, RECRUITING, CLOSED,
    ;

    public static boolean canEnroll(SessionStatus status) {
        return status == RECRUITING;
    }
}
