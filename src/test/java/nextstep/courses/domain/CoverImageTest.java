package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CoverImageTest {

    @Test
    @DisplayName("이미지의 최대 크기는 1024KB이며, 이를 초과하는 경우 예외가 발생한다")
    void coverImageTest01() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "jpg", 1025, 300, 200);
        });
    }

    @Test
    @DisplayName("이미지 타입은 gif, jpg(jpeg 포함),png, svg 허용한다. 이외의 타입은 예외가 발생한다.")
    void coverImageTest02() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "pdf", 100, 300, 200);
        });
    }

    @Test
    @DisplayName("width와 height의 비율은 3:2 이어야 한다. 그렇지 않은 경우 예외가 발생한다")
    void coverImageTest03() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "jpg", 100, 400, 200);
        });
    }

    @Test
    @DisplayName("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 하며, 그렇지 않은 경우 예외가 발생한다")
    void coverImageTest04() {
        assertThatThrownBy(() -> {
            new CoverImage("스프링", "jpg", 100, 150, 100);
        });
    }
}
