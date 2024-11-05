package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImage;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;


@JdbcTest
class JdbcCoverImageRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    private JdbcCoverImageRepository coverImageRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
        coverImageRepository = new JdbcCoverImageRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("주어진 강의에 커버 이미지를 두 번 등록한다")
    void uploadImage() {

        CoverImage image1 = new CoverImage("컴퓨터", "jpg", 514, 300, 200);
        CoverImage image2 = new CoverImage("JAVA", "jpg", 724, 300, 200);

        int uploadCount = 0;
        Session findSession = sessionRepository.findById(4L).orElseThrow();
        findSession.uploadCoverImage(image1);
        uploadCount += coverImageRepository.upload(findSession.getId(), image1);

        findSession.uploadCoverImage(image2);
        uploadCount += coverImageRepository.upload(findSession.getId(), image2);
        Assertions.assertThat(uploadCount).isEqualTo(2);
    }
}
