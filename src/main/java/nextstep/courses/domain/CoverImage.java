package nextstep.courses.domain;

public class CoverImage {

    private static final int MAX_FILE_SIZE_KB = 1024;
    private static final int RATIO_WIDTH = 3;
    private static final int RATIO_HEIGHT = 2;
    private static final int MIN_WIDTH_PIXEL = 300;
    private static final int MIN_HEIGHT_PIXEL = 200;

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
            throw new CannotRegisterException("이미지 크기는 1MB(1024KB) 이하이여야 합니다.");
        }

        if (!ImageFormat.isValidFormat(format)) {
            throw new CannotRegisterException("유효하지 않는 포맷입니다.");
        }

        if (!isValidRatio(width, height)) {
            throw new CannotRegisterException("width와 height의 비율은 3:2여야 합니다.");
        }

        if (!isValidPixel(width, height)) {
            throw new CannotRegisterException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
    }

    private boolean isValidRatio(int width, int height) {
        return width * RATIO_HEIGHT == height * RATIO_WIDTH;
    }

    private boolean isValidPixel(int width, int height) {
        return width >= MIN_WIDTH_PIXEL && height >= MIN_HEIGHT_PIXEL;
    }
}
