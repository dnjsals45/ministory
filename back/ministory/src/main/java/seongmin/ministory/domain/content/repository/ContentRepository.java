package seongmin.ministory.domain.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.ministory.domain.content.entity.Content;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContentRepository extends JpaRepository<Content, Long>, QContentRepository {
    boolean existsByIdAndUserId(Long contentId, Long userId);

    List<Content> findAllByCompleteIsFalseAndDeletedAtIsNull();

    Long countAllByCompleteIsTrue();

    Optional<Content> findByUuid(UUID uuid);

    boolean existsByUuidAndUserId(UUID uuid, Long userId);
}
