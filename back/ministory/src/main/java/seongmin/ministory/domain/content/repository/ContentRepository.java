package seongmin.ministory.domain.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import seongmin.ministory.domain.content.entity.Content;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long>, QContentRepository {
    boolean existsByIdAndUserId(Long contentId, Long userId);
}
