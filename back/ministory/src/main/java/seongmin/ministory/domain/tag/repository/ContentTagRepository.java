package seongmin.ministory.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.ministory.domain.tag.entity.ContentTag;

import java.util.List;
import java.util.Optional;

public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {
    List<ContentTag> findAllByContentId(Long contentId);

    void deleteByContentIdAndTagId(Long contentId, Long tagId);

    Optional<ContentTag> findByContentIdAndTagId(Long contentId, Long tagId);

    boolean existsByContentIdAndTagId(Long contentId, Long tagId);
}
