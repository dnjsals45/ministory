package seongmin.ministory.domain.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.ministory.domain.content.entity.Content;

public interface ContentRepository extends JpaRepository<Content, Long>, QContentRepository {
    boolean existsByIdAndUserId(Long contentId, Long userId);
}
