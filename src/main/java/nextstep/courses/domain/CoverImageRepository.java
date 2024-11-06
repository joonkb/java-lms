package nextstep.courses.domain;

import java.util.List;
import java.util.Optional;

public interface CoverImageRepository {
    Optional<CoverImage> findById(Long id);

    int upload(Long id, CoverImage image);

    List<CoverImage> findImagesBySessionId(Long sessionId);
}
