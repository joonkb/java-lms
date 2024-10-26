package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CoverImageTest {

    @Test
    void 이미지_생성불가_사이즈검증() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "jpg", 1025, 300, 200);
        });
    }

    @Test
    void 이미지_생성불가_타입검증() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "pdf", 100, 300, 200);
        });
    }

    @Test
    void 이미지_생성불가_비율검증() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "jpg", 100, 400, 200);
        });
    }
}
