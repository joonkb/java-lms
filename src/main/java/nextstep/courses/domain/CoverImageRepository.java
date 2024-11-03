package nextstep.courses.domain;

import java.util.Optional;

public interface CoverImageRepository {
    Optional<CoverImage> findById(Long id);

    int upload(CoverImage image);
}
