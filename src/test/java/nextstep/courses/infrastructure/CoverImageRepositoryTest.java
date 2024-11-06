package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.qna.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CoverImageRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    public static final long SAVED_SESSION_ID = 4L;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CoverImageRepository coverImageRepository;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("주어진 커버이미지를 저장하고 조회한다.")
    void uploadCoverImage() {
        Session savedSession = sessionRepository.findById(SAVED_SESSION_ID).orElseThrow(NotFoundException::new);
        CoverImage image = new CoverImage(savedSession.getId(), "스프링 커버이미지", "png", 1000, 300, 200);
        coverImageRepository.upload(savedSession.getId(), image);

        CoverImage savedImage = coverImageRepository.findById(image.getId()).get();
        assertThat(savedImage).isEqualTo(image);
    }

    @Test
    @DisplayName("주어진 강의에 커버이미지를 두번 등록한다")
    void uploadImage() {

        CoverImage image1 = new CoverImage("컴퓨터", "jpg", 514, 300, 200);
        CoverImage image2 = new CoverImage("JAVA", "jpg", 724, 300, 200);

        int uploadCount = 0;
        Session findSession = sessionRepository.findById(SAVED_SESSION_ID).orElseThrow();
        uploadCount += coverImageRepository.upload(findSession.getId(), image1);
        uploadCount += coverImageRepository.upload(findSession.getId(), image2);

        List<CoverImage> coverImages = coverImageRepository.findImagesBySessionId(SAVED_SESSION_ID);
        Assertions.assertThat(coverImages).hasSize(uploadCount);
    }
}
