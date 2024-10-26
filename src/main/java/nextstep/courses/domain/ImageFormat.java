package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageFormat {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg"),
    ;

    private String format;

    ImageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public static boolean isValidFormat(String type) {
        return Arrays.stream(values())
                .map(ImageFormat::getFormat)
                .anyMatch(value -> value.equals(type));
    }
}
