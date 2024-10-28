package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ImageFormat {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg"),
    ;

    private String format;

    private static Map<String, ImageFormat> cachedMap = Collections.unmodifiableMap(Arrays.stream(values())
            .collect(Collectors.toMap(ImageFormat::getFormat, Function.identity())));

    ImageFormat(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public static boolean isValidFormat(String type) {
        return cachedMap.containsKey(type);
    }
}
