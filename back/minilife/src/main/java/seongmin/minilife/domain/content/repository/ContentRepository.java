package seongmin.minilife.domain.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.minilife.domain.content.entity.Content;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long>, QContentRepository {
    boolean existsByIdAndUserId(Long contentId, Long userId);

    @EntityGraph(attributePaths = {"contentTags"})
    List<Content> findByCompleteTrue();

    @EntityGraph(attributePaths = {"contentTags"})
    Page<Content> findByCompleteTrue(Pageable pageable);
}
