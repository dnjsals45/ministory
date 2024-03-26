package seongmin.minilife.domain.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import seongmin.minilife.domain.content.entity.Content;

import java.util.List;

public interface QContentRepository {

    Page<Content> findContentsPage(Pageable pageable);

    List<Content> findRecentContentsWithTags();
}
