package seongmin.minilife.domain.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.minilife.domain.content.entity.Content;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    boolean existsByIdAndUserId(Long contentId, Long userId);

    List<Content> findByCompleteTrue();
}
