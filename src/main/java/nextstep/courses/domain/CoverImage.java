package nextstep.courses.domain;

public class CoverImage {

    private static final int MAX_FILE_SIZE_KB = 1024;
    private static final int RATIO_WIDTH = 3;
    private static final int RATIO_HEIGHT = 2;

    private String title;
    private String format;
    private int fileSize;
    private int width;
    private int height;

    public CoverImage(String title, String format, int fileSize, int width, int height) {
        validateInput(format, fileSize, width, height);
        this.title = title;
        this.format = format;
        this.fileSize = fileSize;
        this.width = width;
        this.height = height;
    }

    private void validateInput(String format, int fileSize, int width, int height) {
        if (fileSize > MAX_FILE_SIZE_KB) {
            throw new CannotRegisterException("이미지 크기는 1MB 이하여야 합니다.");
        }

        if (!ImageFormat.isValidFormat(format)) {
            throw new CannotRegisterException("유효하지 않는 포맷입니다.");
        }

        if (!isValidRatio(width, height)) {
            throw new CannotRegisterException("유효하지 않는 포맷입니다.");
        }
    }

    private boolean isValidRatio(int width, int height) {
        return width * RATIO_HEIGHT == height * RATIO_WIDTH;
    }
}
