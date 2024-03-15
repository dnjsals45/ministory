package seongmin.minilife.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.minilife.domain.tag.entity.ContentTag;

public interface ContentTagRepository extends JpaRepository<ContentTag, Long> {
}
