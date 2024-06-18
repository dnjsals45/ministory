package seongmin.ministory.domain.content.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import seongmin.ministory.domain.content.entity.Content;

import java.util.List;

public interface QContentRepository {
    List<Content> findRecentContentsWithTags();

    Page<Content> findTagContents(String tagName, Pageable pageable);

    Page<Content> searchContent(Pageable pageable, String tagName, String keyword);
}
