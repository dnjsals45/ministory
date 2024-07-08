package seongmin.ministory.domain.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seongmin.ministory.domain.tag.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>, QTagRepository {
    Optional<Tag> findByTagName(String tagName);

    boolean existsByTagName(String tagName);
}
