package seongmin.minilife.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.minilife.domain.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByTagName(String tagName);
}
