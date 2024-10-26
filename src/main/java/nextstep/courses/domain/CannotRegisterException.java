package nextstep.courses.domain;

public class CannotRegisterException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CannotRegisterException(String message) {
        super(message);
    }
}
