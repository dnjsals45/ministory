package seongmin.ministory.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.ministory.domain.tag.entity.ContentTag;

import java.util.List;

public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {
    List<ContentTag> findAllByContentId(Long contentId);

    void deleteByContentIdAndTagId(Long contentId, Long tagId);
}
