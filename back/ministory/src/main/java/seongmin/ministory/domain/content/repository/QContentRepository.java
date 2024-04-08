package seongmin.ministory.domain.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import seongmin.ministory.domain.content.entity.Content;

import java.util.List;

public interface QContentRepository {

    Page<Content> findContentsPage(Pageable pageable);

    List<Content> findRecentContentsWithTags();

    Page<Content> findTagContents(String tagName, Pageable pageable);
}
